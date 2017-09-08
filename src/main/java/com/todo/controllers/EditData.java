package com.todo.controllers;


import com.todo.classes.ConnectDB;
import com.todo.classes.ErrorMetods;
import com.todo.pojo.GetView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Vasili.Zaitsev on 21.11.2016.
 */
public class EditData implements Initializable {
    @FXML
    public Label LabelTxt;
    @FXML
    public Label LabelOKPO;
    @FXML
    public Label LabelDetails;
    @FXML
    public Label LabelDateModify;
    @FXML
    public Label LabelVidPredst;
    @FXML
    public Label LabelDatefistPredst;
    @FXML
    public Label LabelLastVid;
    @FXML
    public Label LabelnumdofCorrect;
    @FXML
    public Label LabelfirstVidPredst;
    @FXML
    public Label LabelnumbPac;
    @FXML
    public Label LabelstatOrg;
    @FXML
    public Label Labelcontact;
    @FXML
    public Label LabeltelNumber;
    @FXML
    public Label Labelemail;
    @FXML
    public Label LabeltimeResp;
    @FXML
    public Label LabeltimeGSU;
    @FXML
    public Label LabeltimeBelstat;
    @FXML
    public TextField FieldComments;
    private PreparedStatement statement;


    GetView view = ViewData.selectData;
    public void save(){

    }
    @FXML
    public void saveEditData(){
        String updateTxt = FieldComments.getText();
        System.out.println(updateTxt);
        if (!updateTxt.equals("")) {

            ViewData.selectData.setComment(updateTxt);
            ConnectDB db = new ConnectDB();
            try{
                String sqlUpd = "UPDATE DataXlsFile set Comment = ? WHERE IDXlsFile ="+ ViewData.selectData.getId();
                statement = ConnectDB.connection.prepareStatement(sqlUpd);
                statement.setString(1,ViewData.selectData.getComment());
                statement.executeUpdate();
                ViewData.stage.hide();
                MainWindowController controller=new MainWindowController();
                controller.view();


            }catch (Exception ex){ex.printStackTrace();}

        }else {
            ErrorMetods.showDialog("Внимание", "Введите комментарий");
        }






    }

    public void hideEditComment(){
        ViewData.stage.close();
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LabelOKPO.setText(ViewData.selectData.getKodEsn());
        LabelDateModify.setText(String.valueOf(ViewData.selectData.getDateModify()));
        LabelVidPredst.setText(ViewData.selectData.getVidPredst());
        LabelDatefistPredst.setText(ViewData.selectData.getDateFirstPreds());
        LabelLastVid.setText(ViewData.selectData.getLastVidPredst());
        LabelnumdofCorrect.setText(ViewData.selectData.getNumdofCorrect());
        LabelfirstVidPredst.setText(ViewData.selectData.getFirstVidPredst());
        LabelnumbPac.setText(ViewData.selectData.getNumbPac());
        LabelstatOrg.setText(ViewData.selectData.getStatOrg());
        Labelcontact.setText(ViewData.selectData.getContact());
        LabeltelNumber.setText(ViewData.selectData.getTelNumber());
        Labelemail.setText(ViewData.selectData.getEmail());
        LabeltimeResp.setText(ViewData.selectData.getTimeResp());
        LabeltimeGSU.setText(ViewData.selectData.getTimeGSU());
        LabeltimeBelstat.setText(ViewData.selectData.getTimeBelstat());
        FieldComments.setText(ViewData.selectData.getComment());


    }
}
