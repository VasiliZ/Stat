package com.todo.controllers;

import com.todo.classes.ConnectDB;
import com.todo.classes.ErrorMetods;
import com.todo.classes.Main;
import com.todo.classes.XlsFile;
import com.todo.pojo.UpdateEsn;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by I see you on 08.10.2016.
 */
public class LoadData {

    public static String files;
    public Button button;
    public Button loadToDB;
    public Button windowClose;
    public Label countLabel;
    public TextField pathField;
    public String pathToDir;
    public ComboBox comboPeriod;
    public ComboBox comboForm;
    public DatePicker timeForRespondent;
    public DatePicker timeForSendOnGSU;
    public DatePicker timeForSendOnBelstat;
    public HashMap<String, Object> map = new HashMap<String, Object>();
    public HashMap<String, Object> mapPeriod = new HashMap<String, Object>();
    private List<UpdateEsn> listEsn = new ArrayList<>();
    private static String nameTable;
    private java.util.Date dateResp;
    private File selectDir;

    public static String getNameTable() {
        return nameTable;
    }
    public void clicked() throws IOException, BiffException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Xls files", "*.xls")
        );
        selectDir = fileChooser.showOpenDialog(Main.loginForm.getScene().getWindow());


        pathField.setText(selectDir.getPath());
        pathToDir = selectDir.getPath();
        files = selectDir.getAbsolutePath();
       /* if (selectDir != null) {
            XlsFile file = new XlsFile();
            XlsFile.getData();
        }*/
    }
    public Date getDate(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = null;
        try {
            java.util.Date date1 = format.parse(s);
            date2 = new Date(date1.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }

    public void setSoato(){

        String sqlSoato = "SELECT Tstatr.SOATO, "+nameTable+".KodEsn FROM "+nameTable+" LEFT JOIN Tstatr on KodEsn = Kno";
        try {
            Statement statement = ConnectDB.connection.createStatement();
            ResultSet set = statement.executeQuery(sqlSoato);

            while (set.next()){
                for (int i=0; i<XlsFile.list.size();i++) {
                    XlsFile.list.get(i).setSoato(set.getString(1));
                    System.out.println(XlsFile.list.get(i).getSoato());
                    int cout = 1 + i;
                    if (set.getString(2).equals(XlsFile.list.get(i).getKodEsn())) {

                        String sqlUpd = "UPDATE " + nameTable + " SET soato = "+XlsFile.list.get(i).getSoato()+" WHERE KodEsn =" + XlsFile.list.get(i).getKodEsn();
                        Statement statement1 = ConnectDB.connection.createStatement();

                        statement1.executeUpdate(sqlUpd);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void loadData() throws IOException, BiffException {

        if (selectDir != null) {
           XlsFile file = new XlsFile();
            XlsFile.getData();
        }

        String valForm = getSelectValueComboForm();
        String valPeriod = getSelectValuecomboPeriod();
        String timeForRespondentS = gettimeForRespondent();
        String timeForSendOnGSUs = timeForSendOnGSU();
        String timeForSendOnBelstats = timeForSendOnBelstat();
        dateResp = getDate(timeForRespondentS);


        nameTable = "DataXlsFile" + LoginController.getNameUser();

        String sql = "CREATE TABLE IF NOT EXISTS "+nameTable+ " (" +
                "  IDXlsFile int(11) NOT NULL AUTO_INCREMENT," +
                "  KodEsn varchar(20) NOT NULL," +
                "  dateModify varchar(10) NOT NULL," +
                "  VidPredst varchar(20) NOT NULL," +
                "  DateFirstPredst varchar(10) NOT NULL," +
                "  LastVidPredst varchar(20) NOT NULL," +
                "  NumbOfCorrect varchar(10) NOT NULL," +
                "  FirstVidPredst varchar(20) NOT NULL," +
                "  NumbOfPacket varchar(10) NOT NULL," +
                "  StatOrg varchar(100) NOT NULL," +
                "  FioSend longtext," +
                "  TelNumb longtext," +
                "  Email longtext," +
                "  idForm varchar(100) NOT NULL," +
                "  IDPeriod varchar(100) NOT NULL," +
                "  timeForRespondent varchar(20) NOT NULL," +
                "  timeForSendOnGSU varchar(20) NOT NULL," +
                "  timeForSendOnBelstat varchar(20) NOT NULL," +
                "  idUser varchar(20) DEFAULT NULL," +
                "  Comment varchar(255) DEFAULT NULL," +
                "  disturbance varchar(255) DEFAULT NULL," +
                "  soato varchar(255) DEFAULT NULL," +
                "  PRIMARY KEY (IDXlsFile)," +
                "  UNIQUE KEY IDXlsFile (IDXlsFile)" +
                ") ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=0 ;";
        String existSql = "drop TABLE if exists " + nameTable;
        try{
        Statement statement1 = ConnectDB.connection.createStatement();
        statement1.execute(existSql);
        statement1.execute(sql);}catch (SQLException e){
            e.printStackTrace();
        }
        if (pathToDir!=null) {

            for (int i = 0; i < XlsFile.list.size(); i++) {
                String esn = XlsFile.list.get(i).getKodEsn();
                for (int j = 0; j < XlsFile.list.size(); j++) {
                    if ((esn.equals(XlsFile.list.get(j).getKodEsn())&&XlsFile.list.get(i).getId()!=XlsFile.list.get(j).getId())) {
                        if (XlsFile.list.get(i).getDateModify().getTime() > XlsFile.list.get(j).getDateModify().getTime()) {
                            XlsFile.list.remove(j);
                            }else{
                                XlsFile.list.remove(i);
                            }
                    }
                }
            }

            for (int i = 0; i<XlsFile.list.size();i++){

                java.util.Date date = XlsFile.list.get(i).getDateFirstPreds();
                long x= date.getTime();
                long y= XlsFile.list.get(i).getDateModify().getTime();
                long z = dateResp.getTime();
              if ((z<x)&&(z>y)){
                  XlsFile.list.get(i).setDisturbance("Отчет предоставлен с корректировками \n и с опозданием");
              }else if (y>z){
                  XlsFile.list.get(i).setDisturbance("Отчет предоставлен с корректировками");
              }else if(x>z){
                  XlsFile.list.get(i).setDisturbance("Отчет предоставлен с опозданием");
              }
            }





            int count=0;
          //  ConnectDB db = new ConnectDB();
            for (int i = 0; i < XlsFile.list.size(); i++) {
                try {
                    if (XlsFile.list.get(i).getKodEsn().startsWith("4",8)) {
                        String sql1 = "INSERT INTO " +LoadData.getNameTable() + "(KodEsn, dateModify, VidPredst, DateFirstPredst,LastVidPredst, NumbOfCorrect, FirstVidPredst, NumbOfPacket, StatOrg,FioSend, TelNumb, Email,idForm, IDPeriod, timeForRespondent, timeForSendOnGSU, timeForSendOnBelstat, idUser, disturbance, soato ) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement statement = ConnectDB.connection.prepareStatement(sql1);
                        statement.setString(1, XlsFile.list.get(i).getKodEsn());
                        Date sqlDate = new Date(XlsFile.list.get(i).getDateModify().getTime());
                        statement.setDate(2, sqlDate);
                        statement.setString(3, XlsFile.list.get(i).getVidPredst());
                        Date sqlDateq = new Date(XlsFile.list.get(i).getDateFirstPreds().getTime());
                        statement.setDate(4, sqlDateq);
                        statement.setString(5, XlsFile.list.get(i).getLastVidPredst());
                        statement.setString(6, XlsFile.list.get(i).getNumdofCorrect());
                        statement.setString(7, XlsFile.list.get(i).getFirstVidPredst());
                        statement.setString(8, XlsFile.list.get(i).getNumbPac());
                        statement.setString(9, XlsFile.list.get(i).getStatOrg());
                        statement.setString(10, XlsFile.list.get(i).getContact());
                        statement.setString(11, XlsFile.list.get(i).getTelNumber());
                        statement.setString(12, XlsFile.list.get(i).getEmail());
                        statement.setString(13, valForm);
                        statement.setString(14, valPeriod);
                        statement.setString(15, timeForRespondentS);
                        statement.setString(16, timeForSendOnGSUs);
                        statement.setString(17, timeForSendOnBelstats);
                        statement.setString(18, LoginController.IDuser);
                        statement.setString(19, XlsFile.list.get(i).getDisturbance());
                        statement.setString(20, XlsFile.list.get(i).getSoato());
                        statement.executeUpdate();
                        count++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countLabel.setText(String.valueOf(count));
            }
        }else{
            safetoBase();
        }
        updateRow();
        _upd();

        sendFinalData();
        XlsFile.list.clear();
        pathField.setText("");

    }
    public void safetoBase(){
        ErrorMetods.showDialog("Внимание", "Файл не найден");
    }
    public void setToComboForm(){
        try {
            Statement statment = ConnectDB.connection.createStatement();
            String sql = "SELECT Forms.IDForm, Forms.NameForm FROM Forms LEFT JOIN User ON Forms.IDUser = user.IDUser WHERE User.IDUser =" + LoginController.IDuser;
            ResultSet set = statment.executeQuery(sql);
            while (set.next()){
                String idForm = set.getString(1);
                String nameForm = set.getString(2);
                map.put(idForm, nameForm);
                comboForm.getItems().add(nameForm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getSelectValueComboForm() {
        try{
            String formName = comboForm.getSelectionModel().getSelectedItem().toString();
            for (int i = 0; i < map.size(); i++) {
            }
            for (Map.Entry map1 : map.entrySet()) {
                if (map1.getValue().equals(formName.toString())) {
                    return (String) map1.getKey();
                }
            }

        }catch (NullPointerException e){
            ErrorMetods.showDialog("Внимание", "Выберите форму");
        }
        return null;

    }
    public void setToComboPeriod(){
        try {
            Statement statement = ConnectDB.connection.createStatement();
            String sql = "Select Period.IDPeriod, Period.namePeriod, Period.yearPeriod From Period";
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                String idPeriod = set.getString(1);
                String namePeriod = set.getString(2);
                String yearPeriod = set.getString(3);
                String concat = namePeriod + " " + yearPeriod;
                mapPeriod.put(idPeriod,concat);
                comboPeriod.getItems().add(concat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public String getSelectValuecomboPeriod(){

        try{
            String formName = comboPeriod.getSelectionModel().getSelectedItem().toString();
            for (int i = 0; i < mapPeriod.size(); i++) {
            }
            for (Map.Entry map1 : mapPeriod.entrySet()) {
                if (map1.getValue().equals(formName.toString())) {
                    return (String) map1.getKey();
                }
            }
        }catch (NullPointerException e) {
            ErrorMetods.showDialog("Внимание", "Выберите период");
        }

        return null;
    }
    public String gettimeForRespondent(){
        try{

            return String.valueOf(timeForRespondent.getValue());
        }catch (NullPointerException e){
            ErrorMetods.showDialog("Внимание", "Выберите дату передачи для респондента");
        }
        return null;
    }
    public String timeForSendOnGSU(){
        try{

            return String.valueOf(timeForSendOnGSU.getValue());
        }catch (NullPointerException e){
            ErrorMetods.showDialog("Внимание", "Выберите дату передачи на ГСУ");
        }
        return null;
    }
    public String timeForSendOnBelstat(){
        try{

            return String.valueOf(timeForSendOnBelstat.getValue());
        }catch (NullPointerException e){
            ErrorMetods.showDialog("Внимание", "Выберите дату передачи на Белстат");
        }
        return null;
    }
    @FXML
    public void initialize(){
        setToComboForm();
        setToComboPeriod();
    }
    @FXML
    public void close(){
       MainWindowController.stage.hide();
    }
    public void updateRow(){
        try{
            Statement statement = ConnectDB.connection.createStatement();
            String sql = "SELECT DISTINCT +"+LoadData.getNameTable()+".IDXlsFile,"+LoadData.getNameTable()+".KodEsn,"+" Tstatr.KNO,Tstatr.BALNS FROM "+ LoadData.getNameTable()+" LEFT JOIN Tstatr ON "+LoadData.getNameTable()+".KodEsn"+" = Tstatr.KNO WHERE Tstatr.BALNS !=\"\"";
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                UpdateEsn esn = new UpdateEsn();
                esn.setId(set.getInt(1));
                esn.setKodesn(set.getString(2));
                esn.setKno(set.getString(3));
                esn.setBalns(set.getString(4));
             //   esn.setKodesn(esn.getBalns());

                listEsn.add(esn);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void _upd() {
        System.out.println(listEsn.size());
        for (int i = 0; i < listEsn.size(); i++) {
            String sql = "UPDATE "+LoadData.getNameTable()+" SET KodEsn = ? WHERE "+LoadData.getNameTable()+".IDXlsFile "+"="+listEsn.get(i).getId();
            try {
                System.out.println("Updated");
                PreparedStatement statement = ConnectDB.connection.prepareStatement(sql);
                statement.setString(1, listEsn.get(i).getBalns());
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (int i=0;i<XlsFile.list.size();i++){
            for (int j=0; j<listEsn.size();j++){
                if (XlsFile.list.get(i).getKodEsn().equals(listEsn.get(j).getKno())){
                    XlsFile.list.get(i).setKodEsn(listEsn.get(j).getBalns());

                }
            }
        }
        setSoato();
    }
    public void sendFinalData(){
            String sql = "INSERT INTO DataXlsFile (KodEsn,dateModify,VidPredst,DateFirstPredst,LastVidPredst," +
                    "                         NumbOfCorrect,FirstVidPredst,NumbOfPacket, StatOrg, FioSend," +
                    "                         TelNumb,Email, idForm, IDPeriod, timeForRespondent," +
                    "                         timeForSendOnGSU, timeForSendOnBelstat, idUser, Comment, disturbance, soato )" +
                    "  SELECT KodEsn,dateModify,VidPredst,DateFirstPredst,LastVidPredst," +
                    "    NumbOfCorrect,FirstVidPredst,NumbOfPacket, StatOrg, FioSend, " +
                    "    TelNumb,Email, idForm, IDPeriod, timeForRespondent, " +
                    "    timeForSendOnGSU, timeForSendOnBelstat, idUser, Comment, disturbance, soato " +
                    "                        FROM "+LoadData.getNameTable();
            try {
                Statement statement = ConnectDB.connection.createStatement();
                statement.execute(sql);
                String existSql = "drop TABLE if exists " +LoadData.getNameTable();
                Statement statement1 = ConnectDB.connection.createStatement();
                statement1.execute(existSql);
                statement1.execute(sql);
                XlsFile.list = null;
                listEsn = null;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
}
