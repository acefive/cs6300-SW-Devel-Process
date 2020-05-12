package edu.gatech.coffeecartrewards.activities;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.model.Preorder;

public class PreOrdersList extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("Preorders");
        setContentView(R.layout.preorders_list);

        List<String> results = Preorder.findUniqueDates();
        setListAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, results));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String date = (String) l.getItemAtPosition(position);
        Intent intent = new Intent(this, PreorderViewByDate.class);
        intent.putExtra("Date", date);
        startActivity(intent);
    }
}