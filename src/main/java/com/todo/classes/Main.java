package com.todo.classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by I see you on 08.10.2016.
 */
public class Main extends Application {
    public static Parent loginForm;

    public void start(Stage primaryStage) throws Exception {
        loginForm= FXMLLoader.load(getClass().getClassLoader().getResource("\\fxml\\login.fxml"));
        primaryStage.setTitle("Нарушители");
        loginForm.getStylesheets().add(getClass().getClassLoader().getResource("\\css\\loginCss.css").toExternalForm());
        // primaryStage.setScene(new Scene(root));
        primaryStage.setScene(new Scene(loginForm));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
