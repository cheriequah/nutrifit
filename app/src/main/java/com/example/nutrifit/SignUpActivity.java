package com.example.nutrifit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText mHeight_EditText, mWeight_EditText;
    private Button mSignUp_Btn;
    private RadioButton mRadioBtn_Goal1, mRadioBtn_Goal2, mRadioBtn_Goal3, mRadioBtn_Exercise1, mRadioBtn_Exercise2, mRadioBtn_Exercise3, mRadioBtn_Male, mRadioBtn_Female;
    private DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        mSignUp_Btn = findViewById(R.id.signUp_btn);

        mRadioBtn_Goal1 = findViewById(R.id.radioBtn_goal1);
        mRadioBtn_Goal2 = findViewById(R.id.radioBtn_goal2);
        mRadioBtn_Goal3 = findViewById(R.id.radioBtn_goal3);
        mRadioBtn_Exercise1 = findViewById(R.id.radioBtn_exercise1);
        mRadioBtn_Exercise2 = findViewById(R.id.radioBtn_exercise2);
        mRadioBtn_Exercise3 = findViewById(R.id.radioBtn_exercise3);
        mRadioBtn_Male = findViewById(R.id.radioBtn_male);
        mRadioBtn_Female = findViewById(R.id.radioBtn_female);
        mHeight_EditText = findViewById(R.id.height);
        mWeight_EditText = findViewById(R.id.weight);
        DB = new DBHelper(this);

        //String newName = getIntent().getStringExtra("name");
        //String newEmail = getIntent().getStringExtra("email");
        //String newDOB = getIntent().getStringExtra("DOB");
        //String newPassword = getIntent().getStringExtra("password");


        mSignUp_Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                insertData();

            }
        });

    }

    //To insert data
    public void insertData() {

        String newName = getIntent().getStringExtra("name");
        String newEmail = getIntent().getStringExtra("email");
        String newDOB = getIntent().getStringExtra("DOB");
        String newPassword = getIntent().getStringExtra("password");

        if(mHeight_EditText.getText().toString().isEmpty() || mWeight_EditText.getText().toString().isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            //Log.d("mytag", "Hi " + newName + newEmail + newDOB + newPassword);
        }
        else {
            Boolean checkEmail = DB.checkEmail(newEmail);
            if(checkEmail == true) {
                Toast.makeText(SignUpActivity.this, "This account already exist. Please sign in.", Toast.LENGTH_SHORT).show();
            }
            else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", newName);
                contentValues.put("email", newEmail);
                contentValues.put("date_of_birth", newDOB);
                contentValues.put("password", newPassword);

                //check the radio button is it checked for goal
                if(mRadioBtn_Goal1.isChecked()) {
                    contentValues.put("goal", 1);
                }
                if(mRadioBtn_Goal2.isChecked()) {
                    contentValues.put("goal", 2);
                }
                if(mRadioBtn_Goal3.isChecked()) {
                    contentValues.put("goal", 3);
                }

                //check the radio button is it checked for exercise
                if(mRadioBtn_Exercise1.isChecked()) {
                    contentValues.put("exercise", 1);
                }
                if(mRadioBtn_Exercise2.isChecked()) {
                    contentValues.put("exercise", 2);
                }
                if(mRadioBtn_Exercise3.isChecked()) {
                    contentValues.put("exercise", 3);
                }

                //check the radio button is it checked for gender
                if(mRadioBtn_Male.isChecked()) {
                    contentValues.put("gender", "M");
                }
                if(mRadioBtn_Female.isChecked()) {
                    contentValues.put("gender", "F");
                }

                float height = Float.parseFloat(String.valueOf(mHeight_EditText.getText()));
                float weight = Float.parseFloat(String.valueOf(mWeight_EditText.getText()));

                contentValues.put("height", height);
                contentValues.put("weight", weight);

                SQLiteDatabase SQLdata = DB.getWritableDatabase();
                long result = SQLdata.insert("users", null, contentValues);

                //if no error
                if(result != -1) {
                    Toast.makeText(SignUpActivity.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
/*
    public void checkGoal(View view) {
        int goal;
        boolean checked = ((RadioButton)view).isChecked();

        switch (view.getId()) {
            case R.id.radioBtn_goal1:
                if(checked)
                    goal = 1;
                break;
            case R.id.radioBtn_goal2:
                 if(checked)
                    goal = 2;
                break;
            case R.id.radioBtn_goal3:
                 if(checked)
                    goal = 3;
                 break;
       }
    }

    public int checkExercise(int exercise) {
        if(mRadioBtn_Exercise1.isChecked()) {
            exercise = 0;
        }
        if(mRadioBtn_Exercise2.isChecked()) {
            exercise = 1;
        }
        if(mRadioBtn_Exercise3.isChecked()) {
            exercise = 2;
        }
        return exercise;
    }

    public String checkGender(String gender) {
        if(mRadioBtn_Female.isChecked()) {
            gender = "Female";
        }
        if(mRadioBtn_Male.isChecked()) {
            gender = "Male";
        }
        return gender;
    }*/
}
