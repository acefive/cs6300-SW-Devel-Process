package edu.gatech.coffeecartrewards.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.model.Customer;
import edu.gatech.coffeecartrewards.model.Sale;
import edu.gatech.coffeecartrewards.model.SaleLine;

public class CustomerInfo extends Activity {
	private Customer customer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.customer_info);
        if(extras != null){
            //this is an Edit Customer rather than add
         	System.out.println("Customer Id out:"+ extras.getLong("CustomerID"));
         	customer = Customer.findById(Customer.class,extras.getLong("CustomerID"));
             getActionBar().setTitle("Customer Info: "+customer.getCustName());
             ((TextView) findViewById(R.id.customerstatus)).setText(customer.getGoldMember());
             ((TextView) findViewById(R.id.montlypoints)).setText(customer.get30DayPointsTotal());
             ((TextView) findViewById(R.id.alltimepoinst)).setText(customer.getPointsTotal());
         }

    }
    public void viewPurchaseHistory(View view){
        Intent intent = new Intent(this,PurchaseHistory.class);
        intent.putExtra("CustomerID", customer.getId());
        startActivity(intent);
    }
}