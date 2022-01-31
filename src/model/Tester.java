package model;

//Testing the data model classes
public class Tester {

    public static void main(String[] args) {
        try {
            Customer c = new Customer("Joe", "Blogs", "ff@hjd.com");
            System.out.println(c);
        } catch (IllegalArgumentException ex){
            System.out.println("The entered email is invalid");
            System.out.println(ex.getLocalizedMessage());
        }


    }

}
