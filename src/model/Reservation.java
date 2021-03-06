package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {

    private Customer customer;
    private IRoom room;
    private Date checkIn;
    private Date checkOut;

    public Reservation(Customer c, IRoom r, Date cIn, Date cOut){
        setCheckIn(cIn);
        setCheckOut(cOut);
        setCustomer(c);
        setRoom(r);
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public void setRoom(IRoom room) {
        this.room = room;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    private String printDate(Date d){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simple = new SimpleDateFormat(pattern);
        return simple.format(d);
    }

    @Override
    public String toString() {
        return "Customer " + customer.getFirstName() + " " + customer.getLastName() + " (" + customer.getEmail() + ")" +
                " has reserved Room " + room.getRoomNumber() + " from " + printDate(checkIn) + " to " + printDate(checkOut);
    }
}
