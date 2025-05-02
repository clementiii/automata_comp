package com.example.automatacomp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PascalTriangleActivity extends AppCompatActivity {
    private TextInputEditText inputEditText;
    private MaterialButton generateButton;
    private TextView resultTextView;
    private TextInputLayout inputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pascal_triangle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.pascal_triangle_title);
        }

        inputEditText = findViewById(R.id.pascal_input);
        generateButton = findViewById(R.id.btn_generate);
        resultTextView = findViewById(R.id.result_text);
        inputLayout = findViewById(R.id.input_layout);

        generateButton.setOnClickListener(v -> generatePascalTriangle());
        applyAnimations();
    }

    private void applyAnimations() {
        inputLayout.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        generateButton.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    }

    private void generatePascalTriangle() {
        String input = inputEditText.getText().toString().trim();
        if (TextUtils.isEmpty(input)) {
            inputLayout.setError("Please enter a number");
            return;
        }
        try {
            int rows = Integer.parseInt(input);
            if (rows < 1) {
                inputLayout.setError("Number must be >= 1");
                return;
            }
            inputLayout.setError(null);
            StringBuilder triangle = new StringBuilder();
            for (int i = 0; i < rows; i++) {
                // Add leading spaces for triangle shape
                for (int s = 0; s < rows - i - 1; s++) triangle.append("  ");
                int num = 1;
                for (int j = 0; j <= i; j++) {
                    triangle.append(num).append("   ");
                    num = num * (i - j) / (j + 1);
                }
                triangle.append("\n");
            }
            resultTextView.setText(triangle.toString());
        } catch (NumberFormatException e) {
            inputLayout.setError("Invalid number format");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
