package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import models.Room;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private final Db database = Db.getInstance();

    @FXML
    public ChoiceBox<String> typeBox;
    @FXML
    public CheckBox serviceBox;
    @FXML
    public DatePicker dateBox;

    @FXML
    public VBox list_vBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeBox.getItems().addAll("Single","Double","Triple","King");
        typeBox.setValue("Single");
      LinkedList<Room> rooms = database.getRooms();


        rooms.forEach((room)->{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/home_room_card.fxml"));
                HomeRoomCardController controller = new HomeRoomCardController();
                loader.setController(controller);
                list_vBox.getChildren().add( loader.load());
                controller.setRoom(room);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void searchRooms(){
        String roomType = typeBox.getValue();
        LocalDate date = dateBox.getValue();

        LinkedList<Room> rooms = database.searchRooms(roomType,date);
        list_vBox.getChildren().clear();

        if(rooms.isEmpty()){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/no-data.fxml"));
                Node noData = loader.load();
                list_vBox.getChildren().add(noData);
                return;
            }catch (IOException e){
                e.printStackTrace();
            }
        }


        rooms.forEach((room)->{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/home_room_card.fxml"));
                HomeRoomCardController controller = new HomeRoomCardController();
                loader.setController(controller);
                list_vBox.getChildren().add(loader.load());
                controller.setRoom(room);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
