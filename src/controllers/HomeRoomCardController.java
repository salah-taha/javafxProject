package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.Room;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeRoomCardController implements Initializable {
    String roomTitle;
    int roomId;
    int serviceId;
    String roomType;
    double pricePerNight;

    @FXML
    private Label room_id,room_title,room_type;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setRoom(Room room){
        room_id.setText(String.valueOf(room.getId()));
        room_title.setText(room.getTitle());
        if(room.getRoomType() != null){
            room_type.setText(room.getRoomTypeString());
        }else{
            room_type.setText("Single");
        }
    }
}
