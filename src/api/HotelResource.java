package api;

import model.*;
import service.*;

import java.util.*;

public class HotelResource {

    //this is going to a singleton aswell
    private static HotelResource singleton = null;

    //Access to the CustomerService Singleton
    private static CustomerService cs = null;

    //Access to the ReservationService Singleton
    private static ReservationService rs = null;

    private HotelResource(){
        System.out.println("Singleton for the HotelResource has been created");
    }

    public static HotelResource getInstance(){
        if (singleton == null){
            singleton = new HotelResource();
            //create the customer service  and Reservation service singleton at the same time
            cs = CustomerService.getInstance();
            rs = ReservationService.getInstance();
        }
        return singleton;
    }

    public Customer getCustomer(String email){
        //getCustomer in CustomerService Class ignores case
        return cs.getCustomer(email);
    }

    /**
     * Creates a customer in the Hotel Reservation System, if the customer email already in use or invalid then
     * throws an error (Check done in add Customer method called in this method)
     * @param email
     * @param firstName
     * @param lastName
     * @throws IllegalArgumentException if email already in use or invalid
     */
    public void createACustomer(String email, String firstName, String lastName) throws IllegalArgumentException{
        //Validation of email is done at the model level for customer
        cs.addCustomer(email, firstName, lastName);
    }

    /***
     *
     * @param roomNumber
     * @return null if no room is found, The room object if found
     */
    public IRoom getRoom(String roomNumber){
        return rs.getARoom(roomNumber);
    }


    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) throws IllegalArgumentException{
        Customer bookingCustomer = cs.getCustomer(customerEmail);
        if (bookingCustomer == null){
            throw new IllegalArgumentException("Email address is not registered on the system");
        }
        return rs.reserveARoom(bookingCustomer, room, checkInDate, checkOutDate);
    }

    /**
     * List of Entered Customers Reservations
     * @param customerEmail
     * @return Collection of reservation the customer has made, or null if no reservations are recorded for them
     * @throws IllegalArgumentException if email address given is not registed with any of
     * the customers on the system
     */
    public Collection<Reservation> getCustomerReservations(String customerEmail) throws IllegalArgumentException{
        //first get the customer object then get their reservations
        Customer currentCustomer = cs.getCustomer(customerEmail);
        if (currentCustomer == null){
            throw new IllegalArgumentException("Email address is not registered on the system");
        }
        //throw error if the customer is not registered
        return rs.getCustomersReservation(currentCustomer);
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return rs.findRooms(checkIn, checkOut);
    }



}
