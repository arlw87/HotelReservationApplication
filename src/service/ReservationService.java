package service;

import java.util.*;

import model.*;

public class ReservationService {

    private static ReservationService singleton = null;

    private static Map<String, IRoom> rooms = new HashMap<>();
    private static Collection<Reservation> reservations = new ArrayList<>();

    private ReservationService(){
        System.out.println("A Singleton has been created for the Reservation service");
    };

    public static ReservationService getInstance(){
        if (singleton == null){
            singleton = new ReservationService();
        }
        return singleton;
    }

    public void addRoom(IRoom room){
        //using put will overwrite the value if the key already exist
        //this behaviour seems sensible as you may want to upper your room info
        rooms.put(room.getRoomNumber(), room);
    }

    /**
     * For Testing purposes to add multiple rooms quickly
     * @param rooms
     */
    public void addRooms(IRoom ...rooms){
        for (IRoom r: rooms)
        {
            addRoom(r);
        }
    }

    /**
     * For Testing purposes
     */
    public Collection<IRoom> getAllRooms(){
        return rooms.values();
    }

    /***
     *
     * @param roomID
     * @return The room, or null if no room with that roomID is found
     */
    public IRoom getARoom(String roomID){
        return rooms.get(roomID);
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
        //iterate through all of the reservations and return a collection of
        //reservation made by that customer

        Collection<Reservation> customersReservations = new ArrayList<>();

        for (Reservation r: reservations){
            if (r.getCustomer().getEmail().equalsIgnoreCase(customer.getEmail())){
                customersReservations.add(r);
            }
        }

        return customersReservations;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date chekInDate, Date checkOutDate){
        //check for if the room is free on those dates should be done before
        Reservation r = new Reservation(customer, room, chekInDate, checkOutDate);
        //Add to the reservation list
        reservations.add(r);
        return r;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        //need to find all rooms that havent been reserved at all
        //need to find all rooms that havent been reserved for the dates specified

        //create a list of all rooms
        Collection<IRoom> allRooms = rooms.values();
        List<IRoom> availableRooms = new ArrayList<IRoom>();

        OUTER_LOOP: for (IRoom room : allRooms){
            //now loop through the current reservations and check against rooms list
            INNER_LOOP: for (Reservation r: reservations){
                //room in reservation same as the current room in room list
                if (r.getRoom().getRoomNumber().equals(room.getRoomNumber())){
                    //Do the rate ranges overlap
                    if (doDateRangesOverlap(checkInDate, checkOutDate, r.getCheckIn(), r.getCheckOut())){
                        //if the reservation overlaps with requested dates
                        //then dont add room to available rooms and goto next room in list
                        continue OUTER_LOOP;
                    }
                }
            }
            //Add room to available rooms
            availableRooms.add(room);
        }

        return availableRooms;
    }

    private boolean doDateRangesOverlap(Date rangeAStart, Date rangeAEnd, Date rangeBStart, Date rangeBEnd){
        return (rangeAStart.getTime() <= rangeBEnd.getTime() && rangeAEnd.getTime() >= rangeBStart.getTime());
    }

    public Collection<Reservation> debugReturnReservations(){
        return reservations;
    }

    public void printAllReservations(){
        System.out.println("Here is a List of all of reservations");
        for (Reservation r: reservations){
            System.out.println(r);
        }
    }



}
