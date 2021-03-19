package controllers;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import transitions.FadeTransition;

import java.io.IOException;

public class Main extends Application {

    private double[] xOffset = {0}, yOffset = {0};

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../ui/splash.fxml"));
        System.out.println("hi");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        AppController.stage = primaryStage;
        AppController.node = root;
        FadeTransition.fadeInFadeOut(root, Duration.seconds(2),(e)->{
            try {
                final Parent parent = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
                AppController.node = parent;
                primaryStage.setScene(new Scene(parent));
                parent.setOnMousePressed(event -> {
                        xOffset[0] = primaryStage.getX() - event.getScreenX();
                        yOffset[0] = primaryStage.getY() - event.getScreenY();
                });
                parent.setOnMouseDragged(event -> {
                        primaryStage.setX(event.getScreenX() + xOffset[0]);
                        primaryStage.setY(event.getScreenY() + yOffset[0]);
                });
                primaryStage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
