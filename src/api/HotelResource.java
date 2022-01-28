package api;

import model.*;
import service.*;

import java.util.*;

public class HotelResource {

    //this is going to a singleton aswell
    HotelResource singleton = null;

    //Access to the CustomerService Singleton
    private CustomerService cs = null;

    private HotelResource(){
        System.out.println("Singleton for the HotelResource has been created");
    }

    public HotelResource getInstance(){
        if (singleton == null){
            singleton = new HotelResource();
            //create the customer service singleton at the same time
            cs = CustomerService.getInstance();
        }
        return singleton;
    }

    public Customer getCustomer(String email){
        //getCustomer in CustomerService Class ignores case
        return cs.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) throws IllegalArgumentException{
        //Validation of email is done at the model level for customer
        //there is currently no validation of the names
        cs.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber){
        return null;
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date CheckInDate, Date checkOutDate){
        return null;
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail){
        return null;
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return null;
    }



}
