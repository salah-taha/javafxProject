package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import models.Room;
import models.Service;
import models.Ticket;
import models.User;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class TicketCardController  implements Initializable {
    @FXML
    private Label user_name,id,start_date,end_date,room_title,service_title;
    @FXML
    public ImageView edit_btn,delete_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void setTicket(Service service, User user, Room room, Ticket ticket){
        Integer idText = ticket.getId();
        id.setText(idText.toString());
        user_name.setText(user.getUsername());
        start_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(ticket.getCheckInDate()));
        end_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(ticket.getCheckOutDate()));
        room_title.setText(room.getTitle());
        service_title.setText(service.getName());
    }
}
