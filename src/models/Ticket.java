package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Ticket {
    int id;
    int userId;
    int roomId;
    int serviceId;
    Date checkInDate;
    Date checkOutDate;
    double totalPrice;

    public Ticket(int id, int userId, int roomId, int serviceId, Date checkInDate, Date checkOutDate, double totalPrice){
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.serviceId = serviceId;
        this.roomId =  roomId;
        this.id = id;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String insertTicket() {

        return String.format("INSERT INTO Tickets (userId, roomId,serviceId,checkInDate,checkOutDate) VALUES (%d,%d,%d,'%s','%s')",
                this.userId, this.roomId,this.serviceId, new SimpleDateFormat("yyyy-MM-dd").format(checkInDate), new SimpleDateFormat("yyyy-MM-dd").format(checkOutDate));
    }

    public String delTicket() {

        return String.format("DELETE FROM Tickets WHERE id = %d ", this.id);
    }

    public String updateTicket() {

        return String.format("UPDATE Tickets SET userId=%d, roomId=%d, serviceId=%d, checkInDate='%s', checkOutDate='%s' where id = %d",
                this.userId,this.roomId,this.serviceId,new SimpleDateFormat("yyyy-MM-dd").format(checkInDate),new SimpleDateFormat("yyyy-MM-dd").format(checkOutDate), this.id);
    }

    public static String searchById(int userId) {

        return String.format("SELECT * FROM Tickets left join users on users.id = tickets.userId left join rooms on rooms.id = tickets.roomId left join services on services.id = tickets.serviceId where users.id = %d", userId);
    }

    public static String searchByTitle(String title) {
        return "SELECT * FROM Tickets left join users on users.id = tickets.userId left join rooms on rooms.id = tickets.roomId left join services on services.id = tickets.serviceId where users.name Like '%"+title+"%'";
    }
}
