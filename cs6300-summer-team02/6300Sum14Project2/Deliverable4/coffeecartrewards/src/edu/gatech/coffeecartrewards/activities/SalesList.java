package edu.gatech.coffeecartrewards.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.model.Sale;

public class SalesList extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("Sales");
        setContentView(R.layout.sales_list);
        
        java.util.List<String> results = Sale.getAllDatesSorted();
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String date = (String) l.getItemAtPosition(position);
        Intent intent = new Intent(this, SalesViewByDate.class);
        intent.putExtra("Date", date);
        startActivity(intent);
    }
}