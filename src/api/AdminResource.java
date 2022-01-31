package api;
import model.*;
import service.*;
import java.util.*;

/**
 * Provides an API for the admin functions of the Hotel Reservation system, between the UI (Admin menu) and the
 * Service classes
 */
public class AdminResource {

    private static AdminResource singleton = null;

    private static CustomerService cs = null;
    private static ReservationService rs = null;

    private AdminResource(){
        System.out.println("Created Singleton for AdminResource");
    }

    public static AdminResource getInstance(){
        if (singleton == null){
            singleton = new AdminResource();
            cs = CustomerService.getInstance();
            rs = ReservationService.getInstance();
        }
        return singleton;
    }

    public Customer getCustomer(String email){
        return cs.getCustomer(email);
    }

    public void addRooms(List<IRoom> rooms){
        for (IRoom r: rooms)
        {
            rs.addRoom(r);
        }
    }

    public Collection<IRoom> getAllRooms(){
        return rs.getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return cs.getAllCustomers();
    }
    public void displayAllReservations(){
        rs.printAllReservations();
    }
}
