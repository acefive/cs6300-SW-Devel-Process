package edu.gatech.coffeecartrewards;

import android.app.Activity;
import android.os.Bundle;


public class SalesViewLineItems extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_view_line_items);
        getActionBar().setTitle("Info for sale #1");
    }
}