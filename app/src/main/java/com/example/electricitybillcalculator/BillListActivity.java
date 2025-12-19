package com.example.electricitybillcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

public class BillListActivity extends AppCompatActivity {
    private ListView listViewBills;
    private TextView textViewEmpty;
    private DatabaseHelper dbHelper;
    private List<Bill> billList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);

        getSupportActionBar().setTitle("Saved Bills");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();
        dbHelper = new DatabaseHelper(this);
        loadBills();
    }

    private void initializeViews() {
        listViewBills = findViewById(R.id.listViewBills);
        textViewEmpty = findViewById(R.id.textViewEmpty);

        listViewBills.setOnItemClickListener(new
                                                     AdapterView.OnItemClickListener() {
                                                         @Override
                                                         public void onItemClick(AdapterView<?> parent, View view, int
                                                                 position, long id) {
                                                             Bill bill = billList.get(position);
                                                             Intent intent = new Intent(BillListActivity.this,
                                                                     BillDetailActivity.class);
                                                             intent.putExtra("BILL_ID", bill.getId());
                                                             startActivity(intent);
                                                         }
                                                     });
    }

    private void loadBills() {
        billList = dbHelper.getAllBills();

        // Reverse the list to show newest first
        Collections.reverse(billList);

        if (billList.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
            listViewBills.setVisibility(View.GONE);
        } else {
            textViewEmpty.setVisibility(View.GONE);
            listViewBills.setVisibility(View.VISIBLE);

            String[] billData = new String[billList.size()];
            for (int i = 0; i < billList.size(); i++) {
                Bill bill = billList.get(i);
                billData[i] = bill.getMonth() + " - RM " + String.format("%.2f",
                        bill.getFinalCost());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, billData);
            listViewBills.setAdapter(adapter);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadBills();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}