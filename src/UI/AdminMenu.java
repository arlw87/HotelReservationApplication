package UI;

import java.sql.SQLOutput;
import java.util.Locale;
import java.util.Scanner;


import model.*;
import service.*;

public class AdminMenu {

    Scanner input = null;

    public AdminMenu(Scanner passedScanner){
        input = passedScanner;
    }

    public void displayMenu(){
        boolean isAdminOpen = true;
        while (isAdminOpen){
            printMenu();
            int selectedOption = getOption();
            isAdminOpen = menuAction(selectedOption);
        }
    }

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

    //Duplication of code from the main menu class. May refactor into seperate helper class
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
     *
     * @param selection
     * @return True if Admin menu is to stay open after the menuAction has been performed
     * false if the Admin Menu is to close after the menuAction has been performed
     */
    private boolean menuAction(int selection){
        switch(selection){
            case 1:
                System.out.println("See All Customers");
                return true;
            case 2:
                System.out.println("See All Rooms");
                return true;
            case 3:
                System.out.println("See All Reservations");
                return true;
            case 4:
                System.out.println("Add a Room");
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

    //check that the room isnt already in the db and check the input is valid
    private void addARoom(){

        int roomNumber = -1;
        int roomSize = -1;
        double roomCost = -1;
        ReservationService rs = ReservationService.getInstance();

        boolean enteringRoomData = true;

        while (enteringRoomData){
            boolean isRoomNumberEntered = false;
            while (!isRoomNumberEntered){
                try{
                    System.out.println("Enter room number");
                    roomNumber = Integer.parseInt(input.nextLine().trim());

                    //check if the room number has been entered before
                    if (rs.getARoom(Integer.toString(roomNumber)) != null){
                        throw new RuntimeException();
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

            rs.addRoom(new Room(String.valueOf(roomNumber), roomCost, roomType));
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
                        throw new IllegalArgumentException("Invlad input");
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("Invalid choice please try again");
                }
            }

        }

    }


}
