package UI;

import java.sql.SQLOutput;
import java.util.Scanner;

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


}
