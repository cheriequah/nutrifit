package com.example.nutrifit;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchMeals extends AppCompatActivity {

    //Nutritionix API endpoint to POST request
    private final String API_KEY = "04acd8e6306dab67348abc29d00a7919";
    private final String APP_ID = "e3e88668";
    private final String ingredientsURL = "https://trackapi.nutritionix.com/v2/natural/nutrients";
    private final String searchURL = "https://trackapi.nutritionix.com/v2/search/instant?query=egg";

    private Spinner mySpinner;
    private ListView foodListView;
    private SearchView mSearchView;
    private ProgressBar mProgressBar;

    private final static String SP_FILE_NAME = "com.example.nutrifit.sharedpreference";

    //the food list where we will store all the food objects after parsing json
    List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meal);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_food);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_food:
                        return true;
                    case R.id.nav_tips:
                        startActivity(new Intent(getApplicationContext()
                                ,FoodTips.class));
                        return true;
                    case R.id.nav_calculator:
                        startActivity(new Intent(getApplicationContext()
                                ,CalorieCalculator.class));
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext()
                                ,Profile.class));
                        return true;

                }
                return false;
            }
        });

        foodListView = findViewById(R.id.foodList);
        mSearchView = findViewById(R.id.search_view);

        //initializing food listview and food list
        foodListView = findViewById(R.id.foodList);
        foodList = new ArrayList<>();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length() > 2) {
                    loadFoodList(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //loadFoodList(newText);
                return false;
            }
        });
    }

    //call function to load the food list
    private void loadFoodList (String keyword) {
        mProgressBar = findViewById(R.id.progressBar);

        mProgressBar.setVisibility(View.VISIBLE);

        //creating a request queue
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, searchURL,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        mProgressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named branded inside the object
                            //so here we are getting that json array
                            JSONArray foodArray = obj.getJSONArray("branded");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < foodArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject foodObject = foodArray.getJSONObject(i);

                                //creating a food object and giving them the values from json object
                                Food food = new Food(foodObject.getString("food_name"), foodObject.getString("nf_calories"));

                                //adding the food to foodlist
                                foodList.add(food);
                            }
                            //creating custom adapter object
                            ListViewAdapter adapter = new ListViewAdapter(foodList, getApplicationContext());

                            //adding the adapter to listview
                            foodListView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Put a query into the POST request to get nutrition value back
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("query", keyword);
                //Log.d("Map", "query");
                return hashMap;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //Add authentication to headers to verify API
                HashMap<String, String> headers = new HashMap();
                headers.put("x-app-id", APP_ID);
                headers.put("x-remote-user-id", APP_ID);
                headers.put("x-app-key", API_KEY);
                //Log.d("authtest", "msg");
                return headers;
            }
        };



        //adding the string request to request queue
        mRequestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logout){
            SharedPreferences.Editor editor = getSharedPreferences(SP_FILE_NAME, MODE_PRIVATE).edit();
            editor.clear();
            editor.commit();
            Intent i = new Intent(SearchMeals.this, SignInActivity.class);
            startActivity(i);
        }
        if(id == R.id.tips){
            Intent i = new Intent(SearchMeals.this, FoodTips.class);
            startActivity(i);
        }
        if(id == R.id.calorie){
            Intent i = new Intent(SearchMeals.this, CalorieCalculator.class);
            startActivity(i);
        }
        if(id == R.id.profile){
            Intent i = new Intent(SearchMeals.this, Profile.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}




