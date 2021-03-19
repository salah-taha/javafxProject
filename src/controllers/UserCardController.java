package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class UserCardController implements Initializable {
    @FXML
    private Label id,title;
    @FXML
    public ImageView edit_btn,delete_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void setUser(User user){
        Integer idText = user.getId();
        id.setText(idText.toString());
        title.setText(user.getUsername());
    }
}
