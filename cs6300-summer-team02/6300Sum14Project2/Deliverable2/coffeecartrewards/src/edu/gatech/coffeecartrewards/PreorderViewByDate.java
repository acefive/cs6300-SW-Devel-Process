package edu.gatech.coffeecartrewards;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import edu.gatech.coffeecartrewards.db.MainDAO;
import edu.gatech.coffeecartrewards.db.Preorder;

import java.util.List;

public class PreorderViewByDate extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preorder_view_by_date);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String date = extras.getString("Date");
            getActionBar().setTitle("Preorders for " + date);
            MainDAO datasource = new MainDAO(this);
            datasource.open();
            List<Preorder> orders = datasource.findAllPreordersByDate(date);
            datasource.close();
            setListAdapter(new ArrayAdapter<Preorder>(this,
                    android.R.layout.simple_list_item_1, orders));
        }

    }
}