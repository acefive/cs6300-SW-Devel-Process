package edu.gatech.coffeecartrewards.model;


import android.content.Context;
import android.util.Log;

import com.orm.SugarRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class Sale extends SugarRecord<Sale> {
    public long id;
    public Date date;
    public Customer customer;

    public Sale(Context context){
        super(context);
       
    }

    public Sale(Context context, Date date, Customer customer){
        super(context);
        this.date = date;
        this.customer = customer;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getDateStr() {
    	java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");
    	Date saledate = date;
    	String saledate_str = df.format(saledate);
    	return saledate_str;
    }
    
    public Date getDate() {
    	return date;
    }
    
    public static List<String> getAllDatesSorted() {
	    List<Sale> sales = Sale.listAll(Sale.class);
	    List<String> alldates = new ArrayList<String>();
	    
	    //get all dates from sales table
	    for (Sale tmpSaleObj : sales) {
	    	java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");
	    	Date saledate = tmpSaleObj.date;
	    	String saledate_str = df.format(saledate);
	    	alldates.add(saledate_str);
	    }
	    
	    //remove dups
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(alldates);
		alldates.clear();
		alldates.addAll(hs);
	 
		//sort asc
	    Collections.sort(alldates, new Comparator<String>() {
	        java.text.DateFormat f = new java.text.SimpleDateFormat("MM/dd/yyyy");
	        @Override
	        public int compare(String o1, String o2) {
	            try {
	                return f.parse(o1).compareTo(f.parse(o2));
	            } catch (ParseException e) {
	                throw new IllegalArgumentException(e);
	            }
	        }
	    });
	    
	    return alldates;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public static List<Sale> findByDate (String date) {
        return Sale.find(Sale.class, "strftime('%m/%d/%Y',date/1000,'unixepoch') = ?", date);
    }


    @Override
    public String toString() {
        return "Sale #" + this.id + "- " + this.customer;
    }

    public List<SaleLine> getLines() {
    	List<SaleLine> saleLines= SaleLine.find(SaleLine.class, "sale = ?", this.getId().toString());
        return saleLines;
    	
    }

}

