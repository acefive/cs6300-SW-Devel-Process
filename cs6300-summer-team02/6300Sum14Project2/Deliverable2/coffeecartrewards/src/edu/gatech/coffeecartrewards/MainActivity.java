package edu.gatech.coffeecartrewards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import edu.gatech.coffeecartrewards.customers.CustomersList;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("Coffee Cart Rewards");

        setContentView(R.layout.main);
    }

    public void manageCustomers(View view){
        Intent intent = new Intent(this, CustomersList.class);
        startActivity(intent);
    }

    public void viewPreorders(View view){
        Intent intent = new Intent(this, PreOrdersList.class);
        startActivity(intent);
    }

    public void viewSales(View view){
        Intent intent = new Intent(this, SalesList.class);
        startActivity(intent);
    }
}
