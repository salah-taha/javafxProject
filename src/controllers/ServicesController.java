package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ServicesController implements Initializable {
    @FXML
    private TextField id,name,price,description;

    @FXML
    private Label message;

    @FXML
    private TextField search_input;
    @FXML
    private RadioButton service_title;

    @FXML
    private VBox list_vBox;

    public Service selectedService = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service_title.setSelected(true);
    }

    public void insertService(){

        int data = 0;
        Service  service = new Service(name.getText(),description.getText(), Double.parseDouble(price.getText()),0);
        if(selectedService != null){
            service.setId(Integer.parseInt(id.getText()));
            data = Db.getInstance().excuteUpdate(service.updateService());
            if(data == 1){
                showMessage("تم تعديل الخدمة "+service.getName());
                resetData();
                search();
            }else{
                showError("حدث خطأ ما");
            }
        }else{
            data=  Db.getInstance().excuteUpdate(service.insertService());
            if(data == 1){
               showMessage("تم اضافة الخدمة "+service.getName());
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
            if(service_title.isSelected()){
                set = Db.getInstance().executeQuery(Service.searchByTitle(searchText));
            }else{
                try{
                    set = Db.getInstance().executeQuery(Service.searchById(Integer.parseInt(searchText)));
                }catch (Exception e){
                    showError("يجب ادخال رقم");
                }
            }

            int i = 0;
            while(set.next()){
                Service service = new Service(set.getString("name"),set.getString("description"),set.getDouble("price"),set.getInt("id"));

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/service_card.fxml"));
                    ServiceCardController controller = new ServiceCardController();
                    loader.setController(controller);
                    list_vBox.getChildren().add(loader.load());
                    controller.setService(service);
                    controller.edit_btn.setOnMousePressed(event->{
                        selectService(service);
                    });
                    controller.delete_btn.setOnMousePressed(event->{
                        deleteService(service);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteService(Service service){
        int data =  Db.getInstance().excuteUpdate(service.delService());
        if(data == 1){
            showError("تم حذف الخدمة "+service.getName());
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
        price.clear();
        description.clear();
        selectedService = null;
    }

    public void selectService(Service service){
        Integer serviceId = service.getId();
        Double servicePrice = service.getPricePerNight();
        id.setText(serviceId.toString());
        name.setText(service.getName());
        price.setText(servicePrice.toString());
        description.setText(service.getDescription());
        selectedService = service;
    }
}
