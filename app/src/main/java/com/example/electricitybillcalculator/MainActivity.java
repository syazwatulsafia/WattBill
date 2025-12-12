package com.example.electricitybillcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerMonth;
    private EditText editTextUnits;
    private RadioGroup radioGroupRebate;
    private TextView textViewTotalCharges, textViewRebate, textViewFinalCost;
    private Button buttonCalculate, buttonSave, buttonViewBills, buttonAbout;
    private DatabaseHelper dbHelper;

    private double totalCharges = 0;
    private double finalCost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set custom title and colors
        getSupportActionBar().setTitle("Electricity Bill Calculator");
        getWindow().setStatusBarColor(Color.parseColor("#2196F3"));

        initializeViews();
        setupSpinner();
        dbHelper = new DatabaseHelper(this);

        buttonCalculate.setOnClickListener(v -> calculateBill());
        buttonSave.setOnClickListener(v -> saveBill());
        buttonViewBills.setOnClickListener(v -> viewBills());
        buttonAbout.setOnClickListener(v -> aboutPage());
    }

    private void initializeViews() {
        spinnerMonth = findViewById(R.id.spinnerMonth);
        editTextUnits = findViewById(R.id.editTextUnits);
        radioGroupRebate = findViewById(R.id.radioGroupRebate);
        textViewTotalCharges = findViewById(R.id.textViewTotalCharges);
        textViewRebate = findViewById(R.id.textViewRebate);
        textViewFinalCost = findViewById(R.id.textViewFinalCost);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonSave = findViewById(R.id.buttonSave);
        buttonViewBills = findViewById(R.id.buttonViewBills);
        buttonAbout = findViewById(R.id.buttonAbout);
    }

    private void setupSpinner() {
        String[] months = {"Select Month", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);
    }

    private void calculateBill() {
        if (!validateInput()) return;

        String selectedMonth = spinnerMonth.getSelectedItem().toString();
        double units = Double.parseDouble(editTextUnits.getText().toString());
        double rebate = getSelectedRebate();

        BillCalculator.BillCalculationResult result =
                BillCalculator.calculateBill(units, rebate);

        totalCharges = result.totalCharges;
        finalCost = result.finalCost;

        textViewTotalCharges.setText("RM " + String.format("%.2f",totalCharges));
        textViewRebate.setText(String.format("%.1f%%", rebate));
        textViewFinalCost.setText("RM " + String.format("%.2f", finalCost));

        buttonSave.setEnabled(true);
    }

    private boolean validateInput() {
        if (spinnerMonth.getSelectedItemPosition() == 0) {
            showError("Please select a month");
            return false;
        }

        String unitsText = editTextUnits.getText().toString();
        if (unitsText.isEmpty()) {
            showError("Please enter electricity units");
            return false;
        }

        try {
            double units = Double.parseDouble(unitsText);
            if (units <= 0) {
                showError("Units must be greater than 0");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Please enter valid number for units");
            return false;
        }

        if (radioGroupRebate.getCheckedRadioButtonId() == -1) {
            showError("Please select rebate percentage");
            return false;
        }

        return true;
    }

    private double getSelectedRebate() {
        int selectedId = radioGroupRebate.getCheckedRadioButtonId();
        if (selectedId == R.id.radioRebate0) return 0;
        if (selectedId == R.id.radioRebate1) return 1;
        if (selectedId == R.id.radioRebate2) return 2;
        if (selectedId == R.id.radioRebate3) return 3;
        if (selectedId == R.id.radioRebate4) return 4;
        if (selectedId == R.id.radioRebate5) return 5;
        return 0;
    }

    private void saveBill() {
        if (totalCharges == 0 || finalCost == 0) {
            showError("Please calculate bill first");
            return;
        }

        String month = spinnerMonth.getSelectedItem().toString();
        double units = Double.parseDouble(editTextUnits.getText().toString());
        double rebate = getSelectedRebate();

        Bill bill = new Bill(month, units, rebate, totalCharges, finalCost);
        long id = dbHelper.addBill(bill);

        if (id != -1) {
            Toast.makeText(this, "Bill saved successfully!", Toast.LENGTH_SHORT).show();
            resetForm();
        } else {
            showError("Failed to save bill");
        }
    }

    private void resetForm() {
        spinnerMonth.setSelection(0);
        editTextUnits.setText("");
        radioGroupRebate.clearCheck();
        textViewTotalCharges.setText("RM 0.00");
        textViewRebate.setText("0.0%");
        textViewFinalCost.setText("RM 0.00");
        buttonSave.setEnabled(false);
        totalCharges = 0;
        finalCost = 0;
    }

    private void viewBills() {
        Intent intent = new Intent(this, BillListActivity.class);
        startActivity(intent);
    }

    private void aboutPage() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}