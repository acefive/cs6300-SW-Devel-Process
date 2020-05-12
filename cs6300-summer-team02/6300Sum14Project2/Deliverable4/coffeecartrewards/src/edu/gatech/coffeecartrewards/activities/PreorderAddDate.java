package edu.gatech.coffeecartrewards.activities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.R.layout;
import edu.gatech.coffeecartrewards.model.Customer;
import edu.gatech.coffeecartrewards.model.Item;
import edu.gatech.coffeecartrewards.model.Preorder;
import edu.gatech.coffeecartrewards.model.Sale;
import edu.gatech.coffeecartrewards.model.SaleLine;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CheckBox;

import java.text.DecimalFormat;
import java.text.ParseException;



public class PreorderAddDate extends ListActivity {

    private Item item;
    private Customer customer;
    private String strDate;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		customer = Customer.findById(Customer.class, extras.getLong("CustomerID"));
		item = Item.findById(Item.class, extras.getLong("ItemID"));
        getActionBar().setTitle("Preorder Dessert (2/2)");
        getActionBar().setSubtitle(String.format("%s - %s", customer.getCustName(), item.getItemName()));
        
		setContentView(R.layout.preorder_add_date);
		
		loadList();
	}
	
	public void select(View view) {
		try {
			Date date = Preorder.toDate(strDate);
			new Preorder(this, date, item, customer).save();
			loadList();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        strDate = (String) l.getItemAtPosition(position);
        
		((Button) findViewById(R.id.select)).setEnabled(true);
    }
    
    private void loadList() {
    	((Button)findViewById(R.id.select)).setEnabled(false);
        List<String> availableDates = Preorder.findOpenPreorderDates(item.getBestSeller());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, availableDates);
        setListAdapter(adapter);
    }
}
