package com.todo.controllers;

import com.todo.classes.ConnectDB;
import com.todo.classes.Main;
import com.todo.pojo.GetReportData;
import com.todo.pojo.Rai;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by Vasili.Zaitsev on 05.12.2016.
 */
public class GetRepornData implements Initializable {
    @FXML
    private TableView tableReport;
    @FXML
    private TableColumn<GetReportData, String> soato;
    @FXML
    private TableColumn<GetReportData, String> rai;
    @FXML
    private TableColumn<GetReportData, String> okpo;
    @FXML
    private TableColumn<GetReportData, String> nalog;
    @FXML
    private TableColumn<GetReportData, String> name;
    @FXML
    private TableColumn<GetReportData, String> form;
    @FXML
    private TableColumn<GetReportData, String> period;
    @FXML
    private TableColumn<GetReportData, String> vid_disturb;
    @FXML
    private TableColumn<GetReportData, Date> FactDatePredst;
    @FXML
    private TableColumn<GetReportData, Date> DateForResp;
    @FXML
    private TableColumn<GetReportData, String> DateForGSU;
    @FXML
    private TableColumn<GetReportData, String> DateForBelstat;
    @FXML
    private TableColumn<GetReportData, String> Comment;
    @FXML
    private TableColumn<GetReportData, String> factAdr;
    @FXML
    private TableColumn<GetReportData, String> phone;
    @FXML
    private TableColumn<GetReportData, String> email;
    @FXML
    private TableColumn<GetReportData, String> otdel;
    @FXML
    private TextField txtOkpo;
    @FXML
    private Button export;
    private List<String> nameRow;

    private ObservableList<GetReportData> listReport;
    private List<Rai> list;
    private ObservableList<GetReportData> sortedList = FXCollections.observableArrayList();
    private ObservableList<GetReportData> sortedListSecond = FXCollections.observableArrayList();
    @FXML
    private Button getFiltredData;
    private HashMap<String, Object> map = new HashMap<String, Object>();
    @FXML
    private ComboBox comboForms;
    private String valueCombo;
    private String formsFilter;
    private File selectDir;


    public void getReport(){
        String okpoFiltering;
        if (txtOkpo.getText().isEmpty()){
            okpoFiltering = "%";

        }else {
            okpoFiltering = txtOkpo.getText();
        }
        try {

            valueCombo = getSelectValueComboForm();
        }catch (NullPointerException e){
            valueCombo = "%";
        }
        String ss = "";
        String sqlReport = "SELECT DISTINCT DataXlsFile.KodEsn, " +
                "  soato.soato, " +
                "  Tstatr.NALOG,  " +
                "  Tstatr.KNAME, " +
                "  Forms.NameForm, " +
                "  Period.namePeriod, " +
                "  Period.yearPeriod, " +
                "  DataXlsFile.disturbance, " +
                "  DataXlsFile.DateFirstPredst, " +
                "  DataXlsFile.timeForRespondent, " +
                "  DataXlsFile.timeForSendOnGSU, " +
                "  DataXlsFile.timeForSendOnBelstat, " +
                "  soato.name, " +
                "  Tstatr.ULICA, " +
                "  Tstatr.DOMKV, " +
                "  DataXlsFile.TelNumb, " +
                "  DataXlsFile.Email, " +
                "  DataXlsFile.Comment, " +
                "  User.user" +
                "      FROM DataXlsFile " +
                "          LEFT JOIN Tstatr  on (DataXlsFile.KodEsn = Tstatr.KNO ) " +
                "          LEFT JOIN soato on (DataXlsFile.soato = soato.soato) " +
                "          LEFT JOIN Forms ON (DataXlsFile.idForm = Forms.IDForm) " +
                "          LEFT JOIN Period on (DataXlsFile.IDPeriod = Period.IDPeriod) " +
                "          LEFT JOIN User ON (DataXlsFile.idUser = User.IDUser) " +
                " WHERE DataXlsFile.KodEsn like '" + okpoFiltering + "%' AND Forms.NameForm LIKE '" +valueCombo+ "%'";
        if (sqlReport.contains("null")){
            String n = "null";
           sqlReport = sqlReport.replaceAll(n, "");}
        nameRow = new ArrayList<>();
        nameRow.add("ЕСН");
        nameRow.add("Соато");
        nameRow.add("Район");
        nameRow.add("ОКЮЛП");
        nameRow.add("Наименование организации");
        nameRow.add("Форма");
        nameRow.add("Период");
        nameRow.add("Нарушение");
        nameRow.add("Фактическая дата предоставления");
        nameRow.add("Дата для респондента");
        nameRow.add("Дата для ГСУ");
        nameRow.add("Дата для белстата");
        nameRow.add("Адрес");
        nameRow.add("Телефон");
        nameRow.add("Email");
        nameRow.add("Справочно: Отдел");
        nameRow.add("Комментарий");

        String sqlRai = "SELECT DISTINCT  soato, name FROM soato WHERE soato.soato LIKE '42%000000'";

        try {
            Statement statement = ConnectDB.connection.createStatement();
            ResultSet set = statement.executeQuery(sqlReport);
            Statement statement1 = ConnectDB.connection.createStatement();
            ResultSet resultSet = statement1.executeQuery(sqlRai);
            list = new ArrayList<>();
            while (resultSet.next()){
                Rai rai1 = new Rai();
                rai1.setSoato(resultSet.getString(1));
                rai1.setName(resultSet.getString(2));
                list.add(rai1);
            }
            list.add(new Rai("4401365000", "Гродно"));
            listReport = FXCollections.observableArrayList();
            while (set.next()){
                System.out.println(set.getString(2));
                String s = set.getString(2).substring(1,4);

                System.out.println(s);
                GetReportData data =new GetReportData();
                data.setOkpo(set.getString(1));
                for (int i =0;i<list.size();i++){
                    if (list.get(i).getSoato().substring(1,4).equals(s)) {
                        if (list.get(i).getSoato().substring(1, 4).equals("401")) {
                            data.setRai("Гродно");
                        }
                        data.setRai(list.get(i).getName());
                    }
                }
                data.setSoato(set.getString(2));
                data.setNalog(set.getString(3));
                data.setName(set.getString(4));
                data.setForm(set.getString(5));
                data.setPeriod(set.getString(6).trim() + " " + set.getString(7).trim());
                data.setVid_disturb(set.getString(8));
                data.setFactDatePredst(set.getDate(9));
                data.setDateForResp(set.getDate(10));
                data.setDateForGSU(set.getString(11));
                data.setDateForBelstat(set.getString(12));
                data.setFactAdr(set.getString(13) + " " + set.getString(14) + " " + set.getString(15));
                data.setPhone(set.getString(16));
                data.setEmail(set.getString(17));
                data.setOtdel(set.getString(19));
                data.setComment(set.getString(18));
                listReport.add(data);
            }
            tableReport.setItems(listReport);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = ConnectDB.connection.createStatement();
            statement.execute(sqlReport);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setToCombo(){
        try {
            Statement statment = ConnectDB.connection.createStatement();
            String sql = "SELECT Forms.IDForm, Forms.NameForm FROM Forms";
            ResultSet set = statment.executeQuery(sql);
            while (set.next()){
                String idForm = set.getString(1);
                String nameForm = set.getString(2);
                map.put(idForm, nameForm);
                comboForms.getItems().add(nameForm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getSelectValueComboForm() {
        try{
            String formName = comboForms.getSelectionModel().getSelectedItem().toString();
            for (int i = 0; i < map.size(); i++) {
            }
            for (Map.Entry map1 : map.entrySet()) {
                if (map1.getValue().equals(formName.toString())) {
                    return (String) map1.getValue();
                }
            }

        }catch (NullPointerException e){
        //    ErrorMetods.showDialog("Внимание", "Выберите форму");
        }
        return null;

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getReport();
        setToCombo();
        soato.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("soato"));
        rai.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("rai"));
        okpo.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("okpo"));
        nalog.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("nalog"));
        name.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("name"));
        form.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("form"));
        period.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("period"));
        vid_disturb.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("vid_disturb"));
        FactDatePredst.setCellValueFactory(new PropertyValueFactory<GetReportData, Date>("FactDatePredst"));
        DateForResp.setCellValueFactory(new PropertyValueFactory<GetReportData, Date>("DateForResp"));
        DateForGSU.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("DateForGSU"));
        DateForBelstat.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("DateForBelstat"));
        Comment.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("Comment"));
        factAdr.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("factAdr"));
        phone.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("email"));
        otdel.setCellValueFactory(new PropertyValueFactory<GetReportData, String>("otdel"));
        FilteredList<GetReportData>datas = new FilteredList<>(listReport, p->true);
        getFiltredData.setOnAction(event-> {
            getReport();
        });
    }
    @FXML
    public void getPathForSave() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Xls files", "*.xls");
        fileChooser.getExtensionFilters().add(filter);
        selectDir = fileChooser.showSaveDialog(Main.loginForm.getScene().getWindow());

        String file = selectDir.getAbsolutePath();
        if (file !=null) {
            selectDir.setWritable(true);
            writeIntoExcel(file);
        }

    }

    public void writeIntoExcel(String file) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Нарушители");
        Row row = sheet.createRow(0);
        for (int i = 0;i<nameRow.size();i++){
            row.createCell(i).setCellValue(nameRow.get(i));
            sheet.autoSizeColumn(i);
        }
        for (int i=0;i<listReport.size();i++){
            Row  row1 = sheet.createRow(i+1);
            for (int j = 0;j<nameRow.size();j++){
                if (j==0) {
                    row1.createCell(j).setCellValue(listReport.get(i).getOkpo());
                }else if (j==1){
                    row1.createCell(j).setCellValue(listReport.get(i).getSoato());
                }else if (j==2){
                    row1.createCell(j).setCellValue(listReport.get(i).getRai());
                }else if (j==3){
                    row1.createCell(j).setCellValue(listReport.get(i).getNalog());
                }else if (j==4){
                    row1.createCell(j).setCellValue(listReport.get(i).getName());
                }else if (j==5){
                    row1.createCell(j).setCellValue(listReport.get(i).getForm());
                }else if (j==6){
                    row1.createCell(j).setCellValue(listReport.get(i).getPeriod());
                }else if (j==7){
                    row1.createCell(j).setCellValue(listReport.get(i).getVid_disturb());
                }else if (j==8){
                    String s = String.valueOf(listReport.get(i).getFactDatePredst());
                    row1.createCell(j).setCellValue(s);
                }else if (j==9){
                    String s = String.valueOf(listReport.get(i).getDateForResp());
                    row1.createCell(j).setCellValue(s);
                }else if (j==10){
                    row1.createCell(j).setCellValue(listReport.get(i).getDateForGSU());
                }else if (j==11){
                    row1.createCell(j).setCellValue(listReport.get(i).getDateForBelstat());
                }else if (j==12){
                    row1.createCell(j).setCellValue(listReport.get(i).getFactAdr());
                }else if (j==13){
                    row1.createCell(j).setCellValue(listReport.get(i).getPhone());
                }else if (j==14){
                    row1.createCell(j).setCellValue(listReport.get(i).getEmail());
                }else if (j==15){
                    row1.createCell(j).setCellValue(listReport.get(i).getOtdel());
                }else if (j==16){
                    row1.createCell(j).setCellValue(listReport.get(i).getComment());
                }
                sheet.autoSizeColumn(j);
            }
        }
        FileOutputStream stream = new FileOutputStream(file);
        workbook.write(stream);
        stream.close();
        workbook.close();
    }
}
