package com.example.loanapp;

import android.content.Intent; // Add this import statement
import android.content.SharedPreferences; // For storing and retrieving key-value data
import android.os.Bundle; // For managing activity lifecycle
import android.view.View; // For handling UI interactions
import android.widget.*; // For using widgets like EditText, RadioGroup, and Toast
import androidx.appcompat.app.AppCompatActivity; // Base class for Android activities
import androidx.appcompat.app.AlertDialog;
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
        int selectedBrandId = rgTabletBrand.getCheckedRadioButtonId();
        int selectedCableId = rgCableType.getCheckedRadioButtonId();

        if (selectedBrandId == -1 || etBorrowerName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String tabletBrand = ((RadioButton) findViewById(selectedBrandId)).getText().toString();
        String cableType = selectedCableId != -1 ? ((RadioButton) findViewById(selectedCableId)).getText().toString() : "None";
        String borrowerName = etBorrowerName.getText().toString().trim();
        String contactInfo = etContactInfo.getText().toString().trim();
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String loanData = tabletBrand + "," + cableType + "," + borrowerName + "," + contactInfo + "," + dateTime;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(dateTime, loanData);
        editor.apply();

        showReceiptDialog(tabletBrand, cableType, borrowerName, contactInfo, dateTime);
    }

    private void showReceiptDialog(String tabletBrand, String cableType, String borrowerName, String contactInfo, String dateTime) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_receipt, null);

        TextView tvReceiptTabletBrand = dialogView.findViewById(R.id.tvReceiptTabletBrand);
        TextView tvReceiptCableType = dialogView.findViewById(R.id.tvReceiptCableType);
        TextView tvReceiptBorrowerName = dialogView.findViewById(R.id.tvReceiptBorrowerName);
        TextView tvReceiptContactInfo = dialogView.findViewById(R.id.tvReceiptContactInfo);
        TextView tvReceiptDate = dialogView.findViewById(R.id.tvReceiptDate);

        tvReceiptTabletBrand.setText("Tablet Brand: " + tabletBrand);
        tvReceiptCableType.setText("Cable Type: " + cableType);
        tvReceiptBorrowerName.setText("Borrower Name: " + borrowerName);
        tvReceiptContactInfo.setText("Contact Info: " + contactInfo);
        tvReceiptDate.setText("Date: " + dateTime);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.btnCloseReceipt).setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(UserActivity.this, MainActivity.class));
            finish();
        });

        dialog.show();
    }
}