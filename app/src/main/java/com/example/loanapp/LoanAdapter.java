package com.example.loanapp;

import android.content.Context; // For accessing application context
import android.view.LayoutInflater; // For inflating XML layout files
import android.view.View; // Represents the UI components in Android
import android.view.ViewGroup; // Represents a group of UI components
import android.widget.ArrayAdapter; // Base class for creating custom adapters
import android.widget.TextView; // TextView UI component for displaying text

import java.util.List; // For handling lists of data

// Custom adapter class for displaying loans in a ListView
public class LoanAdapter extends ArrayAdapter<String> {
    // Constructor for initializing the adapter with context and loan data
    public LoanAdapter(Context context, List<String> loans) {
        super(context, 0, loans); // Call the superclass constructor with context and data
    }

    // Override the getView method to customize how each list item is displayed
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused; if not, inflate a new view
        if (convertView == null) {
            // Inflate the layout for an individual list item
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_loan, parent, false);
        }

        // Get the current loan string from the data source
        String loan = getItem(position);
        // Split the loan string into individual parts (assuming CSV format)
        String[] parts = loan.split(",");

        // Get references to the TextView components in the layout
        TextView tvLoanBrand = convertView.findViewById(R.id.tvLoanBrand); // TextView for loan brand
        TextView tvLoanCable = convertView.findViewById(R.id.tvLoanCable); // TextView for loan cable type
        TextView tvLoanBorrower = convertView.findViewById(R.id.tvLoanBorrower); // TextView for borrower name
        TextView tvLoanDate = convertView.findViewById(R.id.tvLoanDate); // TextView for loan date

        // Populate the TextViews with the appropriate data from the loan parts
        tvLoanBrand.setText(parts[0]); // Set the brand text
        tvLoanCable.setText(parts[1]); // Set the cable type text
        tvLoanBorrower.setText(parts[2]); // Set the borrower name text
        tvLoanDate.setText(parts[4]); // Set the loan date text

        // Return the completed view to be displayed in the ListView
        return convertView;
    }
}
