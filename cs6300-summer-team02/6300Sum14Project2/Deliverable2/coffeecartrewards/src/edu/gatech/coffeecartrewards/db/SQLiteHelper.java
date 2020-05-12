package edu.gatech.coffeecartrewards.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "coffeecartrewards3.db";
    private static final int DATABASE_VERSION = 9;

    private static final String CREATE_CUSTOMERS = "create table customers (id " +
                                        "integer primary key autoincrement, cust_name text, phone_number text," +
                                        "birthday text, card_number text);";

    private static final String CREATE_ITEMS = "create table items ( id integer primary key autoincrement," +
                                                      "item_name text, item_type text, price real,bestseller integer)";

    private static final String CREATE_PREORDERS =  "create table preorders(id integer primary key autoincrement," +
                                                       " preorder_date text, item_id integer, customer_id integer)";

    private static final String CREATE_SALES = "create table sales(id integer primary key autoincrement, sale_date text, item_id integer, " +
                                                " customer_id integer, sale_price real)";

    private static final String CUST_INSERTS = "insert into customers (id, cust_name,phone_number,birthday,card_number) values " +
                                                   "(1,'Traci Fairchild','618-466-9121','1975-11-12','1234567')," +
                                                    "(2,'Reece Karge','618-466-4121','1968-12-12','2234567')," +
                                                    "(3,'Ted Korolchuk','618-456-9121','1985-11-12','1234568')," +
                                                    "(4,'Kevin Stone','628-466-9121','1955-10-12','1234569');";
    private static final String ITEM_INSERTS =  "insert into items (id,item_name,item_type,price,bestseller) values " +
                                                    "(1,'Regular Coffee','Coffee',1.59,0)," +
                                                    "(2,'Cappuccino','Coffee',3.59,0)," +
                                                    "(3,'Coffee Cake','Dessert',4.59,0)," +
                                                    "(4,'Lemon Pound Cake','Dessert',3.59,1)," +
                                                    "(5,'Strawberry Pound Cake','Dessert',2.59,0);";

    private static final String PREORDER_INSERTS = "insert into preorders (id,preorder_date,item_id,customer_id) values " +
                                                    "(1,'2014-07-24',3,3)," +
                                                    "(2,'2014-02-12',3,2)," +
                                                    "(3,'2013-03-06',4,1)," +
                                                    "(4,'2013-11-20',3,3)," +
                                                    "(5,'2014-07-24',5,1);" ;

    private static final String SALES_INSERTS =  "insert into sales (id, sale_date, item_id, customer_id, sale_price) values " +
                                                    "(1,'2014-07-01',2,1,1.59)," +
                                                    "(2,'2014-06-21',4,1,3.59)," +
                                                    "(3,'2014-06-15',5,1,4.59)," +
                                                    "(4,'2014-05-01',4,1,3.59);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_CUSTOMERS);
        database.execSQL(CREATE_ITEMS);
        database.execSQL(CREATE_PREORDERS);
        database.execSQL(CREATE_SALES);
        database.execSQL(CUST_INSERTS);
        database.execSQL(ITEM_INSERTS);
        database.execSQL(PREORDER_INSERTS);
        database.execSQL(SALES_INSERTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS customers");
        db.execSQL("DROP TABLE IF EXISTS items");
        db.execSQL("DROP TABLE IF EXISTS preorders");
        db.execSQL("DROP TABLE IF EXISTS sales");
        onCreate(db);
    }

}
