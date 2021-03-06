package model;

/**
 * FreeRom is an extension of Room Class but the price is nothing
 */
public final class FreeRoom extends Room{

    public FreeRoom(String roomNumber, RoomType roomType){
        super(roomNumber, 0.0, roomType); //set the price to 0
    }

    @Override
    public boolean isFree() {
        return true;
    }

    public String toString(){
        return "Free Room{" +
                "roomNumber='" + this.getRoomNumber() + '\'' +
                ", roomType=" + this.getRoomType() +
                '}';
    }
}

