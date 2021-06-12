package com.dfz.sqldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_add, btn_allView;
    EditText et_name, et_age;
    Switch sw_active;
    ListView lv_customerView;
    ArrayAdapter customerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_allView = findViewById(R.id.btn_viewAll);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        sw_active = findViewById(R.id.sw_active);
        lv_customerView = findViewById(R.id.lv_customerView);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);


        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryOne());
        lv_customerView.setAdapter(customerArrayAdapter);



        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel customerModel;
                try {
                    customerModel = new CustomerModel(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), sw_active.isChecked());

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                    boolean success = dataBaseHelper.addOne(customerModel);
                    Toast.makeText(MainActivity.this, "Success: " + success, Toast.LENGTH_SHORT).show();

                    ArrayAdapter customerArrayAdapter;
                    customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryOne());
                    lv_customerView.setAdapter(customerArrayAdapter);

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_allView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper db = new DataBaseHelper(MainActivity.this);
                List<CustomerModel> everyOne = db.getEveryOne();
                System.out.println(everyOne.toString());
            }
        });

        lv_customerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedCustomer);
                ShowCustomerOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this, "Item eliminado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowCustomerOnListView(DataBaseHelper dbHelper2){
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dbHelper2.getEveryOne());
        lv_customerView.setAdapter(customerArrayAdapter);
    }
}