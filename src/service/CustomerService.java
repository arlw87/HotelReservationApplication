package service;

import model.Customer;

import java.util.Collection;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;

/**
 * Stateful Class that provides the storage of Customer Data using the Singleton Pattern
 */

public class CustomerService {

    private static CustomerService singleton = null;

    final private Collection<Customer> customers = new ArrayList<Customer>();

    private CustomerService(){
        System.out.println("The singleton has been created");
    }

    /**
     * Creates and / or returns the CustomerService Singleton
     * @return
     */
    public static CustomerService getInstance(){
        if (singleton == null){
            singleton = new CustomerService();
        }
        return singleton;
    }

    /**
     * Adds a customer to the Hotel Reservation system
     * @param email
     * @param firstName
     * @param lastName
     * @throws IllegalArgumentException if the email is not valid
     */
    public void addCustomer(String email, String firstName, String lastName) throws IllegalArgumentException{
        if (getCustomer(email) != null){
            throw new IllegalArgumentException("Customer email already in use");
        }
        Customer c = new Customer(firstName, lastName, email);
        customers.add(c);
        System.out.println("Customer " + c + " has been added");
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

    /**
     * Returns all customers
     * @return ALl Customers
     */
    public Collection<Customer> getAllCustomers(){
        return customers;
    }
}
