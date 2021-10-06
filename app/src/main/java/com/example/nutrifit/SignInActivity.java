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

        String text1 = getResources().getString(R.string.noAccount);

        SpannableString ss1 = new SpannableString(text1);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getColor(R.color.green));
                ds.setUnderlineText(false);
            }
        };
        ss1.setSpan(clickableSpan1, 27, 34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mSignUp_TextView.setText(ss1);
        mSignUp_TextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}