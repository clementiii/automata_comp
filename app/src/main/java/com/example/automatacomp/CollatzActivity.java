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

public class CollatzActivity extends AppCompatActivity {

    private EditText inputEditText;
    private Button calculateButton;
    private TextView resultTextView;
    private TextInputLayout inputLayout;
    private NestedScrollView resultScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collatz);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Collatz Sequence");
        }

        // Initialize views
        inputEditText = findViewById(R.id.collatz_input);
        calculateButton = findViewById(R.id.calculate_button);
        resultTextView = findViewById(R.id.result_text);
        inputLayout = findViewById(R.id.input_layout);
        resultScrollView = findViewById(R.id.result_scroll_view);

        // Setup button click listener
        calculateButton.setOnClickListener(v -> calculateCollatz());

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

    private void calculateCollatz() {
        // Get input value
        String input = inputEditText.getText().toString().trim();
        
        if (TextUtils.isEmpty(input)) {
            inputLayout.setError("Please enter a number");
            return;
        }
        
        try {
            int n = Integer.parseInt(input);
            
            // Fixed the constraint: Collatz can start with any positive integer
            if (n <= 0) {
                inputLayout.setError("Number must be positive");
                return;
            }
            
            inputLayout.setError(null); // Clear any previous error
            
            // Calculate and display Collatz sequence
            StringBuilder result = new StringBuilder("Collatz sequence starting with " + n + ":\n");
            result.append(n);
            
            int count = 0;
            while (n != 1) {
                if (n % 2 == 0) {
                    n /= 2;
                } else {
                    n = 3 * n + 1;
                }
                result.append(" → ").append(n);
                count++;
                
                // Add line break every 6 numbers for readability
                if (count % 6 == 0) {
                    result.append("\n");
                }
                
                // Safety check for very long sequences
                if (count > 1000) {
                    result.append("\n\n[Calculation stopped: sequence too long]");
                    break;
                }
            }
            
            result.append("\n\nSequence length: ").append(count + 1);
            
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