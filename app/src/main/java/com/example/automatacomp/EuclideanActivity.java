package com.example.automatacomp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.textfield.TextInputLayout;

public class EuclideanActivity extends AppCompatActivity {

    private EditText firstInputEditText;
    private EditText secondInputEditText;
    private Button calculateButton;
    private TextView resultTextView;
    private TextInputLayout firstInputLayout;
    private TextInputLayout secondInputLayout;
    private NestedScrollView resultScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_euclidean);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Euclidean Algorithm");
        }

        // Initialize views
        firstInputEditText = findViewById(R.id.first_input);
        secondInputEditText = findViewById(R.id.second_input);
        calculateButton = findViewById(R.id.calculate_button);
        resultTextView = findViewById(R.id.result_text);
        firstInputLayout = findViewById(R.id.first_input_layout);
        secondInputLayout = findViewById(R.id.second_input_layout);
        resultScrollView = findViewById(R.id.result_scroll_view);

        // Setup button click listener
        calculateButton.setOnClickListener(v -> calculateGCD());

        // Apply animations
        applyAnimations();
    }

    private void applyAnimations() {
        // Animate title and form elements
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(1200);
        firstInputLayout.startAnimation(fadeIn);
        secondInputLayout.startAnimation(fadeIn);
        calculateButton.startAnimation(fadeIn);
    }

    private void calculateGCD() {
        // Get input values
        String firstInput = firstInputEditText.getText().toString().trim();
        String secondInput = secondInputEditText.getText().toString().trim();
        
        if (TextUtils.isEmpty(firstInput)) {
            firstInputLayout.setError("Please enter a number");
            return;
        }
        
        if (TextUtils.isEmpty(secondInput)) {
            secondInputLayout.setError("Please enter a number");
            return;
        }
        
        try {
            int a = Integer.parseInt(firstInput);
            int b = Integer.parseInt(secondInput);
            
            if (a <= 0) {
                firstInputLayout.setError("Number must be positive");
                return;
            }
            
            if (b <= 0) {
                secondInputLayout.setError("Number must be positive");
                return;
            }
            
            // Clear any previous errors
            firstInputLayout.setError(null);
            secondInputLayout.setError(null);
            
            // Calculate GCD using Euclidean algorithm and show steps
            StringBuilder result = new StringBuilder("Euclidean Algorithm Steps:\n\n");
            
            int originalA = a;
            int originalB = b;
            
            // Store steps for display
            result.append("Finding GCD of ").append(originalA).append(" and ").append(originalB).append(":\n\n");
            
            // Calculate GCD using Euclidean Algorithm with step tracking
            while (b != 0) {
                result.append("gcd(").append(a).append(", ").append(b).append(") = ");
                int remainder = a % b;
                result.append("gcd(").append(b).append(", ").append(remainder).append(")\n");
                a = b;
                b = remainder;
            }
            
            // Display final result
            result.append("\nResult: gcd(").append(originalA).append(", ").append(originalB).append(") = ").append(a);
            
            // Display result with animation
            resultTextView.setText(result.toString());
            resultScrollView.setVisibility(View.VISIBLE);
            Animation slideIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
            slideIn.setDuration(500);
            resultScrollView.startAnimation(slideIn);
            
        } catch (NumberFormatException e) {
            if (!TextUtils.isDigitsOnly(firstInput)) {
                firstInputLayout.setError("Invalid number format");
            }
            if (!TextUtils.isDigitsOnly(secondInput)) {
                secondInputLayout.setError("Invalid number format");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle back button
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Apply custom transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}