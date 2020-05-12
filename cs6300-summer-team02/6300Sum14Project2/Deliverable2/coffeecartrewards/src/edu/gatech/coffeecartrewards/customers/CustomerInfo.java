package edu.gatech.coffeecartrewards.customers;

import android.app.Activity;
import android.os.Bundle;
import edu.gatech.coffeecartrewards.R;

public class CustomerInfo extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("Customer Info: Alex Smith");
        setContentView(R.layout.customer_info);

    }
}