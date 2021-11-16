package com.example.nutrifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView mSignIn_TextView;
    private TextInputEditText mName_EditText, mEmail_EditText, mDOB_EditText, mPassword_EditText, mRePassword_EditText;
    private Button mNext_Btn;
    private DBHelper DB;

    private final static String SP_FILE_NAME = "com.example.nutrifit.sharedpreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //to hide actionbar
        getSupportActionBar().hide();

        mSignIn_TextView = findViewById(R.id.signIn_textView);
        mNext_Btn = findViewById(R.id.btnNext);
        mName_EditText = findViewById(R.id.name);
        mEmail_EditText = findViewById(R.id.email);
        mDOB_EditText = findViewById(R.id.date_of_birth);
        mPassword_EditText = findViewById(R.id.password);
        mRePassword_EditText = findViewById(R.id.re_password);
        DB = new DBHelper(this);

        String text = getResources().getString(R.string.haveAccount);

        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent i = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(i);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getColor(R.color.green));
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mSignIn_TextView.setText(ss);
        mSignIn_TextView.setMovementMethod(LinkMovementMethod.getInstance());

        mNext_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return the text input and convert to string
                String name = mName_EditText.getText().toString();
                String email = mEmail_EditText.getText().toString();
                String DOB = mDOB_EditText.getText().toString();
                String password = mPassword_EditText.getText().toString();
                String repassword = mRePassword_EditText.getText().toString();

                //if any input are empty
                if(name.equals("") || email.equals("") || DOB.equals("") || password.equals("") || repassword.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(password.equals(repassword) && isPasswordValid(password)) {
                        Boolean checkEmail = DB.checkEmail(email);
                        if(checkEmail == false) {
                            /*
                            Boolean insertData = DB.insertData(name, email, DOB, password);
                            if(insertData == true) {
                                Toast.makeText(MainActivity.this, "Registration is Successful", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Registration is Unsuccessful", Toast.LENGTH_SHORT).show();
                            }*/
                            //parse the input from mainpage to sign up page
                            Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                            i.putExtra("name", name);
                            i.putExtra("email", email);
                            i.putExtra("DOB", DOB);
                            i.putExtra("password", password);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "This email already exist. Please Sign In.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(!isPasswordValid(password)) {
                        Toast.makeText(MainActivity.this, "Password needs to be at least 6 characters", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        mDOB_EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.nutrifit.DatePickerFragment mDatePickerDialogFragment;
                mDatePickerDialogFragment = new com.example.nutrifit.DatePickerFragment();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(mCalendar.getTime());
        mDOB_EditText.setText(selectedDate);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }


}