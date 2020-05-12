package edu.gatech.coffeecartrewards.model;

//TODO- just using strings here, instead it should be something like
//TODO- public Item item.  I figure if we switch to an ORM it'll change this anyways.

import android.content.Context;

import com.orm.SugarRecord;
import com.orm.query.Select;

import edu.gatech.coffeecartrewards.utils.Func;
import edu.gatech.coffeecartrewards.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Preorder extends SugarRecord<Preorder> {
    public int id;
    public Date date;
    public Item item;
    public Customer customer;

    public Preorder(Context context){
        super(context);
    }

    public Preorder(Context context, Date date, Item item, Customer customer){
        super(context);
        this.date = date;
        this.item = item;
        this.customer = customer;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return item.getItemName() + " for " + customer.getCustName();
    }

    public static Date toDate(String strDate) throws ParseException {
    	return new SimpleDateFormat("MM/dd/yyyy").parse(strDate);
    }
    
    public static List<Preorder> findByDate (String date) {
        return Preorder.find(Preorder.class, "strftime('%m/%d/%Y',date/1000,'unixepoch') = ?", date);
    }

    public static List<String> findUniqueDates() {
    	List<Preorder> dates = (List<Preorder>) Select.from(Preorder.class).groupBy("date").list();
    	return Utils.map(dates, new Func<Preorder, String>() {
    	    public String apply(Preorder in) {
    	        return new SimpleDateFormat("MM/dd/yyyy").format(in.date);
    	    }
    	});
    	
    }
    
    public static List<String> findOpenPreorderDates(boolean isBestSeller) {
    	List<String> availableDates = new ArrayList<String>(30);
    	
    	Calendar now = Calendar.getInstance();
    	for (Integer i : Utils.range(0, 30)) {
    		now.add(Calendar.DAY_OF_YEAR, 1);
    		String date = new SimpleDateFormat("MM/dd/yyyy").format(now.getTime());
    		List<Preorder> preorders = findByDate(date);
    		if ((isBestSeller && (preorders.size()<3)) || (!isBestSeller && (preorders.size()<5))) {
    			availableDates.add(date);
    		}
    	}
    	
    	return availableDates;
    }
    
}

