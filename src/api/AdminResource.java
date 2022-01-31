package api;
import model.*;
import service.*;
import java.util.*;

/**
 * Provides an API for the admin functions of the Hotel Reservation system, between the UI (Admin menu) and the
 * Service classes. AdminResource uses the singleton pattern so that only one instance of it, is accessible to the
 * program
 */
public class AdminResource {

    private static AdminResource singleton = null;

    private static CustomerService cs = null;
    private static ReservationService rs = null;

    private AdminResource(){
        System.out.println("Created Singleton for AdminResource");
    }

    /**
     * Creates and / or returns the single instance of the Admin Resource
     * @return Singleton of the AdminResource class
     */
    public static AdminResource getInstance(){
        if (singleton == null){
            singleton = new AdminResource();
            cs = CustomerService.getInstance();
            rs = ReservationService.getInstance();
        }
        return singleton;
    }

    /**
     * Get Customer Object using the customers unique email
     * @param email of the customer
     * @return The Customer object associated with the customers unique email
     */
    public Customer getCustomer(String email){
        return cs.getCustomer(email);
    }

    /**
     * Add one or more rooms to the the Hotel Reservation System
     * @param rooms
     */
    public void addRooms(List<IRoom> rooms){
        for (IRoom r: rooms)
        {
            rs.addRoom(r);
        }
    }

    /**
     * Return all rooms in the Hotel Reservation System
     * @return All room objects in a Collection
     */
    public Collection<IRoom> getAllRooms(){
        return rs.getAllRooms();
    }

    /**
     * Return all Customer objects saved in the Hotel Reservation System
     * @return
     */
    public Collection<Customer> getAllCustomers(){
        return cs.getAllCustomers();
    }

    /**
     * Display all previously created reservations
     */
    public void displayAllReservations(){
        rs.printAllReservations();
    }
}
