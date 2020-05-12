package edu.gatech.coffeecartrewards;

import android.content.Context;
import android.util.Log;
import edu.gatech.coffeecartrewards.model.*;
import java.util.Calendar;
import java.util.List;

public class DAO {
    //NOTE: If you end up getting "No such table" errors after a schema change, increment db_version in AndroidManifest

    public static void createDataIfNonExistent(Context context){
        if(Customer.listAll(Customer.class).size() == 0){
           createTestCustomers(context);
        }

        if(Item.listAll(Item.class).size() == 0){
            createTestItems(context);
        }

        if(Preorder.listAll(Preorder.class).size() == 0){
            createTestPreorders(context);
        }

        if(Sale.listAll(Sale.class).size() == 0){
            createTestSales(context);
        }
        
        if(SaleLine.listAll(SaleLine.class).size() == 0){
            createTestSaleLines(context);
        }

    }

    private static void createTestCustomers(Context context){
        Calendar cal = Calendar.getInstance();

        cal.set(1975, Calendar.NOVEMBER, 12);
        new Customer(context,"Brian Elliott","618-466-9121",cal.getTime(),"1234567").save();

        cal.set(1992, Calendar.OCTOBER, 12);
        new Customer(context,"Alex Steen","618-644-9121",cal.getTime(),"1234568").save();

        cal.set(1972, Calendar.JANUARY, 12);
        new Customer(context,"Greg Millen","402-644-9121",cal.getTime(),"1234569").save();
        
        cal.set(1992, Calendar.SEPTEMBER, 2);
        new Customer(context,"Rachel Fairchild","860-283-4466",cal.getTime(),"1274369").save();
        
        cal.set(1998, Calendar.NOVEMBER, 30);
        new Customer(context,"Gabby Fairchild","860-283-4466",cal.getTime(),"1229469").save();
    }

    private static void createTestItems(Context context){
        new Item(context, "Croissant","Dessert",5.50,true).save();
        new Item(context, "Chocolate Hot Dog","Dessert",2.50,true).save();
        new Item(context, "Coffee Cake","Dessert",6.75,false).save();
        new Item(context, "Latte","Coffee",1.75,false).save();
        new Item(context, "Cappuccino","Coffee",2.75,false).save();
        new Item(context, "Macchiato","Coffee",3.75,false).save();
        new Item(context, "Lemon Cake","Dessert",3.95,false).save();
        
        List<Item> ItemDump = Item.listAll(Item.class);
        for (Item junkObj : ItemDump) {
        	Log.d("dbload, itemName:",junkObj.getItemName());
        	Log.d("dbload, itemType:",junkObj.getItemType());
        	Log.d("dbload, itemPrice:",String.valueOf(junkObj.getPrice()));
        	Log.d("dbload, itemBestseller:",String.valueOf(junkObj.getBestSeller()));
        }
    }

    private static void createTestPreorders(Context context){
        Calendar cal = Calendar.getInstance();
        List<Item> items = Item.listAll(Item.class);
        List<Customer> customers = Customer.listAll(Customer.class);

        cal.set(2014, Calendar.JULY, 31);
        new Preorder(context, cal.getTime(),items.get(0),customers.get(0)).save();

        cal.set(2014, Calendar.JULY, 22);
        new Preorder(context, cal.getTime(),items.get(1),customers.get(0)).save();

        cal.set(2014, Calendar.JULY, 25);
        new Preorder(context, cal.getTime(),items.get(2),customers.get(1)).save();
    }


    private static void createTestSales(Context context){
        Calendar cal = Calendar.getInstance();
        List<Customer> customers = Customer.listAll(Customer.class);
        List<Item> items = Item.listAll(Item.class);

        cal.set(2014, Calendar.JUNE, 28);
        new Sale(context, cal.getTime(),customers.get(0)).save();
        new Sale(context, cal.getTime(),customers.get(1)).save();
        new Sale(context, cal.getTime(),customers.get(2)).save();
        new Sale(context, cal.getTime(),customers.get(3)).save();
        new Sale(context, cal.getTime(),customers.get(4)).save();

        cal.set(2014, Calendar.JULY, 1);
        new Sale(context, cal.getTime(),customers.get(1)).save();
        new Sale(context, cal.getTime(),customers.get(2)).save();
        new Sale(context, cal.getTime(),customers.get(3)).save();
        new Sale(context, cal.getTime(),customers.get(4)).save();
    }
    
    private static void createTestSaleLines(Context context){
    	
    	List<Sale> sales = Sale.listAll(Sale.class);
    	List<Item> items = Item.listAll(Item.class);
    	
    	new SaleLine(context, items.get(0).getPrice(),items.get(0),sales.get(0)).save();
      	new SaleLine(context, items.get(1).getPrice(),items.get(1),sales.get(0)).save();
      	new SaleLine(context, items.get(2).getPrice(),items.get(2),sales.get(0)).save();
      	    	
      	new SaleLine(context, items.get(1).getPrice(),items.get(1),sales.get(1)).save();
        new SaleLine(context, items.get(1).getPrice(),items.get(3),sales.get(1)).save();
        new SaleLine(context, items.get(2).getPrice(),items.get(2),sales.get(1)).save();
        new SaleLine(context, items.get(3).getPrice(),items.get(3),sales.get(1)).save();

        new SaleLine(context, items.get(4).getPrice(),items.get(4),sales.get(2)).save();
        new SaleLine(context, items.get(2).getPrice(),items.get(2),sales.get(2)).save();
      	new SaleLine(context, items.get(3).getPrice(),items.get(3),sales.get(2)).save();
      	new SaleLine(context, items.get(3).getPrice(),items.get(4),sales.get(2)).save();
      	new SaleLine(context, items.get(3).getPrice(),items.get(3),sales.get(2)).save();

    	new SaleLine(context, items.get(3).getPrice(),items.get(3),sales.get(3)).save();
        new SaleLine(context, items.get(4).getPrice(),items.get(4),sales.get(3)).save();

        new SaleLine(context, items.get(4).getPrice(),items.get(4),sales.get(4)).save();
       	new SaleLine(context, items.get(5).getPrice(),items.get(5),sales.get(4)).save();

       	new SaleLine(context, items.get(5).getPrice(),items.get(5),sales.get(5)).save();
        new SaleLine(context, items.get(6).getPrice(),items.get(6),sales.get(5)).save();
       	new SaleLine(context, items.get(0).getPrice(),items.get(0),sales.get(6)).save();
        new SaleLine(context, items.get(1).getPrice(),items.get(1),sales.get(7)).save();
       	new SaleLine(context, items.get(2).getPrice(),items.get(2),sales.get(0)).save();
        new SaleLine(context, items.get(3).getPrice(),items.get(3),sales.get(1)).save();
       	new SaleLine(context, items.get(4).getPrice(),items.get(4),sales.get(2)).save();
        new SaleLine(context, items.get(5).getPrice(),items.get(5),sales.get(3)).save();
       	new SaleLine(context, items.get(6).getPrice(),items.get(6),sales.get(4)).save();


    }
}