package com.example.electricitybillcalculator;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BillDetailActivity extends AppCompatActivity {
    private TextView textViewMonth, textViewUnits, textViewRebate,
            textViewTotalCharges, textViewFinalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        getSupportActionBar().setTitle("Bill Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();
        loadBillDetails();
    }

    private void initializeViews() {
        textViewMonth = findViewById(R.id.textViewMonth);
        textViewUnits = findViewById(R.id.textViewUnits);
        textViewRebate = findViewById(R.id.textViewRebate);
        textViewTotalCharges = findViewById(R.id.textViewTotalCharges);
        textViewFinalCost = findViewById(R.id.textViewFinalCost);
    }

    private void loadBillDetails() {
        int billId = getIntent().getIntExtra("BILL_ID", -1);
        if (billId != -1) {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            Bill bill = dbHelper.getBill(billId);

            textViewMonth.setText(bill.getMonth());
            textViewUnits.setText((bill.getUnits()) + " kWh");
            textViewRebate.setText(String.format("%.1f%%", bill.getRebate()));
            textViewTotalCharges.setText("RM " + String.format("%.2f",bill.getTotalCharges()));
            textViewFinalCost.setText("RM " + String.format("%.2f", bill.getFinalCost()));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
