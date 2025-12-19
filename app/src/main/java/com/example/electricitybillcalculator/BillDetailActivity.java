package com.example.electricitybillcalculator;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BillDetailActivity extends AppCompatActivity {

    private TextView textViewMonth, textViewUnits, textViewRebate,
            textViewTotalCharges, textViewFinalCost;
    private int billId;
    private Button buttonEdit, buttonDelete;
    private Bill bill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();
        loadBillDetails();

        buttonEdit.setOnClickListener(v -> editBill());
        buttonDelete.setOnClickListener(v -> confirmDelete());

    }

    private void initializeViews() {
        textViewMonth = findViewById(R.id.textViewMonth);
        textViewUnits = findViewById(R.id.textViewUnits);
        textViewRebate = findViewById(R.id.textViewRebate);
        textViewTotalCharges = findViewById(R.id.textViewTotalCharges);
        textViewFinalCost = findViewById(R.id.textViewFinalCost);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonDelete = findViewById(R.id.buttonDelete);

    }

    private void loadBillDetails() {
        billId = getIntent().getIntExtra("BILL_ID", -1);

        if (billId != -1) {
            billId = getIntent().getIntExtra("BILL_ID", -1);

            if (billId != -1) {
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                bill = dbHelper.getBill(billId);

                textViewMonth.setText(bill.getMonth());
                textViewUnits.setText(bill.getUnits() + " kWh");
                textViewRebate.setText(String.format("%.1f%%", bill.getRebate()));
                textViewTotalCharges.setText("RM " + String.format("%.2f", bill.getTotalCharges()));
                textViewFinalCost.setText("RM " + String.format("%.2f", bill.getFinalCost()));
            }
            else {
                // Handle null bill case
                Toast.makeText(this, "Bill not found", Toast.LENGTH_SHORT).show();
                finish(); // Close activity if bill doesn't exist
            }
        } else {
            // Handle invalid bill ID case
            Toast.makeText(this, "Invalid bill ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void editBill() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("EDIT_BILL", true);
        intent.putExtra("BILL_ID", billId);
        startActivity(intent);
        finish();
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Bill")
                .setMessage("Are you sure you want to delete this bill?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    DatabaseHelper dbHelper = new DatabaseHelper(this);
                    dbHelper.deleteBill(billId);
                    Toast.makeText(this, "Bill deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}