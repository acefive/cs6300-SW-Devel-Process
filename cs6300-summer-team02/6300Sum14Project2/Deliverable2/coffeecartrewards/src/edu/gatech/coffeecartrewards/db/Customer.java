package edu.gatech.coffeecartrewards.db;

public class Customer {
    private long id;
    private String cust_name;
    private String phone_number;
    private String birthday;
    private String card_number;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static Customer findByNameAndBirthday(String cust_name,String birthday){
        return null;
    }

    public String getCustName() {
        return cust_name;
    }

    public void setCustName(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBirthday() {
        return birthday;
    }

    //TODO these next 3 methods are kinda silly I guess I could just store month/day/year of
    //bday as separate int fields.  But if you do that I don't know if you can query by date efficiently, or it's
    //at least trickier.  At the moment we don't need to do that though.
    public int getBirthdayMonth() {
        return Integer.parseInt(getBirthday().split("-")[1]) - 1;
    }

    public int getBirthdayDay(){
        return Integer.parseInt(getBirthday().split("-")[2]);
    }

    public int getBirthdayYear(){
        return Integer.parseInt(getBirthday().split("-")[0]);
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCardNumber() {
        return card_number;
    }

    public void setCardNumber(String card_number) {
        this.card_number = card_number;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return cust_name;
    }

}
