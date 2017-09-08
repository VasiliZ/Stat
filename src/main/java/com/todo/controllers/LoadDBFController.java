package com.todo.controllers;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.sun.javafx.tk.Toolkit;
import com.todo.classes.ConnectDB;
import com.todo.classes.ErrorMetods;
import com.todo.classes.Main;
import com.todo.pojo.GetDataSoato;
import com.todo.pojo.GetDbfFileReg;


import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.jamel.dbf.processor.DbfProcessor;
import org.jamel.dbf.processor.DbfRowMapper;
import org.jamel.dbf.utils.DbfUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.todo.classes.ErrorMetods.*;

/**
 * Created by Vasili.Zaitsev on 30.11.2016.
 */
public class LoadDBFController {
    private Connection connection;
    private ResultSet set;
    private String pathToFile;
    @FXML
    private TextField pathToDBF;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label labelCount;
    private List<GetDbfFileReg> listRow;
    private List<GetDataSoato> dataSoato;
    private InputStream inputStream;
    private String fileName;
    private String tstatrs = "TSTATRS.dbf";
    private String spsoato = "SP_SOATO.DBF";
    @FXML
    public void getFiles() {
        try {

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Открыть файл");
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DBF Files", "*.dbf"));
            File select = chooser.showOpenDialog(Main.loginForm.getScene().getWindow());
            pathToDBF.setText(select.getPath());
            pathToFile = select.getPath();
            fileName = select.getName();

        } catch (Exception e) {

        }

    }
    public void getRowOnDbfFile(String path) {
//        Platform.setImplicitExit(false);
        listRow = new ArrayList<GetDbfFileReg>();

        try {

            inputStream = new FileInputStream(path);
            DBFReader reader = new DBFReader(inputStream);
            reader.setCharactersetName("cp866");

            Object[] row;
            //  int j=0;
            while ((row = reader.nextRecord()) != null) {

                GetDbfFileReg fileReg = new GetDbfFileReg();
                for (int i = 0; i < row.length; i++) {
                    if (reader.getField(i).getName().equals("KNO")) {
                        fileReg.setKno(row[i].toString());
                    } else if (reader.getField(i).getName().equals("KNAME")) {
                        fileReg.setKname(row[i].toString().trim());
                    } else if (reader.getField(i).getName().equals("TEFON")) {
                        fileReg.setTefon(row[i].toString().trim());
                    } else if (reader.getField(i).getName().equals("INDEX")) {
                        fileReg.setIndex(row[i].toString().trim());
                    } else if (reader.getField(i).getName().equals("SOATO")) {
                        fileReg.setSoato(row[i].toString());
                    } else if (reader.getField(i).getName().equals("SOATF")) {
                        fileReg.setSoatof(row[i].toString());
                    } else if (reader.getField(i).getName().equals("ULICA")) {
                        fileReg.setUlica(row[i].toString());
                    } else if (reader.getField(i).getName().equals("DOMKV")) {
                        fileReg.setDomkv(row[i].toString().trim());
                    } else if (reader.getField(i).getName().equals("BALNS")) {
                        fileReg.setBalns(row[i].toString());
                    } else if (reader.getField(i).getName().equals("VIDBL")) {
                        fileReg.setVidbl(row[i].toString());
                    } else if (reader.getField(i).getName().equals("NALOG")){
                        fileReg.setNalog(row[i].toString());
                    }
                }
                listRow.add(fileReg);
                   /*     labelCount.setText(String.valueOf(j));
                        j++;*/
            }
        } catch (FileNotFoundException e) {
            ErrorMetods.showDialog("Внимание", "Выберите файл");
        } catch (DBFException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {

        }
        putDataToTstatrs();
        ErrorMetods.showDialog("Внимание", "Загружено");
    }
    @FXML
    public void sendDataToBase() {
        Platform.runLater(() ->
                getRowOnDbfFile(pathToFile));


    }
    public void checkDataInTableTstatrs() {
        try {
            if (!(pathToFile.equals(""))) {
                // ConnectDB db = new ConnectDB();
                Statement statement = ConnectDB.connection.createStatement();
                String sql = "Select * from Tstatr";
                set = statement.executeQuery(sql);


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание");
                alert.setHeaderText("Внимание");
                alert.setContentText("Перед началом работу таблицу необходимо очистить");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    String sqlDel = "TRUNCATE TABLE Tstatr";
                    statement.execute(sqlDel);
                    sendDataToBase();
                } else {
                    alert.close();
                }

            } else {
                ErrorMetods.showDialog("Внимание", "Выберите файл");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //    e.printStackTrace();
        } catch (NullPointerException e) {
            ErrorMetods.showDialog("Внимание", "Выберите файл");
        }
    }
    public void putDataToTstatrs() {
        try {
            for (int i = 0; i < listRow.size(); i++) {
                String sql = "INSERT INTO Tstatr" + "(KNO, KNAME, TEFON, INDEXn, SOATO, SOATF, ULICA, DOMKV, BALNS,VIDBL, nalog) VALUES " + "(?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = ConnectDB.connection.prepareStatement(sql);
                preparedStatement.setString(1, listRow.get(i).getKno());
                preparedStatement.setString(2, listRow.get(i).getKname());
                preparedStatement.setString(3, listRow.get(i).getTefon());
                preparedStatement.setString(4, listRow.get(i).getIndex());
                preparedStatement.setString(5, listRow.get(i).getSoato());
                preparedStatement.setString(6, listRow.get(i).getSoatof());
                preparedStatement.setString(7, listRow.get(i).getUlica());
                preparedStatement.setString(8, listRow.get(i).getDomkv());
                preparedStatement.setString(9, listRow.get(i).getBalns());
                preparedStatement.setString(10, listRow.get(i).getVidbl());
                preparedStatement.setString(11, listRow.get(i).getNalog());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getDataOnSoatoFile(String path) {
        System.out.println("Heredataon");
        dataSoato = new ArrayList<GetDataSoato>();
        try {
            inputStream = new FileInputStream(path);
            DBFReader reader = new DBFReader(inputStream);
            reader.setCharactersetName("cp866");


               // GetDataSoato _dataSoato = new GetDataSoato();

            Object[] row;
            while ((row = reader.nextRecord())!=null){
                GetDataSoato _dataSoato = new GetDataSoato();
                for (int i = 0; i< row.length;i++){
                    if (reader.getField(i).getName().equals("KOD")) {
                        _dataSoato.setSoato(row[i].toString());
                    } else if (reader.getField(i).getName().equals("NAME")) {
                        _dataSoato.setName(row[i].toString().trim());
                    }
                }
                dataSoato.add(_dataSoato);

            }
            checkDataInTableSoato();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DBFException e) {
            e.printStackTrace();
        }


    }
    public void putDataStatrsToDB(){
        System.out.println("HerePut");
        try{
            for(int i = 0; i<dataSoato.size();i++){
                String s = dataSoato.get(i).getSoato();
                if (dataSoato.get(i).getSoato().startsWith("4")) {
                    String sql = "INSERT INTO soato" + "(soato, name) values" + "(?,?)";
                    PreparedStatement statement = ConnectDB.connection.prepareStatement(sql);
                    statement.setString(1, dataSoato.get(i).getSoato());
                    statement.setString(2, dataSoato.get(i).getName());
                    statement.executeUpdate();
                }
           }
            ErrorMetods.showDialog("Внимание", "Загружено");
        }catch (SQLException e){e.printStackTrace();}

    }
    public void checkDataInTableSoato() {
        System.out.println("check");
        if (!(pathToFile.equals(""))) {
            try {
                Statement statement = ConnectDB.connection.createStatement();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Внимание");
                alert.setHeaderText("Внимание");
                alert.setContentText("Перед началом работу таблицу необходимо очистить");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    String sqlDel = "TRUNCATE TABLE soato";
                    statement.execute(sqlDel);
                   putDataStatrsToDB();
                } else {
                    alert.close();
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    public void onAction(){
        try {
            if (fileName.equals(tstatrs)) {
                checkDataInTableTstatrs();
            } else if (fileName.equals(spsoato)) {
                getDataOnSoatoFile(pathToFile);
                System.out.println("here");
            }
        }catch (NullPointerException e){
            ErrorMetods.showDialog("Внимание", "Вы выбрали неправильный файл");

        }

    }
}

