package com.todo.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by I see you on 08.10.2016.
 */
public class ConnectDB {
    public static String uri = "jdbc:mysql://localhost/disturber";
    public static String logindb = "root";
    public static String passDB = "";
    public static Connection connection;

    public ConnectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");


        } catch (Exception e) {
            System.out.println("Not connect");
            e.printStackTrace();

        }
        try {
            connection = DriverManager.getConnection(uri, logindb, passDB);

            System.out.println("Connect");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
            System.out.println("Conn close");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
