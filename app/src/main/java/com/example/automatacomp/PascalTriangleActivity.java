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
            int n = Integer.parseInt(input);
            if (n < 1) {
                inputLayout.setError("Number must be greater than or equal to 1");
                return;
            }
            inputLayout.setError(null);
            
            // For input n, we need to show n+1 rows (rows 0 to n inclusive)
            // so for n=1, we show rows 0 and 1 (i.e., 1 and 1 1)
            int rows = n + 1;
            
            // Build triangle data first
            int[][] triangle = new int[rows][];
            int maxNum = 1;
            for (int i = 0; i < rows; i++) {
                triangle[i] = new int[i + 1];
                triangle[i][0] = triangle[i][i] = 1;
                for (int j = 1; j < i; j++) {
                    triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
                    if (triangle[i][j] > maxNum) maxNum = triangle[i][j];
                }
            }
            
            // Determine width for each number
            int numWidth = Integer.toString(maxNum).length() + 2;
            StringBuilder sb = new StringBuilder();
            
            // Explain the output
            sb.append("Pascal's Triangle for n=").append(n).append(":\n\n");
            
            for (int i = 0; i < rows; i++) {
                // Leading spaces for triangle shape
                for (int s = 0; s < rows - i - 1; s++) sb.append(" ".repeat(numWidth / 2));
                for (int j = 0; j <= i; j++) {
                    sb.append(String.format("%" + numWidth + "d", triangle[i][j]));
                }
                sb.append("\n");
            }
            resultTextView.setText(sb.toString());
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
