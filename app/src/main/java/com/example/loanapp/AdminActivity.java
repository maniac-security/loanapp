package com.example.loanapp;

// Import necessary Android libraries
import android.content.SharedPreferences; // For managing key-value data storage
import android.os.Bundle; // For handling activity lifecycle events
import android.view.View; // For handling UI views
import android.widget.*; // For UI widgets such as ListView, EditText, RadioGroup
import androidx.appcompat.app.AppCompatActivity; // Base class for activities
import java.util.*; // For using List, Map, and other collections

// Main activity class for managing loans in the admin interface
public class AdminActivity extends AppCompatActivity {
    // Declare UI components and variables
    private ListView listViewLoans; // ListView to display loans
    private EditText etFilterDate; // EditText for entering date filter
    private RadioGroup rgFilterBrand, rgFilterCableType; // RadioGroups for brand and cable type filters
    private SharedPreferences sharedPreferences; // For storing and retrieving loan data
    private LoanAdapter adapter; // Custom adapter for displaying loans in the ListView
    private List<String> loanList, filteredList; // Lists to hold all loans and filtered loans

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call superclass method to handle activity setup
        setContentView(R.layout.activity_admin); // Set the layout for this activity

        // Initialize UI components
        listViewLoans = findViewById(R.id.listViewLoans);
        etFilterDate = findViewById(R.id.etFilterDate);
        rgFilterBrand = findViewById(R.id.rgFilterBrand);
        rgFilterCableType = findViewById(R.id.rgFilterCableType);

        // Access shared preferences for loan data
        sharedPreferences = getSharedPreferences("LoansData", MODE_PRIVATE);

        // Initialize lists for loan data
        loanList = new ArrayList<>(); // List for storing all loans
        filteredList = new ArrayList<>(); // List for storing filtered loans

        // Set up the custom adapter for the ListView
        adapter = new LoanAdapter(this, filteredList);
        listViewLoans.setAdapter(adapter);

        // Load loans from SharedPreferences
        loadLoans();

        // Set up a click listener for the "Filter" button
        findViewById(R.id.btnFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyFilter(); // Apply filters based on user input
            }
        });

        // Set up an item click listener for deleting loans from the ListView
        listViewLoans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteLoan(position); // Delete the selected loan
            }
        });
    }

    // Load all loans from SharedPreferences and populate the lists
    private void loadLoans() {
        loanList.clear(); // Clear the existing list of loans
        filteredList.clear(); // Clear the filtered list

        // Retrieve all stored loans from SharedPreferences
        Map<String, ?> loans = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : loans.entrySet()) {
            loanList.add(entry.getValue().toString()); // Add each loan to the loan list
        }

        // Copy all loans to the filtered list initially
        filteredList.addAll(loanList);
        adapter.notifyDataSetChanged(); // Notify adapter about data changes
    }

    // Apply user-defined filters to the loan list
    private void applyFilter() {
        filteredList.clear(); // Clear the filtered list before applying new filters

        // Get the filter criteria from the UI
        String filterDate = etFilterDate.getText().toString().trim(); // Filter date
        int selectedBrandId = rgFilterBrand.getCheckedRadioButtonId(); // Selected brand radio button ID
        int selectedCableId = rgFilterCableType.getCheckedRadioButtonId(); // Selected cable type radio button ID

        // Get the selected brand and cable type texts, if selected
        String filterBrand = selectedBrandId != -1 ? ((RadioButton) findViewById(selectedBrandId)).getText().toString() : null;
        String filterCable = selectedCableId != -1 ? ((RadioButton) findViewById(selectedCableId)).getText().toString() : null;

        // Iterate through the loan list and apply filters
        for (String loan : loanList) {
            String[] parts = loan.split(","); // Split loan details into parts
            String loanBrand = parts[0]; // Extract brand from loan details
            String loanCable = parts[1]; // Extract cable type from loan details
            String loanDate = parts[4]; // Extract date from loan details

            boolean matches = true; // Flag to determine if the loan matches the filters

            // Apply date filter
            if (filterDate.length() > 0 && !loanDate.startsWith(filterDate)) {
                matches = false;
            }
            // Apply brand filter
            if (filterBrand != null && !loanBrand.equals(filterBrand)) {
                matches = false;
            }
            // Apply cable type filter
            if (filterCable != null && !loanCable.equals(filterCable)) {
                matches = false;
            }

            // Add the loan to the filtered list if it matches all criteria
            if (matches) {
                filteredList.add(loan);
            }
        }

        adapter.notifyDataSetChanged(); // Notify adapter about data changes
    }

    // Delete a loan from SharedPreferences and update the lists
    private void deleteLoan(int position) {
        String loanKey = filteredList.get(position).split(",")[4]; // Extract the unique key (e.g., date) from the loan
        sharedPreferences.edit().remove(loanKey).apply(); // Remove the loan from SharedPreferences
        loadLoans(); // Reload the updated list of loans
        adapter.notifyDataSetChanged(); // Notify adapter about data changes

        // Show a confirmation message to the user
        Toast.makeText(this, "Loan removed", Toast.LENGTH_SHORT).show();
    }
}
