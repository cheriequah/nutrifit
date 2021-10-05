package com.example.nutrifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {
    private TextView mSignUp_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mSignUp_TextView = findViewById(R.id.signUp_textView);

        String text = getResources().getString(R.string.noAccount);

        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //Intent i = new Intent(MainActivity.this, SignInActivity.class);
                //startActivity(i);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.green));
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 28, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mSignUp_TextView.setText(ss);
        mSignUp_TextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}