package edu.gatech.coffeecartrewards.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.model.Item;
import edu.gatech.coffeecartrewards.model.Sale;
import edu.gatech.coffeecartrewards.model.SaleLine;


public class SalesViewLineItems extends Activity {
	ListView listView;

	//Todo make this right aligned or use a tableview or something.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_view_line_items);
        listView = (ListView) findViewById(R.id.listView1);

        Bundle extras = getIntent().getExtras();

        if(extras != null){

            String saleId = String.valueOf(extras.getLong("id"));

            Sale sale = Sale.find(Sale.class,"id = ?", saleId).get(0);

            getActionBar().setTitle("Sale #" + saleId + " -  Date: "+ sale.getDateStr());
            getActionBar().setSubtitle(sale.customer.getCustName());

            List<SaleLine> lines = SaleLine.find(SaleLine.class,"sale = ?", saleId);

            double total = 0.0;

            for (SaleLine s : lines)
                total += s.price;

            Item i = new Item(getApplicationContext(),"Total", null, total,false );
            SaleLine s = new SaleLine(this,total,i,sale);

            lines.add(s);
            ArrayAdapter<SaleLine> adapter = new ArrayAdapter<SaleLine>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, lines);

            listView.setAdapter(adapter);
        }

    }
}
