package com.example.loanapp;

import android.content.Context; // For accessing application context
import android.content.SharedPreferences; // For storing and retrieving key-value data

// Helper class for managing SharedPreferences operations
public class SharedPreferencesHelper {
    private static final String PREF_NAME = "LoanAppPreferences"; // Name of the SharedPreferences file
    private SharedPreferences sharedPreferences; // Instance of SharedPreferences for managing data

    // Constructor to initialize SharedPreferences with the application context
    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        // MODE_PRIVATE ensures that the preferences are accessible only to this application
    }

    // Method to save a String value in SharedPreferences
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit(); // Open an editor for editing preferences
        editor.putString(key, value); // Save the string value with the specified key
        editor.apply(); // Apply changes asynchronously
    }

    // Method to retrieve a String value from SharedPreferences
    public String getString(String key) {
        return sharedPreferences.getString(key, null);
        // Retrieve the string value associated with the key, or return null if not found
    }

    // Method to save an integer value in SharedPreferences
    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit(); // Open an editor for editing preferences
        editor.putInt(key, value); // Save the integer value with the specified key
        editor.apply(); // Apply changes asynchronously
    }

    // Method to retrieve an integer value from SharedPreferences
    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
        // Retrieve the integer value associated with the key, or return -1 if not found
    }

    // Additional methods for saving and retrieving other data types can be added here
    // e.g., saveBoolean, getBoolean, saveFloat, getFloat, etc.
}
