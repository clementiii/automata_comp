package com.example.automatacomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Force app to use the dark theme regardless of system settings
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize UI components
        setupUI();
        
        // Configure drawer layout
        setupDrawer();
        
        // Apply animations
        applyAnimations();
    }
    
    private void setupUI() {
        // Setup toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Setup navigation view
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        // Setup drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
    }
    
    private void setupDrawer() {
        // Create drawer toggle
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        
        // Add drawer listener
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        
        // Set custom drawer toggle color
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.accent_yellow));
    }
    
    private void applyAnimations() {
        // Animate welcome text
        TextView welcomeText = findViewById(R.id.welcome_text);
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(1500);
        welcomeText.startAnimation(fadeIn);
        
        // Animate subtitle text
        TextView subtitleText = findViewById(R.id.subtitle_text);
        Animation slideUp = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slideUp.setStartOffset(300);
        slideUp.setDuration(1000);
        subtitleText.startAnimation(slideUp);
    }
    
    @Override
    public void onBackPressed() {
        // Close drawer if open when back is pressed
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks
        int id = item.getItemId();
        Intent intent = null;
        
        // Handle navigation based on selected item
        if (id == R.id.nav_home) {
            // Already on home screen, just close drawer
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        } 
        // Algorithm activities
        else if (id == R.id.nav_fibonacci) {
            intent = new Intent(this, FibonacciActivity.class);
        }
        else if (id == R.id.nav_collatz) {
            intent = new Intent(this, CollatzActivity.class);
        }
        else if (id == R.id.nav_euclidean) {
            intent = new Intent(this, EuclideanActivity.class);
        }
        else if (id == R.id.nav_lucas) {
            intent = new Intent(this, LucasActivity.class);
        }
        else if (id == R.id.nav_tribonacci) {
            intent = new Intent(this, TribonacciActivity.class);
        }
        // Other activities
        else if (id == R.id.nav_settings) {
            intent = new Intent(this, SettingsActivity.class);
        } 
        else if (id == R.id.nav_about) {
            intent = new Intent(this, AboutActivity.class);
        }
        
        // Start activity if intent was created
        if (intent != null) {
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        
        // Close the drawer after handling the action
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}