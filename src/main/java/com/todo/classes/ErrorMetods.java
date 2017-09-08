package com.todo.classes;

import javafx.scene.control.Alert;

/**
 * Created by Vasili.Zaitsev on 24.10.2016.
 */
public class ErrorMetods {
    public static void showDialog(String title, String test){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(test);
        alert.showAndWait();


    }
}
