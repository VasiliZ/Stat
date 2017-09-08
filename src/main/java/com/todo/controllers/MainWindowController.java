package com.todo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by I see you on 08.10.2016.
 */
public class MainWindowController implements Initializable {

    @FXML
    private MenuItem report;

    public MainWindowController() {

    }

    static Stage stage = new Stage();
    static Stage stageView = new Stage();
    static Stage stageForDBF = new Stage();

    public void load(){
        Parent form_load = null;
        try {
            form_load = FXMLLoader.load(getClass().getClassLoader().getResource("\\fxml\\loadData.fxml"));
            Scene form_scense = new Scene(form_load);
            stage.setScene(form_scense);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void view(){
        try {
            Parent _view = FXMLLoader.load(getClass().getClassLoader().getResource("\\fxml\\showData.fxml"));
            Scene _viewScense = new Scene(_view);
            stageView.setScene(_viewScense);
            stageView.centerOnScreen();
            stageView.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDBF(){
        try {
            Parent view = FXMLLoader.load(getClass().getClassLoader().getResource("\\fxml\\LoadDBFFiles.fxml"));
            Scene viewScense = new Scene(view);
            stageForDBF.setScene(viewScense);
            stageForDBF.centerOnScreen();
            stageForDBF.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void toReport(){
        try {
            Parent view = FXMLLoader.load(getClass().getClassLoader().getResource("\\fxml\\reportData.fxml"));
            Scene viewScense = new Scene(view);
            stageForDBF.setScene(viewScense);
            stageForDBF.centerOnScreen();
            stageForDBF.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (LoginController.getNameUser().equals("svod")){
            report.setVisible(true);
        }
    }

    public void getReport(){

    }
}
