package UI;

import service.CustomerService;
import service.ReservationService;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import model.*;

public class MainMenu {

    private Scanner input = null;
    private CustomerService cs = null;
    private ReservationService rs = null;

    public MainMenu(){
        cs = CustomerService.getInstance();
        rs = ReservationService.getInstance();
        input = new Scanner(System.in);
    }

    public static void main(String[] args) {
        boolean exit = false;
        MainMenu m = new MainMenu();
        while (!exit){
            m.printMenu();
            int selectedOption = m.getOption();
            exit = m.menuAction(selectedOption);
        }

    }

    private void printMenu(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Welcome to the Hotel Reservation Apllication");
        System.out.println("--------------------------------------------\n");
        System.out.println("1 - Find and reserve a room");
        System.out.println("2 - See my reservations");
        System.out.println("3 - Create an Account");
        System.out.println("4 - Admin");
        System.out.println("5 - Exit");
        System.out.println("--------------------------------------------");
        System.out.println("Please select an option");
    }

    private int getOption(){
        boolean isValidSelection = false;
        int selectedOption = -1;

        while (!isValidSelection) {
            try{
                selectedOption = Integer.parseInt(input.nextLine().trim());
                if ((selectedOption > 0) && (selectedOption < 6)){
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

    private boolean menuAction(int selection){
        switch(selection){
            case 1:
                reservingARoom();
                return false;
            case 2:
                viewCustomerReservations();
                return false;
            case 3:
                createAnAccount();
                return false;
            case 4:
                openAdminMenu();
                return false;
            case 5:
                System.out.println("Exit the program");
                return true;
            default:
                System.out.println("Default Option");
                return false;
        }
    }

    //methods for all these actions
    private void openAdminMenu(){
        AdminMenu am = new AdminMenu(input);
        am.displayMenu();
    }

    //This method will validate the email but not the names
    private void createAnAccount(){
        //get customer information
        boolean isEnteringCustomerInfo = true;

        while ( isEnteringCustomerInfo ){
            System.out.println("Please enter in your email (Format name@domain.com)");
            String email = input.nextLine().trim();
            System.out.println("First Name");
            String firstName = input.nextLine().trim();
            System.out.println("Last Name");
            String lastName = input.nextLine().trim();

            try{
                cs.addCustomer(email, firstName, lastName);
                isEnteringCustomerInfo = false;
            } catch (IllegalArgumentException ex){
                System.out.println(ex.getLocalizedMessage());
                System.out.println("Please enter your information again");
            }
        }

        System.out.println("Welcome to the Hotel\n");

    }

    private void reservingARoom() {

        boolean enteringDates = true;
        Date checkIn = null;
        Date checkOut = null;

        while (enteringDates){
            System.out.println("Enter Check in date (mm/dd/yyyy)");
            String checkInString = input.nextLine().trim();
            System.out.println("Enter Check out date (mm/dd/yyyy)");
            String checkOutString = input.nextLine().trim();

            //parse the Dates
            SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");

            try {
                checkIn = formatter.parse(checkInString);
                checkOut = formatter.parse(checkOutString);
                enteringDates = false;
            } catch (ParseException ex) {
                System.out.println("Error with date inputs");
                System.out.println("Please try again");
            }
        }

        //find rooms
        Collection<IRoom> availableRooms = rs.findRooms(checkIn, checkOut);

        //if there are no rooms available then add 7 days to both the checkIn and checkOut
        //dates and search again
        if (availableRooms.size() == 0){
            System.out.println("No rooms found for the date specified looking for rooms available 7 days after your" +
                    "specified start and end days...");
            //create a new search for rooms
            //Add to Date
            Date checkInPlus7 = incrementDateBy7Days(checkIn);
            Date checkOutPlus7 = incrementDateBy7Days(checkOut);
            availableRooms = rs.findRooms(checkIn, checkOut);

            if (availableRooms.size() == 0){
                System.out.println("Sorry there are no rooms available 7 days after your specified dates either");
                return;
            }
        }

        //print the rooms
        System.out.println("Here are the available rooms");
        for (IRoom room : availableRooms){
            System.out.println(room);
        }

        System.out.println("Would you like to book a room? (y/n)");
        boolean choosingLikeToBookRoom = true;
        while( choosingLikeToBookRoom ){

            try {
                String likeToBook = input.nextLine().toLowerCase(Locale.ROOT).trim();
                if (likeToBook.equals("n") || likeToBook.equals("no")){
                    return; //exit this menu option
                } else if (likeToBook.equals("y") || likeToBook.equals("yes")){
                    choosingLikeToBookRoom = false;
                } else {
                    throw new IllegalArgumentException("Invalid input");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid choice please try again");
            }
        }

        System.out.println("Do you have an account with us? (y/n)");
        boolean haveAccountLoop = true;
        while( haveAccountLoop ){

            try {
                String haveAccount = input.nextLine().toLowerCase(Locale.ROOT).trim();
                if (haveAccount.equals("n") || haveAccount.equals("no")){
                    //go to create an account
                    createAnAccount();
                    haveAccountLoop = false;
                    System.out.println("Please continue with reservation and enter you email address you just" +
                            "registered");
                } else if (haveAccount.equals("y") || haveAccount.equals("yes")){
                    haveAccountLoop = false;
                } else {
                    throw new IllegalArgumentException("Invalid input");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid choice please try again");
            }
        }

        //Now type in the account
        System.out.println("Enter email address of account (name@domain.com)");
        boolean enteringCustomerEmail = true;
        Customer reservationCustomer = null;
        while ( enteringCustomerEmail ) {
            try{
                String customerEmail = input.nextLine().toLowerCase(Locale.ROOT).trim();
                reservationCustomer = cs.getCustomer(customerEmail);
                if (reservationCustomer == null){
                    throw new IllegalArgumentException("That customer email address is not in our records");
                }
                enteringCustomerEmail = false;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
                System.out.println("Please try again");
            }
        }

        //Select room

        boolean roomNumberSelectionLoop = true;
        boolean isRoomAvailable = false;
        int roomNumberSelected = -1;
        IRoom reservedRoom = null;
        while ( roomNumberSelectionLoop ) {
            try{
                System.out.println("Please select room number");
                roomNumberSelected = Integer.parseInt(input.nextLine().trim());
                //check if the room number is in the available options
                for (IRoom room : availableRooms) {
                    if (Integer.parseInt(room.getRoomNumber()) == roomNumberSelected) {
                        isRoomAvailable = true;
                        roomNumberSelectionLoop = false;
                        reservedRoom = room;
                        break;
                    }
                }

                //if room isnt available then try again
                throw new RuntimeException("Room is not available");

            } catch (IllegalArgumentException ex){
                System.out.println("Please enter a valid room number");
                System.out.println("Please make a selection from these rooms");
                for (IRoom room : availableRooms){
                    System.out.println(room);
                }
            } catch (RuntimeException re){
                System.out.println(re.getLocalizedMessage());
                System.out.println("Please make a selection from these rooms");
                for (IRoom room : availableRooms){
                    System.out.println(room);
                }
            }
        }

        //create a reservation
        Reservation r = rs.reserveARoom(reservationCustomer, reservedRoom, checkIn, checkOut);

        System.out.println("");
        System.out.println("Thank you for your reservation");
        //print Reservation
        System.out.println(r);
    }

    private void viewCustomerReservations(){
        //Now type in the account
        System.out.println("Type in your email address to see your reservations");
        System.out.println("If you wish to exit this option type EXIT");
        System.out.println("Enter email address of account (name@domain.com)");
        boolean enteringCustomerEmail = true;
        Customer reservationCustomer = null;
        while ( enteringCustomerEmail ) {
            try{
                String customerEmail = input.nextLine().toLowerCase(Locale.ROOT).trim();
                //if the customer decides they dont want to type in address or they are not
                //registered then there is an option to exit the menu option
                if (customerEmail.toUpperCase(Locale.ROOT).equals("EXIT")){
                    return;
                }
                reservationCustomer = cs.getCustomer(customerEmail);
                if (reservationCustomer == null){
                    throw new IllegalArgumentException("That customer email address is not in our records");
                }
                //get the customer Reservations
                Collection<Reservation> customerReservations = rs.getCustomersReservation(reservationCustomer);
                //now print the reservations
                if (customerReservations.size() == 0){
                    System.out.println("There are no reservations for this customer");
                } else {
                    for (Reservation res : customerReservations){
                        System.out.println(res);
                    }
                    System.out.println("");
                }

                enteringCustomerEmail = false;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
                System.out.println("Please try again");
            }
        }
    };

    private static Date incrementDateBy7Days(Date dateToIncrement){
        Calendar date = Calendar.getInstance();
        date.setTime(dateToIncrement);
        date.add(Calendar.DATE, 7);
        return date.getTime();
    }
}
