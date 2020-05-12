package edu.gatech.coffeecartrewards.db;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

//TODO- maybe replace a lot of this with an ORM?  See also SQLiteHelper.java
public class MainDAO {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { "id","cust_name","phone_number","birthday","card_number" };

    public MainDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /* Customer Methods */
    public Customer createCustomer(String name, String phone_number, String birthday, String card_number) {
        ContentValues values = new ContentValues();
        values.put("cust_name", name);
        values.put("phone_number", phone_number);
        values.put("birthday", birthday);
        values.put("card_number", card_number);

        long insertId = database.insert("customers", null,
                values);
        Cursor cursor = database.query("customers",
                allColumns, "id  = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Customer newCustomer = cursorToCustomer(cursor);
        cursor.close();
        return newCustomer;
    }

    public int updateCustomer(long id,String name, String phone_number, String card_number,String birthday) {
        ContentValues values = new ContentValues();
        values.put("cust_name", name);
        values.put("phone_number", phone_number);
        values.put("birthday", birthday);
        values.put("card_number", card_number);
        //String sql = "UPDATE CUSTOMERS SET cust_name ='" + c.getCustName() + "' WHERE id=" + c.getId();
        //database.execSQL(sql);
        return database.update("customers",values,"id = " + id,null);
    }

    public void deleteCustomer(Customer c) {
        long id = c.getId();
        System.out.println("Customer deleted with id: " + id);
        database.delete("customers", "id = " + id, null);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<Customer>();

        Cursor cursor = database.query("customers",
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Customer c = cursorToCustomer(cursor);
            customers.add(c);
            cursor.moveToNext();
        }
        cursor.close();
        return customers;
    }

    public Customer findCustomerByNameAndBirthday(String cust_name, String birth_date){
        Cursor cursor = database.query("customers",
                allColumns, "cust_name='" + cust_name + "' and birthday='" + birth_date + "'",
                null, null, null, null);
        Customer c = null;
        if(cursor.moveToFirst()){
            c = cursorToCustomer(cursor);
        }
        cursor.close();
        return c;
    }

    public Customer findCustomerById(int id){
        Cursor cursor = database.query("customers",
                allColumns, "id=" + String.valueOf(id),
                null, null, null, null);
        Customer c = null;
        if(cursor.moveToFirst()){
            c = cursorToCustomer(cursor);
        }
        cursor.close();
        return c;
    }

    private Customer cursorToCustomer(Cursor cursor) {
        Customer c = new Customer();
        c.setId(cursor.getLong(0));
        c.setCustName(cursor.getString(1));
        c.setPhoneNumber(cursor.getString(2));
        c.setBirthday(cursor.getString(3));
        c.setCardNumber(cursor.getString(4));
        return c;
    }

    /* Preorders */
    public List<String> getAllPreorderDates() {
        List<String> dates = new ArrayList<String>();

        String[] preorder_columns = { "strftime('%m/%d/%Y',preorder_date) preorder_date" };

        Cursor cursor = database.query(true,"preorders",
                preorder_columns, null,
                null, null, null, "date(preorder_date) desc",null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dates.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return dates;
    }

    public List<Preorder> findAllPreordersByDate(String date) {
        List<Preorder> orders = new ArrayList<Preorder>();

        Cursor cursor = database.rawQuery("select c.cust_name,i.item_name from preorders p "
                                            + " left join customers c on p.customer_id = c.id "
                                            + " left join items i on p.item_id = i.id "
                                            + " where preorder_date='" + convertToSQLiteDate(date) + "';",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Preorder p = new Preorder();
            p.customer_name = cursor.getString(0);
            p.item_name = cursor.getString(1);
            orders.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return orders;
    }

    //TODO this doesn't really go here.  Also, could use some input validation if we felt like it.
    //f("02/12/2014") == "2014-02-12"
    public String convertToSQLiteDate(String date){
        return date.split("/")[2] + "-" + date.split("/")[0] + "-" + date.split("/")[1];
    }


}


