package UI;

import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {

    Scanner input = new Scanner(System.in);

    public MainMenu(){}

    public static void main(String[] args) {
        MainMenu m = new MainMenu();
        m.printMenu();
        int selectedOption = m.getOption();
        m.menuAction(selectedOption);
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

    private void menuAction(int selection){
        switch(selection){
            case 1:
                System.out.println("Finding and reserving a room");
                break;
            case 2:
                System.out.println("See all my reservations");
                break;
            case 3:
                System.out.println("Creating an account");
                break;
            case 4:
                System.out.println("Administration Menu");
                openAdminMenu();
                break;
            case 5:
                System.out.println("Exit the program");
                break;
        }
    }

    //methods for all these actions
    private void openAdminMenu(){
        AdminMenu am = new AdminMenu(input);
        am.displayMenu();
    }


}
