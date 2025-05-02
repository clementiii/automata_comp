package com.example.automatacomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Force app to use the dark theme regardless of system settings
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Setup toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Animate welcome text
        TextView welcomeText = findViewById(R.id.welcome_text);
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(1500);
        welcomeText.startAnimation(fadeIn);
        
        // Setup main screen navigation buttons
        findViewById(R.id.btn_fibonacci).setOnClickListener(v -> startActivityWithAnim(FibonacciActivity.class));
        findViewById(R.id.btn_lucas).setOnClickListener(v -> startActivityWithAnim(LucasActivity.class));
        findViewById(R.id.btn_tribonacci).setOnClickListener(v -> startActivityWithAnim(TribonacciActivity.class));
        findViewById(R.id.btn_collatz).setOnClickListener(v -> startActivityWithAnim(CollatzActivity.class));
        findViewById(R.id.btn_euclidean).setOnClickListener(v -> startActivityWithAnim(EuclideanActivity.class));
    }

    private void startActivityWithAnim(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}