package edu.gatech.coffeecartrewards.model;

import android.content.Context;

import com.orm.SugarRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Customer extends SugarRecord<Customer> {
    private String custName;
    private String phoneNumber;
    private Date birthday;
    private String card_number;

    public Customer(Context context){
        super(context);
    }

    public Customer(Context context, String custName, String phoneNumber, Date birthday, String cardNumber){
        super(context);
        this.custName = custName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.card_number = cardNumber;
    }

    public void setCustName(String custName){ this.custName = custName; }
    public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber;}
    public void setBirthday(Date birthday){ this.birthday = birthday; }
    public void setCardNumber(String card_number) {
        this.card_number = card_number;
    }

    public static List<Customer> findByName(String cust_name){
    	return Customer.find(Customer.class, "cust_name = ? ", cust_name);
    }

    public static List<Customer> findByNameAndBirthday(String cust_name,String birthday){
    	Date bdate = null;
    	try {
		   bdate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(birthday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return Customer.find(Customer.class, "cust_name = ? and birthday = ?", cust_name, birthday);
    }

    public String getCustName() {
        return custName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public Date getBirthday() {
        return birthday;
    }
    
    public String getBirthdayStr() {
    	java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");
    	String birthday_str = df.format(this.birthday);
    	return birthday_str;
    }

    //TODO these next 3 methods are kinda silly I guess I could just store month/day/year of
    //bday as separate int fields.  But if you do that I don't know if you can query by date efficiently, or it's
    //at least trickier.  At the moment we don't need to do that though.
    public int getBirthdayMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);
        return cal.get(Calendar.MONTH);
    }

    public int getBirthdayDay(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getBirthdayYear(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);
        return cal.get(Calendar.YEAR);
    }



    public String getCardNumber() {
        return card_number;
    }


    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return custName;
    }
    public List<Sale> getSales(){
    	return Sale.find(Sale.class, "customer = ?", getId().toString());
    }
    
 public String getPointsTotal(){
    	List<Sale> sales = getSales();
    	System.out.println("number of sales:"+sales.size());
    	double total=0;
    	for(Sale sale: sales){
    		if(sale!=null){
    			for(SaleLine item: sale.getLines()){
        			System.out.println("items");
    				total=total+Math.round(Double.valueOf(item.getPrice()));
    			}
    		}
    	}
    	
    	return total+"";
    }
    public String get30DayPointsTotal(){
    	List<Sale> sales = getSales();
    	System.out.println("number of 30 sales:"+sales.size());
    	double total=0;
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DATE,-30);
    	for(Sale sale: sales){
    		if(sale!=null){
    			if(cal.getTime().before(sale.getDate())){
	    			for(SaleLine item: sale.getLines()){
	        			System.out.println("30 day items");
	    				total=total+Math.round(Double.valueOf(item.getPrice()));
	    			}
    			}
    		}
    	}
    	
    	return total+"";
    }
    
    public boolean isGoldMember() {
    	if(Double.valueOf(getPointsTotal())>5000 || Double.valueOf(get30DayPointsTotal())>500){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public String getGoldMember(){
    	if(isGoldMember()){
    		return "Gold Member";
    	}else{
    		return "Non Gold Member";
    	}
    }


    public Boolean isValid(){
        return false;
    }
}
