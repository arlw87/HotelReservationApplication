package model;

import java.util.Objects;

public class Room implements IRoom{

    private String roomNumber;
    private Double price;
    private RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType){
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "Room "+ roomNumber + " costs £" + price + " is of type " + roomType;
    }

    @Override
    public int hashCode() {
        return roomType.hashCode() + price.hashCode() + roomType.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
        //Cast
        Room aRoom = (Room)obj;
        return Objects.equals(roomNumber, aRoom.getRoomNumber()) && Objects.equals(price, aRoom.getRoomPrice()) &&
                Objects.equals(roomType, aRoom.getRoomType());
    }


}
