package models;

//Room management module
// a.Enter user/guest date if not available
// b.Filter rooms via different options (if room is busy or not, room type, room service)
// c.Assign room to guest.
// d.View near checkout client (client with checkout within two days)
// e.Utilize other service module to assign client to other service.
// f.Detailed bill/invoice for each client

import java.util.Date;



public class Room {
    private int id;
    private String title;
    private RoomType roomType;
    private double pricePerNight;




    public Room(String title,RoomType roomType,int id, double pricePerNight) {
        this.title = title;
        this.roomType = roomType;
        this.id = id;
        this.pricePerNight = pricePerNight;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title  = title;
    }

    public String getRoomTypeString(){
        String roomType = "";
        switch (this.roomType){
            case single -> {
                roomType =  "single";
            }
            case triple -> {
                roomType =  "triple";
            }
            case quad -> {
                roomType =  "quad";
            }
            case king -> {
                roomType =  "King";
            }
        }
        return roomType;
    }

    public static RoomType getRoomTypeFromString(String roomTypeString){
        RoomType type = RoomType.king;
        switch (roomTypeString.trim().toLowerCase()){
            case "single" -> {
                type = RoomType.single;
            }
            case "triple" -> {
                type = RoomType.triple;
            }
            case "quad" -> {
                type =  RoomType.quad;
            }
            case "king" -> {
               type = RoomType.king;
            }

        }
        return type;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }






    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public enum RoomType{
        single,triple,quad,king
    }


    public String insertRoom() {

        return String.format("INSERT INTO Rooms (title, type, price) VALUES ('%s','%s',%f)", this.title,
                this.roomType, this.pricePerNight);
    }

    public String delRoom() {

        return String.format("DELETE FROM Rooms WHERE id = %d ", this.id);
    }

    public String updateRoom() {

        return String.format("UPDATE Rooms SET title='%s', type='%s', price=%f where id = %d",
                this.title, this.roomType,this.pricePerNight, this.id);
    }

    public static String searchById(int roomId) {

        return String.format("SELECT * FROM Rooms where id = %d", roomId);
    }

    public static String searchByTitle(String title) {
        return "SELECT * FROM Rooms where title Like '%"+title+"%'";
    }

}


