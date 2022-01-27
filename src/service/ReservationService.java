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

    public ReservationService getInstance(){
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

        return reservations;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date chekInDate, Date checkOutDate){
        //check for if the room is free on those dates should be done before
        Reservation r = new Reservation(customer, room, chekInDate, checkOutDate);
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
                    //now check the dates
                    if (true){
                        //if the room is already booked for those dates then you
                        //dont want to add the room to the available rooms
                        //so move to the next room in the allRooms collection
                        continue OUTER_LOOP;
                    }
                }
            }
            //Add room to available rooms
            availableRooms.add(room);
        }

        return null;
    }


}
