package edu.gatech.coffeecartrewards.activities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.gatech.coffeecartrewards.ExpandableListAdapter;
import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.model.Customer;
import edu.gatech.coffeecartrewards.model.Sale;
import edu.gatech.coffeecartrewards.model.SaleLine;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class PurchaseHistory extends Activity {
	private Customer customer;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		setContentView(R.layout.purchase_history);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		final ListView listview = (ListView) findViewById(R.id.purchasehistory);
		 if(extras != null){
	            //this is an Edit Customer rather than add
	         	customer = Customer.findById(Customer.class,extras.getLong("CustomerID"));
	             getActionBar().setTitle("Purchase History: "+customer.getCustName());
	             // get the listview
	             expListView = (ExpandableListView) findViewById(R.id.expandableListView1);
	      
	             // preparing list data
	             prepareListData();
	      
	             listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
	      
	             // setting list adapter
	             expListView.setAdapter(listAdapter);
	             
	      }

	}
	private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        
        for(Sale sale: customer.getSales()){
    		List<String> subList = new ArrayList<String>();
        	// Adding child data
        	listDataHeader.add(sale.getDateStr());
        	for(SaleLine saleline: sale.getLines()){
        		subList.add(saleline.getItem().getItemName()+" - $"+saleline.getPrice());
        	}
        	listDataChild.put(sale.getDateStr(), subList);
        }
 
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.purchase_history, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_purchase_history, container, false);
			return rootView;
		}
	}

}
