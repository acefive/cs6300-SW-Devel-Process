package edu.gatech.coffeecartrewards.activities;


import java.util.List;


import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.model.Customer;
import edu.gatech.coffeecartrewards.model.Item;
import edu.gatech.coffeecartrewards.activities.PreorderAddDate;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class PreorderAdd extends ListActivity {

    private Item item;
    private Customer customer;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		customer = Customer.findById(Customer.class,extras.getLong("CustomerID"));
        getActionBar().setTitle("Preorder Dessert (1/2)");
        getActionBar().setSubtitle(customer.getCustName());
        
		setContentView(R.layout.preorder_add);

        List<Item> values = Item.listDesserts();
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
	}
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        item = (Item) l.getItemAtPosition(position);

        Intent intent = new Intent(this,PreorderAddDate.class);
        intent.putExtra("CustomerID", customer.getId());
        intent.putExtra("ItemID", item.getId());
        startActivity(intent);
    }
}
