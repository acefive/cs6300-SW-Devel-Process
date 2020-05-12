package edu.gatech.coffeecartrewards;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.gatech.coffeecartrewards.db.Preorder;

import java.util.ArrayList;
import java.util.List;


public class SalesViewByDate extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_view_by_date);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String date = extras.getString("Date");
            getActionBar().setTitle("Sales for " + date);

            List<String> sales = new ArrayList<String>();
            sales.add("1. Reece Karge - $4.75");
            setListAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, sales));
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String sale = (String) l.getItemAtPosition(position);
        Intent intent = new Intent(this, SalesViewLineItems.class);
        intent.putExtra("Sale", sale);
        startActivity(intent);
    }


}