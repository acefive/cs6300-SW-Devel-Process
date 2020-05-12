package edu.gatech.coffeecartrewards.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.model.Preorder;
import java.util.List;

public class PreorderViewByDate extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preorder_view_by_date);
        Bundle extras = getIntent().getExtras();
        String date = extras.getString("Date");
        getActionBar().setTitle("Preorders for " + date);
        List<Preorder> preorders = Preorder.findByDate(date);
        setListAdapter(new ArrayAdapter<Preorder>(this, android.R.layout.simple_list_item_1, preorders));
    }
}