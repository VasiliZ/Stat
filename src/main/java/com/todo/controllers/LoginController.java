package com.todo.controllers;

import com.todo.classes.ConnectDB;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by I see you on 08.10.2016.
 */
public class LoginController{
    @FXML
    public Button buttonLogin;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passField;
    @FXML
    public Label errLogin;
    @FXML
    public static String IDuser;
    public static String nameUser;

    public static String getNameUser() {
        return nameUser;
    }

    public static void setNameUser(String nameUser) {
        LoginController.nameUser = nameUser;
    }

    private static String nameTable;

    public static String getNameTable() {
        return nameTable;
    }

    public static void setNameTable(String nameTable) {
        LoginController.nameTable = nameTable;
    }




    public void logIn() throws IOException, SQLException {
        checkUser();

    }
    public  void checkUser() throws SQLException{
        ConnectDB db = new ConnectDB();
        try {
            Statement statement = ConnectDB.connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT user.IDUser, user.user, user.pass from User");


            while (set.next()){


                String user = set.getString(2).toString();
                String pass = set.getString(3).toString();
                String userInput = loginField.getText().toLowerCase().toString();
                String passInput = DigestUtils.md5Hex(passField.getText().toString());


                if (userInput.equals(user) && passInput.equals(pass)){
                    IDuser = set.getString(1).toString();
                    nameUser = userInput;



                    // System.out.println("all right");
                    Parent form_load = FXMLLoader.load(getClass().getClassLoader().getResource("\\fxml\\main.fxml"));
                    Scene form_scense = new Scene(form_load);
                    Stage app_stage = (Stage) (buttonLogin.getScene().getWindow());
                    app_stage.setScene(form_scense);
                    app_stage.centerOnScreen();
                    app_stage.show();

                }else {
                    errLogin.setText("Неверный логин или пароль");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void onEnter(){
        passField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    logIn();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });
    }


}
