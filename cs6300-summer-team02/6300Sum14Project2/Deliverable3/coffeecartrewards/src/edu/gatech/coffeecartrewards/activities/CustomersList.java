package edu.gatech.coffeecartrewards.activities;

import android.content.Intent;
import android.os.Bundle;
import java.util.List;
import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.gatech.coffeecartrewards.MainActivity;
import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.model.Customer;

public class CustomersList extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("Manage Customers");
        setContentView(R.layout.customers_list);
        loadList();
    }

    public void addNew(View view) {
        Intent intent = new Intent(this, CustomersAddOrUpdate.class);
        startActivity(intent);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Customer c = (Customer) l.getItemAtPosition(position);
        Intent intent = new Intent(this, CustomerManage.class);
        System.out.println("Customer clicked with id:"+c.getId());
        intent.putExtra("CustomerID", c.getId());
        startActivity(intent);
    }

    public void onBackPressed() {
        Intent intent = new Intent(CustomersList.this, MainActivity.class);
        startActivity(intent);
    }

    private void loadList(){
        List<Customer> values = Customer.listAll(Customer.class);

        ArrayAdapter<Customer> adapter = new ArrayAdapter<Customer>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }


}