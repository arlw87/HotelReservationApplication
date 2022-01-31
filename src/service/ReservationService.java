package service;

import java.util.*;

import model.*;

/**
 * Stateful class that provides the storage for Reservations in the hotel reservation system using the
 * singlton pattern.
 */

public class ReservationService {

    private static ReservationService singleton = null;

    private static Map<String, IRoom> rooms = new HashMap<>();
    private static Collection<Reservation> reservations = new ArrayList<>();

    private ReservationService(){
    };

    /**
     * Returns and / or creates a singleton object
     * @return
     */
    public static ReservationService getInstance(){
        if (singleton == null){
            singleton = new ReservationService();
        }
        return singleton;
    }

    /**
     * Add room to the Hotel Reservation System
     * @param room
     */
    public void addRoom(IRoom room){
        rooms.put(room.getRoomNumber(), room);
    }

    /**
     * Get all rooms
     * @return All rooms as a collection
     */
    public Collection<IRoom> getAllRooms(){
        return rooms.values();
    }

    /***
     * Room data stored as HashMap so use roomID to return room object
     * @param roomID
     * @return The room, or null if no room with that roomID is found
     */
    public IRoom getARoom(String roomID){
        return rooms.get(roomID);
    }

    /**
     * Iterate through all the customers reserves and return the ones that match the customer provided
     * @param customer
     * @return Collection of customer Objects, empty is no matches for supplied customer
     */
    public Collection<Reservation> getCustomersReservation(Customer customer){
        Collection<Reservation> customersReservations = new ArrayList<>();

        for (Reservation r: reservations){
            if (r.getCustomer().getEmail().equalsIgnoreCase(customer.getEmail())){
                customersReservations.add(r);
            }
        }

        return customersReservations;
    }

    /**
     * Create a reservation object and add it to the List of reservations
     * @param customer
     * @param room
     * @param chekInDate
     * @param checkOutDate
     * @return The reservation that has just been created
     */
    public Reservation reserveARoom(Customer customer, IRoom room, Date chekInDate, Date checkOutDate){
        Reservation r = new Reservation(customer, room, chekInDate, checkOutDate);
        reservations.add(r);
        return r;
    }

    /**
     * Find available rooms based on the check in and check out dates supplied. Room is available
     * if it exists and doesn't have a reservation that overlaps the supplied check in and check out dates
     * @param checkInDate
     * @param checkOutDate
     * @return A collection of rooms. If no room found a collection of rooms with size 0
     */
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        //create a list of all rooms
        Collection<IRoom> allRooms = rooms.values();
        //empty list to store all available rooms
        List<IRoom> availableRooms = new ArrayList<IRoom>();

        OUTER_LOOP: for (IRoom room : allRooms){
            //loop through the current reservations and check against rooms list
            INNER_LOOP: for (Reservation r: reservations){
                //if room in reservation same as the current room in room list does the reserveration dates overlap
                //the checkInDate and checkOutDate provided
                if (r.getRoom().getRoomNumber().equals(room.getRoomNumber())){
                    if (doDateRangesOverlap(checkInDate, checkOutDate, r.getCheckIn(), r.getCheckOut())){
                        //if the reservation overlaps with requested dates
                        //then dont add room to available rooms as it is not available
                        //goto next room in all rooms list
                        continue OUTER_LOOP;
                    }
                }
            }
            //Add room to available room if it doesnt have any reservationa ginst it or its reservations
            //dont fall within the chekInDate and checkOutDate
            availableRooms.add(room);
        }

        return availableRooms;
    }

    /**
     * Helper function to work out if two date ranges overlap or not
     * @param rangeAStart
     * @param rangeAEnd
     * @param rangeBStart
     * @param rangeBEnd
     * @return True if they do overlap, false if they do not
     */
    private boolean doDateRangesOverlap(Date rangeAStart, Date rangeAEnd, Date rangeBStart, Date rangeBEnd){
        return (rangeAStart.getTime() <= rangeBEnd.getTime() && rangeAEnd.getTime() >= rangeBStart.getTime());
    }

    public void printAllReservations(){
        for (Reservation r: reservations){
            System.out.println(r);
        }
    }

    public void printAllRooms(){
        for (IRoom r: getAllRooms()){
            System.out.println(r);
        }
    }

}
