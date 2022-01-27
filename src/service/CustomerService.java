package service;

import model.Customer;

import java.util.Collection;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;

public class CustomerService {

    private static CustomerService singleton = null;

    private Collection<Customer> customers = new ArrayList<Customer>();

    private CustomerService(){
        System.out.println("The singleton has been created");
    }

    public CustomerService getInstance(){
        if (singleton == null){
            singleton = new CustomerService();
        }
        return singleton;
    }

    public void addCustomer(String email, String firstName, String lastName){
        try{
            if (getCustomer(email) != null){
                throw new IllegalArgumentException("Customer email already used");
            }
            Customer c = new Customer(firstName, lastName, email);
            customers.add(c);
            System.out.println("Customer " + c + " has been added");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Returns customer object for that email
     * @param customerEmail
     * @return Customer is found, null if no customer with that email is found
     */
    public Customer getCustomer(String customerEmail){
        //Customers are stored in ArrayList so iterate through them
        for (Customer c: customers){
            if (c.getEmail().equalsIgnoreCase(customerEmail)){
                return c;
            }
        }
        return null;
    }

    public Collection<Customer> getAllCustomers(){
        return customers;
    }
}
