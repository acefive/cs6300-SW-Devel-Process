package edu.gatech.coffeecartrewards.model;

import android.content.Context;

import com.orm.SugarRecord;

import java.text.DecimalFormat;

public class SaleLine extends SugarRecord<SaleLine> {

    public double price;
    public Item item;
    public Sale sale;

    public SaleLine(Context context){
        super(context);
    }

    public SaleLine(Context context, double price, Item item, Sale sale){
        super(context);
        this.price = price;
        this.item = item;
        this.sale = sale;
    }

    @Override
    public String toString() {
        return  this.item + ": $" +  new DecimalFormat("#.00").format(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public double getPrice() {
    	return price;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    
    public Item getItem() {
    	return item;
    }
    
    public void setSale(Sale sale) {
    	this.sale = sale;
    }
    
    public Sale getSale() {
    	return sale;
    }

    
}
