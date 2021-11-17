package com.example.nutrifit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Vector;

public class FoodTips extends AppCompatActivity {
    private final static String SP_FILE_NAME = "com.example.nutrifit.sharedpreference";

    RecyclerView recyclerView;
    Vector<YoutubeVideos> youtubeVideos = new Vector<YoutubeVideos>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_tips);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_food:
                        startActivity(new Intent(getApplicationContext()
                                ,SearchMeals.class));
                        return true;
                    case R.id.nav_tips:
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

        //links the youtube videos

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/YL65EmlIQpk\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/MIOk6o5_xS8\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/b_6j2tQWUGE\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/TRv9BSq2yZI\" frameborder=\"0\" allowfullscreen></iframe>") );


        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);

    }
    // to display webpages

    public void browser1 (View view) {
        Intent browserIntent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://www.healthline.com/nutrition/27-health-and-nutrition-tips#TOC_TITLE_HDR_3"));
        startActivity(browserIntent);
    }
    public void browser2 (View view) {
        Intent browserIntent = new Intent (Intent.ACTION_VIEW,Uri.parse("https://www.medicinenet.com/healthy_living/article.htm"));
        startActivity(browserIntent);
    }
    public void browser3 (View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.niddk.nih.gov/health-information/weight-management/healthy-eating-physical-activity-for-life/health-tips-for-adults"));
        startActivity(browserIntent);
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
            Intent i = new Intent(FoodTips.this, SignInActivity.class);
            startActivity(i);
        }
        if(id == R.id.tips){
            Intent i = new Intent(FoodTips.this, FoodTips.class);
            startActivity(i);
        }
        if(id == R.id.calorie){
            Intent i = new Intent(FoodTips.this, CalorieCalculator.class);
            startActivity(i);
        }
        if(id == R.id.profile){
            Intent i = new Intent(FoodTips.this, Profile.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}





