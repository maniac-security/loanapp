package com.example.loanapp;

import android.content.Intent; // For navigating between activities
import android.os.Bundle; // For managing activity lifecycle
import android.view.View; // For handling UI interactions
import android.widget.EditText; // EditText UI component for input
import android.widget.Toast; // For displaying brief messages to the user
import androidx.appcompat.app.AppCompatActivity; // Base class for Android activities

// Activity class for the login screen
public class LoginActivity extends AppCompatActivity {
    private EditText etAdminCode; // EditText for entering the admin code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call superclass method to handle activity creation
        setContentView(R.layout.activity_login); // Set the layout for this activity

        // Initialize the EditText component for admin code input
        etAdminCode = findViewById(R.id.etAdminCode);

        // Set a click listener for the "Login" button
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin(); // Validate the admin code when the button is clicked
            }
        });
    }

    // Method to validate the entered admin code
    private void validateLogin() {
        // Get the admin code entered by the user, trimming any leading/trailing spaces
        String adminCode = etAdminCode.getText().toString().trim();

        // Check if the entered admin code matches the expected value
        if (adminCode.equals("1234")) { // Example hardcoded admin code
            // Start the AdminActivity if the code is correct
            startActivity(new Intent(this, AdminActivity.class));
            finish(); // Close the login activity to prevent returning to it
        } else {
            // Show a Toast message if the entered code is invalid
            Toast.makeText(this, "Invalid Code", Toast.LENGTH_SHORT).show();
        }
    }
}
