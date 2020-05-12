package edu.gatech.coffeecartrewards.customers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.content.DialogInterface;
import edu.gatech.coffeecartrewards.R;
import edu.gatech.coffeecartrewards.db.Customer;
import edu.gatech.coffeecartrewards.db.MainDAO;

//TODO it'd be nice to find the first unused card # in the database and
//display it in the card field automatically

public class CustomersAddOrUpdate extends Activity {
    private Customer custToEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
           //this is an Edit Customer rather than add
            //TODO can extract this pattern out we use it a lot
            MainDAO datasource = new MainDAO(this);
            datasource.open();
            custToEdit = datasource.findCustomerById(extras.getInt("CustomerID"));
            datasource.close();
            //TODO error handling here if cust not found.
        }

        getActionBar().setTitle((custToEdit != null) ? "Edit Customer" : "Add Customer");
        setContentView(R.layout.customers_add);

         if(custToEdit != null) {
             findViewById(R.id.deleteCustomer).setVisibility(View.VISIBLE);
             findViewById(R.id.viewReport).setVisibility(View.VISIBLE);
             ((EditText) findViewById(R.id.cust_name)).setText(custToEdit.getCustName());
             ((EditText) findViewById(R.id.phone_number)).setText(custToEdit.getPhoneNumber());
             ((EditText) findViewById(R.id.card_number)).setText(custToEdit.getCardNumber());
             ((DatePicker) findViewById(R.id.birthday)).updateDate(custToEdit.getBirthdayYear(), custToEdit.getBirthdayMonth(), custToEdit.getBirthdayDay());
         }
    }

    public void addOrUpdateCustomer(View view){
        //TODO i use these 3 lines in a row like 4 times in this class.  There's a better way.
        String cust_name = ((EditText) findViewById(R.id.cust_name)).getText().toString();
        String phone_number = ((EditText) findViewById(R.id.phone_number)).getText().toString();
        String card_number = ((EditText) findViewById(R.id.card_number)).getText().toString();
        if(cust_name.length() < 4){
            showDialog("Error","Name must be 4 or more characters.",false);
        } else if(!PhoneNumberUtils.isGlobalPhoneNumber(phone_number)){
            showDialog("Error","Please enter a valid phone number.",false);
        } else if(card_number.length() < 4 || !card_number.matches("-?\\d+(\\.\\d+)?")){
            showDialog("Error","Please enter a numeric card number of 4 or more digits.",false);
        } else {
            //customer has filled out the form properly
            DatePicker bdpicker = (DatePicker) findViewById(R.id.birthday);

            String birth_date = String.valueOf(bdpicker.getYear()) + "-" + formatDatePart(bdpicker.getMonth()+1) + "-"
                        + formatDatePart(bdpicker.getDayOfMonth());
            MainDAO datasource = new MainDAO(this);
            datasource.open();
            if(custToEdit == null) {
                //We are creating a customer
                Customer c = datasource.findCustomerByNameAndBirthday(cust_name, birth_date);
                if (c != null) {
                    //customer is maybe found in DB, confirm
                    custFoundDialog(c);
                } else {
                    createCustomer(cust_name, phone_number, card_number, birth_date);
                }
            } else {
                //We are editing a customer
                int updateId = datasource.updateCustomer(custToEdit.getId(),cust_name, phone_number, card_number, birth_date);
                if(updateId == 0){
                    showDialog("Error","There was an error, did not update customer.",false);
                } else {
                    showDialog("Success", "Customer edited.", true);
                }
            }

            datasource.close();
        }
    }

    public void deleteCustomer(View view){
        //TODO- when deleting a customer do we delete all their sales?  we should delete all their preorders.
        //Maybe sale should not have customer_id, but instead actually log customer name.
        new android.app.AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Are you sure you want to delete this customer? This action cannot be undone.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    MainDAO datasource = new MainDAO(getApplicationContext());
                    datasource.open();
                    datasource.deleteCustomer(custToEdit);
                    datasource.close();
                    showDialog("Success","Customer deleted.",true);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void createCustomer(String cust_name, String phone_number, String card_number, String birth_date) {
        MainDAO datasource = new MainDAO(this);
        datasource.open();
        Customer c = datasource.createCustomer(cust_name, phone_number, birth_date, card_number);
        if(c != null) {
            showDialog("Success","Customer saved.", true);
            ((EditText) findViewById(R.id.cust_name)).setText("");
            ((EditText) findViewById(R.id.phone_number)).setText("");
            ((EditText) findViewById(R.id.card_number)).setText("");
        } else {
          showDialog("Error","There was an error.",false);
        }
        datasource.close();
    }

    //TODO this showDialog should probably be in a different class, since lots of activities may need this kinda thing
    //but without the backToList flag.  hmm.
    public void showDialog(String title, String msg, final Boolean backToList){
        new android.app.AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (backToList) {
                            Intent intent = new Intent(CustomersAddOrUpdate.this,CustomersList.class);
                            startActivity(intent);
                        }
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void custFoundDialog(Customer c){
        new android.app.AlertDialog.Builder(this)
                .setTitle("Customer Found?")
                .setMessage("A customer with the same name and birthdate was found in the system.  Card #" + c.getCardNumber()
                                + ". Is this the same person?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       showDialog("Error","Sorry, each customer can only have one VIP card.",false);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String cust_name = ((EditText) findViewById(R.id.cust_name)).getText().toString();
                        String phone_number = ((EditText) findViewById(R.id.phone_number)).getText().toString();
                        String card_number = ((EditText) findViewById(R.id.card_number)).getText().toString();
                        DatePicker bdpicker = (DatePicker) findViewById(R.id.birthday);
                        String birth_date =  String.valueOf(bdpicker.getYear()) + "-" + formatDatePart(bdpicker.getMonth() + 1) +
                                        "-" + formatDatePart(bdpicker.getDayOfMonth());

                        createCustomer(cust_name, phone_number, card_number, birth_date);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void viewCustomerInfo(View view){
        Intent intent = new Intent(this,CustomerInfo.class);
        intent.putExtra("CustomerID", (int) custToEdit.getId());
        startActivity(intent);
    }

    //formatDatePart(7) == "07" formatDatePart(11) == "11"
    public String formatDatePart(int month){
        return (month < 10) ? "0" + month : String.valueOf(month);
    }

}