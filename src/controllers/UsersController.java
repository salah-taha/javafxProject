package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class UsersController implements Initializable{
    @FXML
    private TextField id,name,email,password;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private Label message;

    @FXML
    private TextField search_input;
    @FXML
    private RadioButton user_title;

    @FXML
    private VBox list_vBox;

    public User selectedUser = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        type.getItems().addAll("Customer","Employee");
        type.setValue("Customer");
        user_title.setSelected(true);
    }

    public void insertUser(){

        int data = 0;
        User  user = new User(name.getText(),email.getText(), 0,User.getUserTypeFromString(type.getValue()),password.getText());
        if(selectedUser != null){
            user.setId(Integer.parseInt(id.getText()));
            data = Db.getInstance().excuteUpdate(user.updateUser());
            if(data == 1){
                showMessage("تم تعديل المستخدم "+user.getUsername());
                resetData();
                search();
            }else{
                showError("حدث خطأ ما");
            }
        }else{
            data=  Db.getInstance().excuteUpdate(user.insertUser());
            if(data == 1){
                showMessage("تم اضافة المستخدم "+user.getUsername());
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
                set = Db.getInstance().executeQuery(User.searchByTitle(searchText));
            }else{
                try{
                    set = Db.getInstance().executeQuery(User.searchById(Integer.parseInt(searchText)));
                }catch (Exception e){
                    showError("يجب ادخال رقم");
                }
            }

            int i = 0;
            while(set.next()){
                User user = new User(set.getString("name"),set.getString("email"),set.getInt("id"),User.getUserTypeFromString(set.getString("type")),set.getString("password"));

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/user_card.fxml"));
                    UserCardController controller = new UserCardController();
                    loader.setController(controller);
                    list_vBox.getChildren().add(loader.load());
                    controller.setUser(user);
                    controller.edit_btn.setOnMousePressed(event->{
                        selectUser(user);
                    });
                    controller.delete_btn.setOnMousePressed(event->{
                        deleteUser(user);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(User user){
        int data =  Db.getInstance().excuteUpdate(user.delUser());
        if(data == 1){
           showError("تم حذف المستخدم "+user.getUsername());
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
        name.clear();
        id.clear();
        email.clear();
        password.clear();
        type.setValue("Customer");
        selectedUser = null;
    }

    public void selectUser(User user){
        Integer userId = user.getId();
        id.setText(userId.toString());
        name.setText(user.getUsername());
        email.setText(user.getEmail());
        password.setText(user.getPassword());

        if(user.getUserType() != null){
            type.setValue(user.getUserTypeString());
        }
        selectedUser = user;
    }

}
