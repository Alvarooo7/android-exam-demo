package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation);
        ivLogo = findViewById(R.id.ivLogo);
        ivLogo.startAnimation(animation);
        final Intent goToLogin = new Intent(this,LoginActivity.class);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    startActivity(goToLogin);
                    finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}