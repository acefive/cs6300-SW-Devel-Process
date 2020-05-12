TRACEABILITY DOCUMENT

  (REQ) Business Requirement ID and Descrip       
  (UC)  Use Case    
  (SYS) System Component

1.  REQ:  For each VIP customer, the system needs to know name, phone number, birth date, and the assigned VIP card number of the customer.
1.  UC:   Add Customer
1.  SYS:  Customer.class uses setCardNumber(), setCustName(), setPhoneNumber(), setBirthdate()

2.  REQ:  Each customer can have only one VIP card
2.  UC:   Add Customer
2.  SYS:  Manage Customer UI, CustomersAddOrUpdate()

3.  REQ:  The coffee cart owner must be able to add VIP customers to the system and remove VIP customers from the system.  When a customer is removed, 
his or her data is permanently deleted from the system.
3.  UC:   Add Customer, Delete Customer
3.  SYS:  Manage Customer UI, CustomerAddOrUpdate(), deleteCustomer() 
