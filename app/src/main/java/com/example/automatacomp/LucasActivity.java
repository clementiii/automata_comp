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

import java.math.BigInteger;

public class LucasActivity extends AppCompatActivity {

    private EditText inputEditText;
    private Button calculateButton;
    private TextView resultTextView;
    private TextInputLayout inputLayout;
    private NestedScrollView resultScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucas);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Lucas Sequence");
        }

        // Initialize views
        inputEditText = findViewById(R.id.lucas_input);
        calculateButton = findViewById(R.id.calculate_button);
        resultTextView = findViewById(R.id.result_text);
        inputLayout = findViewById(R.id.input_layout);
        resultScrollView = findViewById(R.id.result_scroll_view);

        // Setup button click listener
        calculateButton.setOnClickListener(v -> calculateLucas());

        // Apply animations
        applyAnimations();
    }

    private void applyAnimations() {
        // Animate title and form elements
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(1200);
        inputLayout.startAnimation(fadeIn);
        calculateButton.startAnimation(fadeIn);
    }

    private void calculateLucas() {
        // Get input value
        String input = inputEditText.getText().toString().trim();
        
        if (TextUtils.isEmpty(input)) {
            inputLayout.setError("Please enter a number");
            return;
        }
        
        try {
            int n = Integer.parseInt(input);
            
            if (n < 3) {
                inputLayout.setError("Number must be >= 3");
                return;
            }
            
            inputLayout.setError(null); // Clear any previous error
            
            // Calculate and display Lucas sequence
            StringBuilder result = new StringBuilder("Lucas Numbers:\n");
            
            // Using BigInteger to handle large Lucas numbers
            BigInteger a = BigInteger.valueOf(2);
            BigInteger b = BigInteger.valueOf(1);
            
            result.append(a).append(", ").append(b);
            
            for (int i = 2; i < n; i++) {
                BigInteger next = a.add(b);
                result.append(", ").append(next);
                a = b;
                b = next;
                
                // Add line break for better readability
                if ((i + 1) % 5 == 0) {
                    result.append("\n");
                }
            }
            
            // Display result with animation
            resultTextView.setText(result.toString());
            resultScrollView.setVisibility(View.VISIBLE);
            Animation slideIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
            slideIn.setDuration(500);
            resultScrollView.startAnimation(slideIn);
            
        } catch (NumberFormatException e) {
            inputLayout.setError("Invalid number format");
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