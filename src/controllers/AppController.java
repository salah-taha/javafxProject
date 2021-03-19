package controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import transitions.FadeTransition;

import java.io.IOException;

public class AppController {


    final private double[] xOffset = {0}, yOffset = {0};
    private Pane selectedPane;

    @FXML
    public BorderPane mainApp;

    @FXML
    public Pane btn_home, btn_rooms, btn_services, btn_users,btn_tickets;


    @FXML
    public Pane menu_pane;

    @FXML
    private Label error_message;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    public static Stage stage;
    public static Node node;

    public  void close(){
        FadeTransition.fadeOut(node,Duration.seconds(2), e -> System.exit(0));
    }

    public void login(){
      boolean loggedIn =  Db.getInstance().login(username.getText(),password.getText());
       if(loggedIn){
           FadeTransition.fadeOut(node, Duration.seconds(1),(e)->{

               try {
                   Parent root = FXMLLoader.load(getClass().getResource("../ui/app.fxml"));
                   stage.setScene(new Scene(root));
                   root.setOnMousePressed(event -> {
                       xOffset[0] = stage.getX() - event.getScreenX();
                       yOffset[0] = stage.getY() - event.getScreenY();
                   });
                   root.setOnMouseDragged(event -> {
                       stage.setX(event.getScreenX() + xOffset[0]);
                       stage.setY(event.getScreenY() + yOffset[0]);
                   });
                   node = root;
                   stage.show();
                   selectedPane = menu_pane;
               } catch (IOException ioException) {
                   ioException.printStackTrace();

               }

           });
       }else{
           error_message.setVisible(true);
       }
    }

    public void logout(){
        FadeTransition.fadeOut(node,Duration.seconds(1),(e)->{

            try {
                Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
                node = root;
                stage.setScene(new Scene(root));
                root.setOnMousePressed(event -> {
                    xOffset[0] = stage.getX() - event.getScreenX();
                    yOffset[0] = stage.getY() - event.getScreenY();
                });
                root.setOnMouseDragged(event -> {
                    stage.setX(event.getScreenX() + xOffset[0]);
                    stage.setY(event.getScreenY() + yOffset[0]);
                });
                stage.show();
                selectedPane = null;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
    }


    //navigation
    public void logoClick(){
        if(selectedPane != menu_pane){
            if(selectedPane != null){
                selectedPane.getStyleClass().clear();
                selectedPane.getStyleClass().add("btn_menu");
            }
            mainApp.setCenter(menu_pane);
            selectedPane = menu_pane;
        }
    }

    public void navigate(String route, Pane pane) throws Exception{
        if(selectedPane != pane){
            if(selectedPane != null){
                selectedPane.getStyleClass().clear();
                selectedPane.getStyleClass().add("btn_menu");
            }
            Pane scene = FXMLLoader.load(getClass().getResource(route));
            mainApp.setCenter(scene);
            selectedPane = pane;
            selectedPane.getStyleClass().clear();
            selectedPane.getStyleClass().add("btn_menu_selected");
        }
    }

    public void navigateHome() throws Exception{
       navigate("../ui/home.fxml",btn_home);
    }
    public void navigateRooms() throws Exception{
        navigate("../ui/rooms.fxml",btn_rooms);
    }
    public void navigateUsers() throws Exception{
        navigate("../ui/users.fxml",btn_users);
    }
    public void navigateServices() throws Exception{
        navigate("../ui/services.fxml",btn_services);
    }
    public void navigateTickets()throws Exception{
        navigate("../ui/tickets.fxml",btn_tickets);
    }

}
