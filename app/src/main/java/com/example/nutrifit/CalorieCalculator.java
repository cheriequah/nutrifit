package com.example.nutrifit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Formatter;

public class CalorieCalculator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mAgeCalorie;
    private EditText mWeightCalorie;
    private EditText mHeightCalorie;
    private Spinner mSpinnerGender;
    private Spinner mSpinnerActivity;
    private Spinner mSpinnerGoal;
    private Button mCalculateCalorie;
    private TextView mResCalorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);

        mAgeCalorie = findViewById(R.id.calorie_age);
        mWeightCalorie = findViewById(R.id.calorie_weight);
        mHeightCalorie = findViewById(R.id.calorie_height);
        mSpinnerGender = findViewById(R.id.gender_spinner);
        mSpinnerActivity = findViewById(R.id.activity_spinner);
        mSpinnerGoal = findViewById(R.id.goal_spinner);
        mCalculateCalorie = findViewById(R.id.calculate_calorie_btn);
        mResCalorie = findViewById(R.id.calorie_calculated);

        //Listener for spinner
        mSpinnerGender.setOnItemSelectedListener(CalorieCalculator.this);
        mSpinnerActivity.setOnItemSelectedListener(CalorieCalculator.this);
        mSpinnerGoal.setOnItemSelectedListener(CalorieCalculator.this);

        //Spinner elements added to array list genderCategory
        ArrayList<String> genderCategory = new ArrayList<>();
        genderCategory.add("Choose Gender");
        genderCategory.add("Male");
        genderCategory.add("Female");

        //Spinner elements added to array list activityLevel
        ArrayList<String> activityLevel = new ArrayList<>();
        activityLevel.add("Choose Activity Level");
        activityLevel.add("Sedentary (little or no exercise)");
        activityLevel.add("Lightly active (exercise 1-3 days/week)");
        activityLevel.add("Moderately active (exercise 3-5 days/week)");
        activityLevel.add("Very active (exercise 6-7 days a week)");
        activityLevel.add("Extra active (very hard exercise/sports)");

        //spinner elements added to array list goalCategory
        ArrayList<String> goalCategory = new ArrayList<>();
        goalCategory.add("Select Goal");
        goalCategory.add("Lose Weight");
        goalCategory.add("Maintain Weight");
        goalCategory.add("Gain Weight");

        //creating adapter for spinner
        ArrayAdapter<String>  gender_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderCategory);
        mSpinnerGender.setAdapter(gender_Adapter);

        ArrayAdapter<String> activity_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, activityLevel);
        mSpinnerActivity.setAdapter(activity_Adapter);

        ArrayAdapter<String> goal_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, goalCategory);
        mSpinnerGoal.setAdapter(goal_Adapter);

        mCalculateCalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age = 0, height = 0;
                double weight = 0;


                if(mAgeCalorie.getText().toString().length() > 0) {
                    age = Integer.parseInt(mAgeCalorie.getText().toString());
                }
                if(mWeightCalorie.getText().toString().length() > 0) {
                    weight = Double.parseDouble(mWeightCalorie.getText().toString());
                }
                if(mHeightCalorie.getText().toString().length() > 0) {
                    height = Integer.parseInt(mHeightCalorie.getText().toString());
                }

                int gender_Position = mSpinnerGender.getSelectedItemPosition();
                int activity_Position = mSpinnerActivity.getSelectedItemPosition();
                int goal_Position = mSpinnerGoal.getSelectedItemPosition();

                double activity_level = 0, BMR = 0, calories = 0, goal=0;


                //if is male
                if(gender_Position == 1)
                    BMR = 10 * weight + height - 5 * age + 5;
                //if female
                if(gender_Position == 2)
                    BMR = 10 * weight + 6.25 * height - 5 * age - 161;

                //get the activity level of the user
                if(activity_Position == 1)
                    activity_level = 1.2;
                if(activity_Position == 2)
                    activity_level = 1.375;
                if(activity_Position == 3)
                    activity_level = 1.55;
                if(activity_Position == 4)
                    activity_level = 1.725;
                if(activity_Position == 5)
                    activity_level = 1.9;

                //formula for calories
                calories = BMR*activity_level;

                //get the goal of the user
                Formatter formatter = new Formatter();

                if(goal_Position == 1)
                    goal = calories - (calories*0.2);

                if(goal_Position == 2)
                    goal = calories;

                if(goal_Position == 3)
                    goal = calories + (calories*0.2);

                formatter.format("%.1f", goal);
                mResCalorie.setText(formatter.toString());
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}