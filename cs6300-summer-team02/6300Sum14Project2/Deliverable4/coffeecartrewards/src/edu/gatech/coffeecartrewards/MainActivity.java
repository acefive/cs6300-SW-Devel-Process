package edu.gatech.coffeecartrewards;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import edu.gatech.coffeecartrewards.activities.PreOrdersList;
import edu.gatech.coffeecartrewards.activities.SalesList;
import edu.gatech.coffeecartrewards.activities.CustomersList;
import edu.gatech.coffeecartrewards.model.Item;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DAO.createDataIfNonExistent(this);
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
