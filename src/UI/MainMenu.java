package UI;

import service.CustomerService;

import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {

    private Scanner input = null;
    private CustomerService cs = null;

    public MainMenu(){
        cs = CustomerService.getInstance();
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
                System.out.println("Finding and reserving a room");
                return false;
            case 2:
                System.out.println("See all my reservations");
                return false;
            case 3:
                System.out.println("Creating an account");
                createAnAccount();
                return false;
            case 4:
                System.out.println("Administration Menu");
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


}
