package com.example.loanapp;

import android.content.SharedPreferences; // For storing and retrieving key-value data
import android.os.Bundle; // For managing activity lifecycle
import android.view.View; // For handling UI interactions
import android.widget.*; // For using widgets like EditText, RadioGroup, and Toast
import androidx.appcompat.app.AppCompatActivity; // Base class for Android activities

import java.text.SimpleDateFormat; // For formatting dates
import java.util.Date; // For getting the current date
import java.util.Locale; // For setting locale for date formatting

// Activity class for user loan registration
public class UserActivity extends AppCompatActivity {
    private EditText etBorrowerName, etContactInfo; // Input fields for borrower name and contact info
    private RadioGroup rgTabletBrand, rgCableType; // RadioGroups for selecting tablet brand and cable type
    private SharedPreferences sharedPreferences; // SharedPreferences for storing loan data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call superclass method to handle activity creation
        setContentView(R.layout.activity_user); // Set the layout for this activity

        // Initialize input fields and radio groups
        etBorrowerName = findViewById(R.id.etBorrowerName);
        etContactInfo = findViewById(R.id.etContactInfo);
        rgTabletBrand = findViewById(R.id.rgTabletBrand);
        rgCableType = findViewById(R.id.rgCableType);

        // Access SharedPreferences for storing loans
        sharedPreferences = getSharedPreferences("LoansData", MODE_PRIVATE);

        // Set a click listener for the "Save Loan" button
        findViewById(R.id.btnSaveLoan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLoan(); // Save the loan data when the button is clicked
            }
        });
    }

    // Method to save loan data to SharedPreferences
    private void saveLoan() {
        // Get the selected brand and cable type from the radio groups
        int selectedBrandId = rgTabletBrand.getCheckedRadioButtonId();
        int selectedCableId = rgCableType.getCheckedRadioButtonId();

        // Check if required fields are filled
        if (selectedBrandId == -1 || etBorrowerName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return; // Exit the method if validation fails
        }

        // Get the selected tablet brand and cable type as strings
        String tabletBrand = ((RadioButton) findViewById(selectedBrandId)).getText().toString();
        String cableType = selectedCableId != -1 ? ((RadioButton) findViewById(selectedCableId)).getText().toString() : "None";

        // Get borrower name and contact info from input fields
        String borrowerName = etBorrowerName.getText().toString().trim();
        String contactInfo = etContactInfo.getText().toString().trim();

        // Get the current date and time in the format "yyyy-MM-dd HH:mm:ss"
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Combine all loan data into a single string
        String loanData = tabletBrand + "," + cableType + "," + borrowerName + "," + contactInfo + "," + dateTime;

        // Save the loan data in SharedPreferences using the date and time as a unique key
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(dateTime, loanData); // Key: dateTime, Value: loanData
        editor.apply(); // Apply changes asynchronously

        // Show a confirmation message
        Toast.makeText(this, "Loan Registered", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity after saving the loan
    }
}
