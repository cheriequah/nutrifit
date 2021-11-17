package com.example.nutrifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {
    private EditText weightKgForEditText, heightCmForEditText;
    private EditText weightLbsForEditText, heightFtForEditText, heightInForEditText;
    private Button calculateButton;
    private TextView bmiForTextView, categoryForTextView;
    private ToggleButton toggleButton;
    private TextView mGoalCalories, mEmailOutput;

    private boolean MetricUnits;

    private final static String SP_FILE_NAME = "com.example.nutrifit.sharedpreference";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mGoalCalories = findViewById(R.id.goal);
        mEmailOutput = findViewById(R.id.email_output);

        weightKgForEditText = findViewById(R.id.weight_kg);
        heightCmForEditText = findViewById(R.id.height_cm);

        weightLbsForEditText = findViewById(R.id.weight_lbs);
        heightFtForEditText = findViewById(R.id.height_ft);
        heightInForEditText= findViewById(R.id.height_in);

        calculateButton = findViewById(R.id.calculate_bmi_button);
        toggleButton = findViewById(R.id.toggle);

        bmiForTextView = findViewById(R.id.activity_bmi);
        categoryForTextView = findViewById(R.id.activity_category);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_food:
                        startActivity(new Intent(getApplicationContext()
                                ,SearchMeals.class));
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
                        return true;

                }
                return false;
            }
        });


        MetricUnits = true;
        updateInputsVisibility();


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MetricUnits) {
                    if (weightKgForEditText.length() == 0 || heightCmForEditText.length() == 0) {
                        Toast.makeText(Profile.this, "Please enter the weight and height", Toast.LENGTH_SHORT).show();
                    } else {
                        double heightInCms = Double.parseDouble(heightCmForEditText.getText().toString());
                        double weightInKgs = Double.parseDouble(weightKgForEditText.getText().toString());
                        double bmi = BMI.getInstance().calcBMIMetric(heightInCms, weightInKgs);
                        displayBMI(bmi);

                    }
                } else {
                    if (weightLbsForEditText.length() == 0 || heightFtForEditText.length() == 0 || heightInForEditText.length() == 0) {
                        Toast.makeText(Profile.this, "Please enter the weight and height", Toast.LENGTH_SHORT).show();
                    } else {
                        double heightFeet = Double.parseDouble(heightFtForEditText.getText().toString());
                        double heightInches = Double.parseDouble(heightInForEditText.getText().toString());
                        double weightLbs = Double.parseDouble(weightLbsForEditText.getText().toString());
                        double bmi =BMI.getInstance().calcBMIImperial(heightFeet, heightInches, weightLbs);
                        displayBMI(bmi);
                    }
                }
            }
        });

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MetricUnits = !MetricUnits;
                updateInputsVisibility();
            }
        });

        //display user calculated calories in profile
        SharedPreferences sp = getSharedPreferences(SP_FILE_NAME, MODE_PRIVATE);
        String calories = sp.getString("calories", "");
        String email = sp.getString("email", "");

        //display user email in profile
        mEmailOutput.setText(email);
        mGoalCalories.setText(calories);
    }


    private void updateInputsVisibility() {
        if (MetricUnits) {
            heightCmForEditText.setVisibility(View.VISIBLE);
            weightKgForEditText.setVisibility(View.VISIBLE);
            heightFtForEditText.setVisibility(View.GONE);
            heightInForEditText.setVisibility(View.GONE);
            weightLbsForEditText.setVisibility(View.GONE);
        } else {
            heightCmForEditText.setVisibility(View.GONE);
            weightKgForEditText.setVisibility(View.GONE);
            heightFtForEditText.setVisibility(View.VISIBLE);
            heightInForEditText.setVisibility(View.VISIBLE);
            weightLbsForEditText.setVisibility(View.VISIBLE);
        }

    }
    private void displayBMI(double bmi) {
        //ResultCardView.setVisibility(View.VISIBLE);

        bmiForTextView.setText(String.format("%.2f", bmi));

        String bmiCategory = BMI.getInstance().classifyBMI(bmi);
        categoryForTextView.setText(bmiCategory);


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
            Intent i = new Intent(Profile.this, SignInActivity.class);
            startActivity(i);
        }
        if(id == R.id.tips){
            Intent i = new Intent(Profile.this, FoodTips.class);
            startActivity(i);
        }
        if(id == R.id.calorie){
            Intent i = new Intent(Profile.this, CalorieCalculator.class);
            startActivity(i);
        }
        if(id == R.id.search_food){
            Intent i = new Intent(Profile.this, SearchMeals.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}






