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

    /***
     * Sets customer email. Validates the email using Regex.
     * @param email
     * @throws IllegalArgumentException if the email does follow the name@domain.com format
     */
    public void setEmail(String email) throws IllegalArgumentException{
        Pattern pattern = Pattern.compile("([A-Z]|[a-z]|[0-9])+@([A-Z]|[a-z]|[0-9])+\\.com");
        Matcher matcher = pattern.matcher(email.trim());
        if (!matcher.matches()){
            throw new IllegalArgumentException("Email invalid");
        }
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer : " +
                firstName + " " + lastName +
                " Email Address: " + email;
    }
}
