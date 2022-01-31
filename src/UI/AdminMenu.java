package UI;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


import api.AdminResource;
import api.HotelResource;
import model.*;
import service.*;

/**
 * Interface for the Administration menu. Prints and reads from the console. Uses Admin Resource and Hotel
 * Resource class as the API to interface with the application
 */

public class AdminMenu {

    Scanner input = null;
    AdminResource ar = null;
    HotelResource hr = null;

    public AdminMenu(Scanner passedScanner){
        input = passedScanner;
        ar = AdminResource.getInstance();
        hr = HotelResource.getInstance();
    }

    /**
     * Displays the Admin Menu in the console
     * Takes the menu option the user gives and
     * invokes the menuAction method to perform
     * action based on the user input.
     * If the user chooses to exit, isAdminOpen will become
     * false and the menu will exit
     */
    public void displayMenu(){
        boolean isAdminOpen = true;
        while (isAdminOpen){
            printMenu();
            int selectedOption = getOption();
            isAdminOpen = menuAction(selectedOption);
        }
    }

    /**
     * Prints the Admin menu to the console
     */
    private void printMenu(){
        System.out.println("\n--------------------------------------------");
        System.out.println("                Admin Menu                    ");
        System.out.println("--------------------------------------------\n");
        System.out.println("1 - See All Customers");
        System.out.println("2 - See All rooms");
        System.out.println("3 - See All Reservations");
        System.out.println("4 - Add a Room");
        System.out.println("5 - Add Test Data");
        System.out.println("6 - Back to Main Menu");
        System.out.println("--------------------------------------------");
        System.out.println("Please select an option");
    }

    /**
     * Get the Option that the user selects from the Admin Menu. If an invalid input such as letters, symbols or
     * a too high or too low number is entered the input will be requested again
     * @return The option that the user selects as an int
     */
    private int getOption(){
        boolean isValidSelection = false;
        int selectedOption = -1;

        while (!isValidSelection) {
            try{
                selectedOption = Integer.parseInt(input.nextLine().trim());
                if ((selectedOption > 0) && (selectedOption < 7)){
                    isValidSelection = true;
                } else {
                    System.out.println("Entered number is not a valid option, please try again");
                }
            } catch (NumberFormatException e){
                System.out.println("Incorrect input please try again");
            }
        }
        return selectedOption;
    }

    /**
     * Executes appropriate method based on the user input from the Admin menu options
     * @param selection
     * @return True if Admin menu is to stay open after the menuAction has been performed
     * false if the Admin Menu is to close after the menuAction has been performed
     */
    private boolean menuAction(int selection){
        switch(selection){
            case 1:
                printCustomers();
                return true;
            case 2:
                displayAllRooms();
                return true;
            case 3:
                seeAllReservations();
                return true;
            case 4:
                addARoom();
                return true;
            case 5:
                System.out.println("Add Test Data");
                return true;
            case 6:
                System.out.println("Back to Main Menu");
                return false;
            default:
                System.out.println("Default Option");
                return false;
        }
    }

    /**
     * Add a room or rooms to the Hotel Reservation System. Method will check that the input is valid and
     * will check if that room number has been previously registered. After one room has been entered it
     * will ask if another one is to an add. Once all rooms have been entered they are all add to the Hotel
     * Reservation System at once.
     */
    private void addARoom(){

        int roomNumber = -1;
        int roomSize = -1;
        double roomCost = -1;

        List<IRoom> roomsToAdd = new ArrayList<>();

        boolean enteringRoomData = true;

        while (enteringRoomData){
            boolean isRoomNumberEntered = false;
            while (!isRoomNumberEntered){
                try{
                    System.out.println("Enter room number");
                    roomNumber = Integer.parseInt(input.nextLine().trim());

                    //check if the room number has been entered before
                    if (hr.getRoom(Integer.toString(roomNumber)) != null){
                        throw new RuntimeException();
                    }

                    if(roomNumber < 1){
                        throw new IllegalArgumentException("Room number must be 1 or more");
                    }

                    isRoomNumberEntered = true;
                } catch (IllegalArgumentException ex){
                    System.out.println("Please enter a valid room number");
                } catch (RuntimeException re){
                    System.out.println("Room already exists please add another");
                }
            }

            boolean isRoomCostEntered = false;
            while (!isRoomCostEntered){
                try{
                    System.out.println("Enter room cost per night");
                    roomCost = Double.parseDouble(input.nextLine().trim());
                    isRoomCostEntered = true;
                } catch (IllegalArgumentException ex){
                    System.out.println("Please enter a valid room cost");
                }
            }

            boolean isRoomSizeEntered = false;
            while (!isRoomSizeEntered){
                try{
                    System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                    roomSize = Integer.parseInt(input.nextLine().trim());
                    if (roomSize > 2 || roomSize < 1){
                        throw new IllegalArgumentException("Wrong room size");
                    }
                    isRoomSizeEntered = true;
                } catch (IllegalArgumentException ex){
                    System.out.println("Please enter a valid room size");
                }
            }

            RoomType roomType = null;
            if (roomSize == 1){
                roomType = RoomType.SINGLE;
            } else {
                roomType = RoomType.DOUBLE;
            }

            //Add newly created room to list of rooms to be saved
            roomsToAdd.add(new Room(String.valueOf(roomNumber), roomCost, roomType));

            //enter another room
            boolean enterAnotherRoom = true;
            while (enterAnotherRoom) {
                try {
                    System.out.println("Do you want to enter data for another room (Y/Yes or N/No): ");
                    String enteredResponse = input.nextLine().trim().toLowerCase(Locale.ROOT);
                    if (enteredResponse.equals("n") || enteredResponse.equals("no")){
                        enteringRoomData = false;
                        enterAnotherRoom = false;
                    } else if (enteredResponse.equals("y") || enteredResponse.equals("yes")){
                        enterAnotherRoom = false;
                    } else {
                        throw new IllegalArgumentException("Invalid input");
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("Invalid choice please try again");
                }
            }
        }

        //Add rooms to the system
        ar.addRooms(roomsToAdd);

    }

    /**
     * Prints all rooms on the Hotel Reservation System
     */
    private void displayAllRooms(){
        System.out.println("All Rooms");
        System.out.println("--------------------------------------------");
        for (IRoom room: ar.getAllRooms()){
            System.out.println(room);
        }
    }

    /**
     * Prints all the customers of the Hotel Reservation System
     */
    private void printCustomers(){
        System.out.println("All Customers");
        for (Customer c: ar.getAllCustomers()){
            System.out.println(c);
        }
    }

    /**
     * Prints all the reservations recorded on the Hotel Reservation System
     */
    private void seeAllReservations(){
        System.out.println("All reservations are displayed below");
        ar.displayAllReservations();
    }

}
