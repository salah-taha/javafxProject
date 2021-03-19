package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.Room;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RoomsController implements Initializable {
    @FXML
    private TextField id,title,price;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private Label message;

    @FXML
    private TextField search_input;
    @FXML
    private RadioButton room_title;

    @FXML
    private VBox list_vBox;

   public static Room selectedRoom = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        type.getItems().addAll("Single","Double","Triple","King");
        type.setValue("Single");
        room_title.setSelected(true);
    }

   public void insertRoom(){

        int data = 0;
        Room  room = new Room(title.getText(),Room.getRoomTypeFromString(type.getValue()), 0,Double.parseDouble(price.getText()));
     if(selectedRoom != null){
         room.setId(Integer.parseInt(id.getText()));
         data = Db.getInstance().excuteUpdate(room.updateRoom());
         if(data == 1){
             showMessage("تم تعديل الغرفة "+room.getTitle());
             resetData();
             search();
         }else{
            showError("حدث خطأ ما");
         }
     }else{
         data=  Db.getInstance().excuteUpdate(room.insertRoom());
         if(data == 1){
             showMessage("تم اضافة الغرفة "+room.getTitle());
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
            if(room_title.isSelected()){
                 set = Db.getInstance().executeQuery(Room.searchByTitle(searchText));
            }else{
                 try{
                     set = Db.getInstance().executeQuery(Room.searchById(Integer.parseInt(searchText)));
                 }catch (Exception e){
                    showError("يجب ادخال رقم");
                 }
            }

            int i = 0;
            while(set.next()){
                Room room = new Room(set.getString("title"),Room.getRoomTypeFromString(set.getString("type")),set.getInt("id"),set.getDouble("price"));

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/room_card.fxml"));
                        RoomCardController controller = new RoomCardController();
                        loader.setController(controller);
                        list_vBox.getChildren().add(loader.load());
                        controller.setRoom(room);
                        controller.edit_btn.setOnMousePressed(event->{
                            selectRoom(room);
                        });
                        controller.delete_btn.setOnMousePressed(event->{
                            deleteRoom(room);
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteRoom(Room room){
       int data =  Db.getInstance().excuteUpdate(room.delRoom());
        if(data == 1){
            showError("تم حذف الغرفة "+room.getTitle());
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
        title.clear();
        id.clear();
        price.clear();
        type.setValue("Single");
        selectedRoom = null;
    }

   public void selectRoom(Room room){
        Integer roomId = room.getId();
        Double roomPrice = room.getPricePerNight();
        id.setText(roomId.toString());
        title.setText(room.getTitle());
        price.setText(roomPrice.toString());
        if(room.getRoomType() != null){
            type.setValue(room.getRoomTypeString());
        }
        selectedRoom = room;
   }

}
