package service;
import model.*;

import java.util.Collection;
import java.util.Date;
import java.util.*;

public class ReservationTester {

    public static void main(String[] args) {
        ReservationService rs = ReservationService.getInstance();
        ReservationService rs2 = ReservationService.getInstance();
        System.out.println(rs.equals(rs2));

        //Lets make some Rooms
        Room room1 = new Room("001", 100.0, RoomType.DOUBLE);
        Room room2 = new Room("002", 120.0, RoomType.DOUBLE);
        Room room3 = new Room("003", 10.0, RoomType.SINGLE);
        Room room4 = new Room("004", 80.0, RoomType.SINGLE);
        Room room5 = new Room("005", 60.0, RoomType.SINGLE);
        Room room6 = new Room("006", 200.0, RoomType.DOUBLE);
        Room room7 = new Room("007", 300.0, RoomType.DOUBLE);

        rs.addRooms(room1, room2, room3, room4, room5, room6, room7);

        System.out.println(rs.getAllRooms());

        System.out.println(rs.getARoom("002"));

        System.out.println(rs.getARoom("015"));

        //find Available Rooms
        System.out.println("Find Available Rooms");
        List<IRoom> availableRooms = (List<IRoom>) rs.findRooms(new Date(), new Date());
        System.out.println(availableRooms);

        //Create some customers
        Customer c1 = new Customer("First", "Last", "Hello@example.com");
        Customer c2 = new Customer("Jan", "Jackson", "abc@example.com");

        //Book a Room
        rs.reserveARoom(c1, availableRooms.get(0), getDateObject(2022, 1, 1), getDateObject(2022, 1, 5));
        rs.reserveARoom(c2, availableRooms.get(3), getDateObject(2022, 0, 3), getDateObject(2022, 0,5));
        rs.reserveARoom(c1, availableRooms.get(1), getDateObject(2022, 5, 19), getDateObject(2022, 5, 22) );

        //check reservations
        System.out.println("Current Reservations");
        System.out.println(rs.debugReturnReservations());

        //check customers reservations
        System.out.println("Here are " + c1 + "Reservations");
        System.out.println(rs.getCustomersReservation(c1));

        //CHeck the find available rooms method again
        System.out.println("Find Available Rooms Again");
        List<IRoom> availableRooms2 = (List<IRoom>) rs.findRooms(getDateObject(2022, 5, 22), getDateObject(2022, 5, 28));
        System.out.println(availableRooms2);

        //Print all the Reservations
        rs.printAllReservations();

    }

    private static Date getDateObject(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return c.getTime();
    }




}
