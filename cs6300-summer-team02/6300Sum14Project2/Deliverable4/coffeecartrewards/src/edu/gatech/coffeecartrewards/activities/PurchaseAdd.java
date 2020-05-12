package edu.gatech.coffeecartrewards.activities;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.R.layout;
import edu.gatech.coffeecartrewards.model.Customer;
import edu.gatech.coffeecartrewards.model.Item;
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



public class PurchaseAdd extends ListActivity {

    private Item item;
    private Customer customer;
    private double itemPriceForCustomer;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		customer = Customer.findById(Customer.class,extras.getLong("CustomerID"));
        getActionBar().setTitle("Purchase Product");
        getActionBar().setSubtitle(customer.getCustName());
        
		setContentView(R.layout.purchase_add);
		
		((Button)findViewById(R.id.productBuy)).setEnabled(false);
		((CheckBox) findViewById(R.id.refillCheckBox)).setEnabled(false);
        loadList();
	}
	
	public void buyItem(View view) {
        Calendar cal = Calendar.getInstance();
        Sale sale = new Sale(this, cal.getTime(), customer);
        sale.save();
        
        SaleLine saleLine = new SaleLine(this, itemPriceForCustomer, item, sale);
        saleLine.save();
	}
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        item = (Item) l.getItemAtPosition(position);
        
        CheckBox refillCheckBox = (CheckBox) findViewById(R.id.refillCheckBox);
        refillCheckBox.setChecked(false);
        if (item.isRefillable()) {
        	refillCheckBox.setEnabled(true);
        } else {
        	refillCheckBox.setEnabled(false);
        }
        
        updateTotals();
        
		((Button) findViewById(R.id.productBuy)).setEnabled(true);
    }
    
    public void refillCheckBoxCB(View view) {
    	updateTotals();
    }
    
    private void updateTotals() {
    	boolean isRefill = ((CheckBox) findViewById(R.id.refillCheckBox)).isChecked();
    	
        itemPriceForCustomer = item.getPrice(isRefill, customer.isGoldMember());
        
        long points = Math.round(itemPriceForCustomer);
        
        ((TextView) findViewById(R.id.productCost)).setText(DecimalFormat.getCurrencyInstance(Locale.US).format(itemPriceForCustomer));
        ((TextView) findViewById(R.id.productPoints)).setText(String.format("%d Points", points));
    }
	
    private void loadList(){
        List<Item> values = Item.listAll(Item.class);
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
}
