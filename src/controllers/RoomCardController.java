package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import models.Room;


import java.net.URL;
import java.util.ResourceBundle;

public class RoomCardController implements Initializable {
   @FXML
  private Label id,title;
   @FXML
    public ImageView edit_btn,delete_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void setRoom(Room room){
        Integer idText = room.getId();
        id.setText(idText.toString());
        title.setText(room.getTitle());


    }

}
