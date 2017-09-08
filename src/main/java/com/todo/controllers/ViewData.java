package com.todo.controllers;

import com.todo.classes.ConnectDB;
import com.todo.pojo.GetView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewData implements Initializable {

    public ObservableList<GetView> data;
    public TableView table;
    public TableColumn <GetView, String> idXls;
    public TableColumn <GetView, String> kod;
    public TableColumn <GetView, String> dateMod;
    public TableColumn <GetView, String> vidPredst;
    public TableColumn <GetView, String> datefirstPredst;
    public TableColumn <GetView, String> lastVidPredst;
    public TableColumn <GetView, String> numbOfCorrect;
    public TableColumn <GetView, String> firstVidPredst;
    public TableColumn <GetView, String> numbOfPacket;
    public TableColumn <GetView, String> statorg;
    public TableColumn <GetView, String> fioSend;
    public TableColumn <GetView, String> telNumb;
    public TableColumn <GetView, String> email;
    public TableColumn <GetView, String> timeForRespondetn;
    public TableColumn <GetView, String> timeToGSU;
    public TableColumn <GetView, String> timeToBelstat;
    public TableColumn <GetView, String> comment;
    public static GetView selectData;


    public static Stage stage = new Stage();



    public void close(){
        MainWindowController.stageView.hide();
    }

    public void viewData() throws SQLException {
        Statement statement = ConnectDB.connection.createStatement();
        data = FXCollections.observableArrayList();
        String sql = "SELECT IDXlsFile, KodEsn, dateModify, VidPredst, DateFirstPredst, LastVidPredst, NumbOfCorrect, FirstVidPredst, NumbOfPacket,StatOrg, FioSend, TelNumb, Email,timeForRespondent,timeForSendOnGSU,timeForSendOnBelstat,Comment FROM DataXlsFile LEFT JOIN User on DataXlsFile.idUser = User.IDUser WHERE User.IDUser = " + LoginController.IDuser;
        ResultSet set = statement.executeQuery(sql);
        while (set.next()) {
         GetView _data = new GetView(set.getString(1), set.getString(2), set.getDate(3), set.getString(4), set.getString(5), set.getString(6), set.getString(7), set.getString(8), set.getString(9), set.getString(10), set.getString(11), set.getString(12), set.getString(13), set.getString(14), set.getString(15), set.getString(16),set.getString(17));
            data.add(_data);
        }
        table.setItems(data);



    }

    @FXML
    private void handleEditData() throws IOException {
        selectData = (GetView) table.getSelectionModel().getSelectedItem();
        Parent form_load = null;
        try {
            form_load = FXMLLoader.load(getClass().getClassLoader().getResource("\\fxml\\EditData.fxml"));
            Scene form_scense = new Scene(form_load);
            stage.setScene(form_scense);
        //    stage.initModality(Modality.WINDOW_MODAL);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            viewData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        idXls.setCellValueFactory(new PropertyValueFactory<GetView, String>("id"));
        kod.setCellValueFactory(new PropertyValueFactory<GetView, String>("kodEsn"));
        dateMod.setCellValueFactory(new PropertyValueFactory<GetView, String>("dateModify"));
        vidPredst.setCellValueFactory(new PropertyValueFactory<GetView, String>("vidPredst"));
        datefirstPredst.setCellValueFactory(new PropertyValueFactory<GetView, String>("dateFirstPreds"));
        lastVidPredst.setCellValueFactory(new PropertyValueFactory<GetView, String>("lastVidPredst"));
        numbOfCorrect.setCellValueFactory(new PropertyValueFactory<GetView, String>("numdofCorrect"));
        firstVidPredst.setCellValueFactory(new PropertyValueFactory<GetView, String>("firstVidPredst"));
        numbOfPacket.setCellValueFactory(new PropertyValueFactory<GetView, String>("numbPac"));
        statorg.setCellValueFactory(new PropertyValueFactory<GetView, String>("statOrg"));
        fioSend.setCellValueFactory(new PropertyValueFactory<GetView, String>("contact"));
        telNumb.setCellValueFactory(new PropertyValueFactory<GetView, String>("telNumber"));
        email.setCellValueFactory(new PropertyValueFactory<GetView, String>("email"));
        timeForRespondetn.setCellValueFactory(new PropertyValueFactory<GetView, String>("timeResp"));
        timeToGSU.setCellValueFactory(new PropertyValueFactory<GetView, String>("timeGSU"));
        timeToBelstat.setCellValueFactory(new PropertyValueFactory<GetView, String>("timeBelstat"));
        comment.setCellValueFactory(new PropertyValueFactory<GetView, String>("comment"));
    }
}
