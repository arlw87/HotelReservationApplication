package model;

import java.util.regex.*;

public class Customer {

    private String firstName;
    private String lastName;
    private String email;

    public Customer(String first, String last, String email) throws IllegalArgumentException{
        try{
            setEmail(email);
            setFirstName(first);
            setLastName(last);
        } catch (IllegalArgumentException Illegal){
            throw Illegal;
        }
    }

    public void setFirstName(String name){
        firstName = name;
    }

    public void setLastName(String name){
        lastName = name;
    }

    //If email is incorrect throw an exception so object not created
    public void setEmail(String email) throws IllegalArgumentException{
        //do a regex check
        Pattern pattern = Pattern.compile("([A-Z]|[a-z]|[0-9])+@([A-Z]|[a-z]|[0-9])+\\.com");
        Matcher matcher = pattern.matcher(email.trim());
        System.out.println("The email provide is correct: " + matcher.matches());
        if (!matcher.matches()){
            throw new IllegalArgumentException("Email invalid");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return "The Customer information is: " +
                "firstName: '" + firstName + '\'' +
                ", lastName: '" + lastName + '\'' +
                "and email adress: '" + email + '\'' +
                '}';
    }
}
