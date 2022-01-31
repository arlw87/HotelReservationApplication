package UI;

import api.AdminResource;
import api.HotelResource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import model.*;

/**
 * Main Menu of the Application. Provides the UI on the console for the user
 * to interact with. Uses the AdminResource and HotelResource class's as the API
 * to interact with the application
 */
public class MainMenu {

    private Scanner input = null;

    private AdminResource ar = null;
    private HotelResource hr = null;


    public MainMenu(){
        hr = HotelResource.getInstance();
        ar = AdminResource.getInstance();
        input = new Scanner(System.in);
    }

    public static void main(String[] args) {}

    /**
     * Prints the main menu to the console
     */
    public void printMenu(){
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

    /**
     * Get the Option that the user selects from the Admin Menu. If an invalid input such as letters, symbols or
     * a too high or too low number is entered the input will be requested again
     * @return The option that the user selects as an int
     */
    public int getOption(){
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

    /**
     * Takes the user input menu selection and runs the appropriate method to perform
     * the users desired action
     * @param selection
     * @return false, if the Main menu is to stay open after the action is performed
     * true if it is to close after the action is performed (Exit menu)
     */
    public boolean menuAction(int selection){
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
                System.out.println("Thank you for using the Hotel Reservation Service - Good bye");
                return true;
            default:
                System.out.println("Default Option");
                return false;
        }
    }

    /**
     * Opens the Admin menu
     */
    private void openAdminMenu(){
        AdminMenu am = new AdminMenu(input);
        am.displayMenu();
    }

    /**
     * Creates a customer account on the Hotel Reservation System. Validates the email entered
     * and checks to see if the customer email has been used previously.
     */
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
                hr.createACustomer(email, firstName, lastName);
                isEnteringCustomerInfo = false;
            } catch (IllegalArgumentException ex){
                System.out.println(ex.getLocalizedMessage());
                System.out.println("Please enter your information again");
            }
        }

        System.out.println("Welcome to the Hotel\n");

    }

    /**
     * Reserve a room in the Hotel Reservation System. If no rooms available for the supplied dates then the
     * method will check if any rooms are available a week later, if they are not then the method will exit.
     */
    private void reservingARoom() {

        boolean enteringDates = true;
        Date checkIn = null;
        Date checkOut = null;

        //loop for entering dates, will loop until the entered dates are valid
        while (enteringDates){
            System.out.println("Enter Check in date (mm/dd/yyyy)");
            String checkInString = input.nextLine().trim();
            System.out.println("Enter Check out date (mm/dd/yyyy)");
            String checkOutString = input.nextLine().trim();

            //parse the Dates
            SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");

            //Validates the entered dates. Are they both in the future, is the check out date after the
            //check in date, if these are not true, ask for inputs again
            try {
                checkIn = formatter.parse(checkInString);
                checkOut = formatter.parse(checkOutString);
                //check if the dates are in the future and the check in is before check out
                if (checkIn.compareTo(checkOut) > 0 || checkIn.compareTo(Calendar.getInstance().getTime()) < 0) {
                    throw new RuntimeException("Check In date must be before check out and in the future");
                }
                enteringDates = false;
            } catch (ParseException ex) {
                System.out.println("Error with date inputs");
                System.out.println("Please try again");
            } catch (RuntimeException re){
                System.out.println("Check in Date must be before checkout date and must be in the future");
            }
        }

        //find rooms for these entered dates
        Collection<IRoom> availableRooms = hr.findARoom(checkIn, checkOut);

        //if there are no rooms available then add 7 days to both the checkIn and checkOut
        //dates and search again
        if (availableRooms.size() == 0){
            System.out.println("No rooms found for the date specified looking for rooms available 7 days after your" +
                    "specified start and end days...");
            //create a new search for rooms
            //Add to Date
            checkIn = incrementDateBy7Days(checkIn);
            checkOut = incrementDateBy7Days(checkOut);
            availableRooms = hr.findARoom(checkIn, checkOut);

            if (availableRooms.size() == 0){
                System.out.println("Sorry there are no rooms available 7 days after your specified dates either");
                return;
            }
        }

        //print the rooms and Date Range

        System.out.println("Here are the available rooms for the dates: " + dateToSrtring(checkIn) + " to " + dateToSrtring(checkOut));
        for (IRoom room : availableRooms){
            System.out.println(room);
        }

        System.out.println("Would you like to book a room? (y/n)");
        boolean choosingLikeToBookRoom = true;

        //Choice to book a room loop, will loop until a validate input entered
        //to book a room. Any form of n, y, yes or no are valid
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

        //Check if the customer has account or not loop
        //will loop until they give a valid input
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

        //Now type in the email address to reserve room
        System.out.println("Enter email address of account (name@domain.com)");
        boolean enteringCustomerEmail = true;
        Customer reservationCustomer = null;
        //loop until the customer email is valid
        while ( enteringCustomerEmail ) {
            try{
                String customerEmail = input.nextLine().toLowerCase(Locale.ROOT).trim();
                reservationCustomer = hr.getCustomer(customerEmail);
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
        //Select a room loop
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
        Reservation r = hr.bookARoom(reservationCustomer.getEmail(), reservedRoom, checkIn, checkOut);

        System.out.println("");
        System.out.println("Thank you for your reservation");
        //print Reservation
        System.out.println(r);
    }

    /**
     * View all the customer reservations for the given customer
     */
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
                reservationCustomer = hr.getCustomer(customerEmail);
                if (reservationCustomer == null){
                    throw new IllegalArgumentException("That customer email address is not in our records");
                }
                //get the customer Reservations
                Collection<Reservation> customerReservations = hr.getCustomerReservations(reservationCustomer.getEmail());
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

    /**
     * Increment the supplied Date by 7 days
     * @param dateToIncrement
     * @return The dateToIncrement plus 7 days
     */
    private static Date incrementDateBy7Days(Date dateToIncrement){
        Calendar date = Calendar.getInstance();
        date.setTime(dateToIncrement);
        date.add(Calendar.DATE, 7);
        return date.getTime();
    }

    /**
     * Format a Date to a string
     * @param d
     * @return
     */
    private String dateToSrtring(Date d){
        String pattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(d);
    }
}
