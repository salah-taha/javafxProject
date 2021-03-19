package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.Room;
import models.Service;
import models.Ticket;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class TicketsController  implements Initializable {
    @FXML
    private TextField id,user_id,room_id,service_id;
    @FXML
    private DatePicker start_date,end_date;

    @FXML
    private Label message;

    @FXML
    private TextField search_input;
    @FXML
    private RadioButton user_title;

    @FXML
    private VBox list_vBox;

    public Ticket selectedTicket = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user_title.setSelected(true);
    }

    public void insertTicket(){

        int data = 0;
        Ticket ticket = new Ticket(0,Integer.parseInt(user_id.getText()),Integer.parseInt(room_id.getText()),Integer.parseInt(service_id.getText()), Date.from(start_date.getValue().atStartOfDay(ZoneId.of( "America/Montreal" )).toInstant()),Date.from(end_date.getValue().atStartOfDay(ZoneId.of( "America/Montreal" )).toInstant()),0);
        if(selectedTicket != null){
            ticket.setId(Integer.parseInt(id.getText()));
            data = Db.getInstance().excuteUpdate(ticket.updateTicket());
            if(data == 1){
               showMessage("تم تعديل التذكرة "+ticket.getCheckInDate());
                resetData();
                search();
            }else{
               showError("حدث خطأ ما");
            }
        }else{
            data=  Db.getInstance().excuteUpdate(ticket.insertTicket());
            if(data == 1){
                showMessage("تم اضافة التذكرة "+ticket.getCheckInDate());
                resetData();
            }else{
                showError("حدث خطأ ما");
            }
        }

    }

    public void search(){
        String searchText = search_input.getText();
        list_vBox.getChildren().clear();
        ResultSet set = null;
        try{
            if(user_title.isSelected()){
                set = Db.getInstance().executeQuery(Ticket.searchByTitle(searchText));
            }else{
                try{
                    set = Db.getInstance().executeQuery(Ticket.searchById(Integer.parseInt(searchText)));
                }catch (Exception e){
                   showError("يجب ادخال رقم");
                }
            }

            int i = 0;
            while(set.next()){
                Ticket ticket = new Ticket(set.getInt("id"),set.getInt("userID"),set.getInt("roomId"),set.getInt("serviceId"),set.getDate("checkInDate"),set.getDate("checkOutDate"),0);
                User user = new User(set.getString("users.name"),set.getString("users.email"),set.getInt("users.id"),User.getUserTypeFromString(set.getString("users.type")),"");
                Room room = new Room(set.getString("rooms.title"),null,0,0);
                Service service = new Service(set.getString("services.name"),null,0,0);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/ticket_card.fxml"));
                    TicketCardController controller = new TicketCardController();
                    loader.setController(controller);
                    list_vBox.getChildren().add(loader.load());
                    controller.setTicket(service,user,room,ticket);
                    controller.edit_btn.setOnMousePressed(event->{
                        selectTicket(ticket,user,room,service);
                    });
                    controller.delete_btn.setOnMousePressed(event->{
                        deleteTicket(ticket);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteTicket(Ticket ticket){
        int data =  Db.getInstance().excuteUpdate(ticket.delTicket());
        if(data == 1){
             showError("تم حذف التذكرة "+ticket.getCheckInDate());
            resetData();
            search();
        }else{
           showError("حدث خطأ ما");
        }
    }

    void showMessage(String s){
        message.setText(s);
        message.setTextFill(Paint.valueOf(Color.GREEN.toString()));
        message.setVisible(true);
    }

    void showError(String s){
        message.setText(s);
        message.setTextFill(Paint.valueOf(Color.RED.toString()));
        message.setVisible(true);
    }

    void resetData(){
        room_id.clear();
        id.clear();
        user_id.clear();
        service_id.clear();
        selectedTicket = null;
    }

    public void selectTicket(Ticket ticket,User user,Room room,Service service){
        Integer ticketId = ticket.getId();
        Integer userId = user.getId();
        Integer serviceId = service.getId();
        Integer roomId = room.getId();
        id.setText(ticketId.toString());
        user_id.setText(userId.toString());
        service_id.setText(serviceId.toString());
        room_id.setText(roomId.toString());
        start_date.setValue(LocalDate.parse(ticket.getCheckInDate().toString()));
        end_date.setValue(LocalDate.parse(ticket.getCheckOutDate().toString()));

        selectedTicket = ticket;
    }
}
