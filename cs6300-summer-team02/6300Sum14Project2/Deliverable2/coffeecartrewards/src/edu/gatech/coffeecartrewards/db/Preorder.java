package edu.gatech.coffeecartrewards.db;

//TODO- just using strings here, instead it should be something like
//TODO- public Item item.  I figure if we switch to an ORM it'll change this anyways.

public class Preorder {
    public int id;
    public String date;
    public String item_name;
    public String customer_name;

    @Override
    public String toString(){
        return customer_name + " - " + item_name;
    }
}

