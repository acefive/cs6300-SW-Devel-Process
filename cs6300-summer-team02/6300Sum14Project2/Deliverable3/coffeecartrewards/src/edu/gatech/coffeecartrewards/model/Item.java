package edu.gatech.coffeecartrewards.model;
import java.util.List;

import android.content.Context;

import com.orm.*;
/*
Use this class like this with the ORM:
        Item i = new Item(this, "Croissant","Dessert",5.50,true);
        i.save();

        List<Item> items = Item.listAll(Item.class);*/

public class Item extends SugarRecord<Item> {
    String itemName;
    String itemType;
    double price;
    Boolean bestSeller;

    public Item(Context context){
        super(context);
    }

    public Item(Context context, String itemName, String itemType, double price, Boolean bestSeller){
        super(context);
        this.itemName = itemName;
        this.itemType = itemType;
        this.price = price;
        this.bestSeller = bestSeller;
    }

    public void setItemName(String n){ this.itemName = n; }
    
    public String getItemName(){
    	return itemName;
    }

    public void setItemType(String t){ this.itemType = t; }
    
    public String getItemType() {
    	return itemType;
    }

    public void setBestSeller(Boolean bestSeller) {
        this.bestSeller = bestSeller;
    }
    
    public Boolean getBestSeller() {
    	return bestSeller;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public boolean isRefillable() {
    	return itemType.equals("Coffee");
    }
    
    public double getPrice() {
    	// When called with no arguments assume non-GOLD status and not a refill
    	return getPrice(false, false);
    }
    
    public double getPrice(boolean isRefill, boolean isGoldMember) {
    	double actualPrice;
    	
    	if (isRefillable() && isRefill) {
    		if (isGoldMember) {
    			actualPrice = 0.0;
    		} else {
    			actualPrice = price/2.0;
    		}
    	} else {
    		actualPrice = price;
    	}
    	
    	return Math.round(actualPrice*100.0)/100.0;
    }

    @Override
    public String toString() {
        return itemName;
    }
    
    public static List<Item> listDesserts () {
        return Item.find(Item.class, "item_type = ?", "Dessert");
    }
}
