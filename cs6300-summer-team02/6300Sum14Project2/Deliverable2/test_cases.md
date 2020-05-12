# Test Cases

## TC 1 - Make Purchase

### TC 1.1 - Coffee

 1. Open _App_
 2. Select _Purchase View_
 3. Select a customer
 	* Any customer
 4. Add _Coffee_ item to order
 	* Order total should update to include price of coffee (TBD)

### TC 1.2 - non-GOLD refill

 1. Open _App_
 2. Select _Purchase View_
 3. Select a customer
 	* non-GOLD level customer
 4. Add _Coffee refill_ item to order
 	* Order total should update to include half price (TBD) for coffee refill

### TC 1.3 - GOLD refill

 1. Open _App_
 2. Select _Purchase View_
 3. Select a customer
 	* GOLD level customer
 4. Add _Coffee refill_ item to Order
 	* Order total should be `$0.00` indicating a free refill


## TC 2 Earn VIP Points

### TC 2.1 Basic points

 1. Open _App_
 2. Select _Purchase View_
 3. Select a customer
 	* Any customer with no purchase history
 4. Select _Dessert_ item for purchase
	* Order total should be TBD for Dessert item
	* The number of VIP points should be TBD
 5. Checkout
 6. Go back to the home screen
 7. Select _Manage Customers_
 8. Select customer just used for purchase
 9. Select _View Report_
 	* The customers VIP Point total should be incremented to reflect recent purchase
 	* The customers VIP Point total over the last month should be incremented to reflect recent purchase

### TC 2.2 GOLD level

 1. Open _App_
 2. Select _Purchase View_
 3. Select a customer
 	* Any customer with no purchase history
 4. Select items for purchase that total `$500.00` +/- `$0.49`
 5. Checkout
 6. Go back to the home screen
 7. Select _Manage Customers_
 8. Select customer just used for purchase
 9. Select _View Report_
 	* The customers VIP Point total should be `500`
 	* The customers VIP Point total over the last month should be `500`
 	* The customer should now have GOLD level status
 
### TC 2.3 Permanet GOLD level

 1. Open _App_
 2. Select _Purchase View_
 3. Select a customer
 	* Any customer with no purchase history
 4. Select items for purchase that total `$5000.00` +/- `$0.49`
 5. Checkout
 6. Go back to the home screen
 7. Select _Manage Customers_
 8. Select customer just used for purchase
 9. Select _View Report_
 	* The customers VIP Point total should be `5000`
 	* The customers VIP Point total over the last month should be `5000`
 	* The customer should now have permanent GOLD level status

 	
## TC 3 Pre-orders

### TC 3.1 - Dessert success

 1. Open _App_
 2. Select _Preorders_
 3. Select TBD Dessert
 	* Best seller dessert
 4. Select TBD date
 5. Checkout
 	* System should correctly reserve the pre-order
 	* Check the _View Preorders_ screen to confirm pre-order for request data and customer

### TC 3.2 - Dessert fail (best seller)

 1. Open _App_
 2. Select _Preorders_
 3. Select TBD Dessert
 	* Best seller dessert
 4. Select TBD date
 5. Checkout
 	* System should correctly reserve the pre-order
 	* Check the _View Preorders_ screen to confirm pre-order for request data and customer
 6. Repeat steps 3,4,5 three more times (with the same customer, dessert, and date) for a total of 4 attempted preorders
 	* Three preorders should succeed
 	* On the fourth preorder attempt the system should reject the pre-order
 
### TC 3.3 - Dessert fail (non-best seller)

 1. Open _App_
 2. Select _Preorders_
 3. Select TBD Dessert
 	* Non-best seller dessert
 4. Select TBD date
 5. Checkout
 	* System should correctly reserve the pre-order
 	* Check the _View Preorders_ screen to confirm pre-order for request data and customer
 6. Repeat steps 3,4,5 five more times (with the same customer, dessert, and date) for a total of 6 attempted preorders
 	* Five preorders should succeed
 	* On the sixth preorder attempt the system should reject the pre-order


## TC 4 - Purchase history

### TC 4.1

 1. Open _App_
 2. Select _Purchase View_
 3. Select a customer
 	* Any customer with no purchase history
 4. Select an item for purchase
 5. Checkout
 6. Go back to the home screen
 7. Select _Manage Customers_
 8. Select customer just used for purchase
 9. Select _View Report_
 	* The report should show the recent purchase
 	* The report should show the new VIP point total
  	* The report should show the new VIP point total over the last 30 days
 	* The report should show the VIP GOLD status
 	
 	

## TC 5 - Daily Report

### TC 5.1

 1. Open _App_  	
 2. Select _Purchase View_
 3. Select a customer
 	* Any customer with no purchase history
 4. Select an item for purchase
 5. Checkout
 6. Go back to the home screen
 7. Select _Preorders_
 8. Select TBD Dessert for the same customer
 9. Select the current day
 10. Checkout
 11. Go back to the home screen
 12. Select _Daily Report_
 	* The report should show the recent purchase
 	* The report should show the preorder pepending for the current day
 
 	
## TC 6 - Customer information

### TC 6.1 - Add customer

 1. Open _App_
 2. Select _Manage Customers_
 3. Select _Add New_
 4. Enter name, phone number, card number, and birthdate
 5. Select _Save_
 6. Exit _App_
 7. Open _App_
 8. Select _Manage Customers_
 	* The customer added in step 4 should be in the list
 9. Select the customer added in step 4
    * The information on the customer should match the information entered in step 4
    
### TC 6.1 - Edit customer

 1. Open _App_
 2. Select _Manage Customers_
 3. Select _Add New_
 4. Enter name, phone number, card number, and birthdate
 5. Select _Save_
 6. Exit _App_
 7. Open _App_
 8. Select _Manage Customers_
 	* The customer added in step 4 should be in the list
 9. Select the customer added in step 4
 10. Edit the customers phone number
 11. Select _Save_
 12. Exit _App_
 13. Open _App_
 14. Select _Manage Customers_
 15. Select customer added in step 4
    * The phone number should have updated to reflect the edit in step 10

### TC 6.2 - Remove customer

 1. Open _App_
 2. Select _Manage Customers_
 3. Select _Add New_
 4. Enter name, phone number, card number, and birthdate
 5. Select _Save_
 6. Exit _App_
 7. Open _App_
 8. Select _Manage Customers_
 	* The customer added in step 4 should be in the list
 9. Select the customer added in step 4
 10. Select _Remove Customer_
 11. Exit _App_
 12. Open _App_
 13. Select _Manage Customers_
    * The customer added in step 4 should not be in list of customers

