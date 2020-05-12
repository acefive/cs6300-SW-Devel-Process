package edu.gatech.coffeecartrewards.activities;

import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.R.layout;
import edu.gatech.coffeecartrewards.model.Customer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CustomerManage extends Activity {
	
    private Customer customer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		//System.out.println("Customer Id out:"+ extras.getLong("CustomerID"));
		customer = Customer.findById(Customer.class,extras.getLong("CustomerID"));
        //TODO error handling here if cust not found.
        getActionBar().setTitle("Manage Customer");
		setContentView(R.layout.customer_manage);
	}
	
	/* New Purchase callback */
    public void newPurchase(View view){
        Intent intent = new Intent(this,PurchaseAdd.class);
        intent.putExtra("CustomerID", customer.getId());
        startActivity(intent);
    }

	/* New Preorder callback */
    public void newPreorder(View view){
        Intent intent = new Intent(this,PreorderAdd.class);
        intent.putExtra("CustomerID", customer.getId());
        startActivity(intent);
    }
    
	/* View Report callback */
    public void viewCustomerInfo(View view){
        Intent intent = new Intent(this,CustomerInfo.class);
        intent.putExtra("CustomerID", customer.getId());
        startActivity(intent);
    }
    
	/* edit customer callback */
    public void editCustomer(View view){
        Intent intent = new Intent(this, CustomersAddOrUpdate.class);
        System.out.println("Edit Customer clicked with id:"+customer.getId());
        intent.putExtra("CustomerID", customer.getId());
        startActivity(intent);
    }
}
