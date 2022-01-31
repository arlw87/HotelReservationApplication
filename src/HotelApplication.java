import UI.*;

public class HotelApplication {
    public static void main(String[] args) {
        boolean exit = false;
        MainMenu m = new MainMenu();
        while (!exit){
            m.printMenu();
            int selectedOption = m.getOption();
            exit = m.menuAction(selectedOption);
        }
    }
}
