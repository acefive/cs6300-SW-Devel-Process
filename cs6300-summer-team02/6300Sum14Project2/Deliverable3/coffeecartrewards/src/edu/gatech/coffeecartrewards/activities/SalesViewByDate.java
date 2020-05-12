package edu.gatech.coffeecartrewards.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.model.Customer;
import edu.gatech.coffeecartrewards.model.Sale;
import java.util.ArrayList;
import java.util.List;


public class SalesViewByDate extends ListActivity {
	
	Customer saleCustObj;
	String clickeddate;
	List<String> bday_list = new ArrayList<String>();
	List<String> cardnum_list = new ArrayList<String>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_view_by_date);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
        	
        	//get the date that was clicked on
            clickeddate = extras.getString("Date");
            getActionBar().setTitle("Sales for " + clickeddate);

            //initialize the list that will hold all the sale dates
            List<String> listOfCustSales = new ArrayList<String>();
            
            //get all the sales
            List<Sale> sales = Sale.findByDate(clickeddate);

            setListAdapter(new ArrayAdapter<Sale>(this,
                    android.R.layout.simple_list_item_1, sales));
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, SalesViewLineItems.class);

        Sale sale = (Sale) l.getItemAtPosition(position);
        intent.putExtra("id", sale.id);

        startActivity(intent);
    }


}