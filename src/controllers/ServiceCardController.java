package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import models.Service;

import java.net.URL;
import java.util.ResourceBundle;

public class ServiceCardController  implements Initializable {
    @FXML
    private Label id,title;
    @FXML
    public ImageView edit_btn,delete_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void setService(Service service){
        Integer idText = service.getId();
        id.setText(idText.toString());
        title.setText(service.getName());
    }
}
