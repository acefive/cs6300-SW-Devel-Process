package edu.gatech.coffeecartrewards.customers;

import android.content.Intent;
import android.os.Bundle;
import java.util.List;
import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.gatech.coffeecartrewards.MainActivity;
import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.db.Customer;
import edu.gatech.coffeecartrewards.db.MainDAO;

public class CustomersList extends ListActivity {
    private MainDAO datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("Manage Customers");
        setContentView(R.layout.customers_list);
        datasource = new MainDAO(this);
        datasource.open();
        loadList();
    }

    public void addNew(View view) {
        Intent intent = new Intent(this, CustomersAddOrUpdate.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
        loadList();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Customer c = (Customer) l.getItemAtPosition(position);
        Intent intent = new Intent(this, CustomersAddOrUpdate.class);
        intent.putExtra("CustomerID", (int) c.getId());
        startActivity(intent);
    }

    public void onBackPressed() {
        Intent intent = new Intent(CustomersList.this, MainActivity.class);
        startActivity(intent);
    }

    private void loadList(){
        List<Customer> values = datasource.getAllCustomers();

        ArrayAdapter<Customer> adapter = new ArrayAdapter<Customer>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }



}