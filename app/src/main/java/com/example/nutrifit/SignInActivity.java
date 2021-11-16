package com.example.nutrifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity {
    private TextView mSignUp_TextView;
    private TextInputEditText mEmail_SignIn, mPasswordSignIn;
    private Button mSignIn_Btn;
    private DBHelper DB;

    private final static String SP_FILE_NAME = "com.example.nutrifit.sharedpreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

        /*
        SharedPreferences sp = getSharedPreferences(SP_FILE_NAME, MODE_PRIVATE);
        if(sp.getBoolean("isLoggedIn", true)) {
            Intent i = new Intent(SignInActivity.this, SearchMeals.class);
            startActivity(i);
        }
        */
        mSignUp_TextView = findViewById(R.id.signUp_textView);
        mEmail_SignIn = findViewById(R.id.email_signIn);
        mPasswordSignIn = findViewById(R.id.password_signIn);
        mSignIn_Btn = findViewById(R.id.signIn_btn);
        DB = new DBHelper(this);

        String text = getResources().getString(R.string.noAccount);

        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getColor(R.color.green));
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 27, 34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mSignUp_TextView.setText(ss);
        mSignUp_TextView.setMovementMethod(LinkMovementMethod.getInstance());

        mSignIn_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail_SignIn.getText().toString();
                String password = mPasswordSignIn.getText().toString();

                if(email.equals("") || password.equals("")) {
                    Toast.makeText(SignInActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkEmailPw = DB.checkEmailPassword(email, password);
                    if(checkEmailPw == true) {

                        SharedPreferences.Editor editor = getSharedPreferences(SP_FILE_NAME, MODE_PRIVATE).edit();
                        editor.putString("email", email);
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        Toast.makeText(SignInActivity.this, "Sign In Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignInActivity.this, SearchMeals.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(SignInActivity.this, "Sign In unsuccesful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}