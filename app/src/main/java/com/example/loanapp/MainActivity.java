package com.example.loanapp;

import android.content.Intent; // For navigating between activities
import android.content.SharedPreferences; // For storing and retrieving application settings
import android.os.Bundle; // For handling activity lifecycle events
import android.view.View; // For handling UI interactions
import android.widget.Switch; // UI component for toggle switches
import androidx.appcompat.app.AppCompatActivity; // Base class for Android activities
import androidx.appcompat.app.AppCompatDelegate; // For managing dark mode settings

// Main activity class, serving as the entry point of the app
public class MainActivity extends AppCompatActivity {
    private Switch switchDarkMode; // Switch UI component for toggling dark mode
    private SharedPreferences sharedPreferences; // SharedPreferences for storing app settings

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call superclass method to handle activity setup
        setContentView(R.layout.activity_main); // Set the layout for this activity

        // Initialize the dark mode switch UI component
        switchDarkMode = findViewById(R.id.switchDarkMode);

        // Access shared preferences to load saved settings
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        // Load the saved dark mode state from SharedPreferences
        boolean isDarkMode = sharedPreferences.getBoolean("DarkMode", false);

        // Set the state of the dark mode switch based on the saved preference
        switchDarkMode.setChecked(isDarkMode);

        // Apply the dark mode setting to the app
        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        // Set a listener for changes in the dark mode switch state
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the updated dark mode state in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("DarkMode", isChecked);
            editor.apply();

            // Apply the updated dark mode setting to the app
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
        });

        // Set a click listener for the "User" button
        findViewById(R.id.btnUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the UserActivity when the "User" button is clicked
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });

        // Set a click listener for the "Admin" button
        findViewById(R.id.btnAdmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the LoginActivity when the "Admin" button is clicked
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}
