package com.todo.classes;

import com.todo.controllers.LoadData;
import com.todo.controllers.LoginController;
import com.todo.pojo.DataXlsWithDetails;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by I see you on 08.10.2016.
 */
public class XlsFile {

    public static String dbfFile = LoadData.files;
    public static ArrayList<DataXlsWithDetails> list = new ArrayList<DataXlsWithDetails>();
    public static void getData() throws IOException, BiffException {
        int cout=0;
//╨┐╤Ç╨╛╨▓╨╡╤Ç╨║╨░ ╨╡╤ü╤é╤î ╨╗╨╕ ╨▓ ╤ä╨░╨╣╨╗╨╡ ╨┐╨╛╨╗╨╡ ╨┤╨╡╤é╨░╨╗╨╕╨╖╨░╤å╨╕╤Å

            Workbook workbook = Workbook.getWorkbook(new File(dbfFile));
            Sheet sheet = workbook.getSheet(0);
            if (sheet.getCell(1,0).getContents().contains("Детализация")){
                for (int q = 1; q < sheet.getRows(); q++){
                    DataXlsWithDetails dataXlsWithDetails = new DataXlsWithDetails();
                    for (Cell cell : sheet.getRow(q)) {
                        if (cell.getColumn() == 0) {
                            dataXlsWithDetails.setKodEsn(cell.getContents());
                        } else if (cell.getColumn() == 2) {
                            String str = cell.getContents();
                            String parse = str.substring(0, 7);
                            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
                            try {
                                Date date = format.parse(parse);
                                java.sql.Date date1 =new java.sql.Date(date.getTime());
                                dataXlsWithDetails.setDateModify(date1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if (cell.getColumn() == 3) {
                            dataXlsWithDetails.setVidPredst(cell.getContents());
                        } else if (cell.getColumn() == 4) {
                            String str = cell.getContents();
                            String parse = str.substring(0, 7);
                            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
                            try {
                                Date date = format.parse(parse);
                                java.sql.Date date1 =new java.sql.Date(date.getTime());
                                dataXlsWithDetails.setDateFirstPreds(date1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                         //   dataXlsWithDetails.setDateFirstPreds(str.substring(0, 7));
                        } else if (cell.getColumn() == 5) {
                            dataXlsWithDetails.setLastVidPredst(cell.getContents());
                        } else if (cell.getColumn() == 6) {
                            dataXlsWithDetails.setNumdofCorrect(cell.getContents());
                        } else if (cell.getColumn() == 7) {
                            dataXlsWithDetails.setFirstVidPredst(cell.getContents());
                        } else if (cell.getColumn() == 8) {
                            dataXlsWithDetails.setNumbPac(cell.getContents());
                        } else if (cell.getColumn() == 9) {
                            dataXlsWithDetails.setStatOrg(cell.getContents());
                        } else if (cell.getColumn() == 10) {
                            dataXlsWithDetails.setContact(cell.getContents());
                        } else if (cell.getColumn() == 11) {
                            dataXlsWithDetails.setTelNumber(cell.getContents());
                        } else if (cell.getColumn() == 12) {
                            dataXlsWithDetails.setEmail(cell.getContents());
                        }
                        dataXlsWithDetails.setId(cout++);
                    }
                    list.add(dataXlsWithDetails);
                }
            }else {
                for (int q = 1; q < sheet.getRows(); q++){
                    DataXlsWithDetails dataXlsWithDetails = new DataXlsWithDetails();
                    for (Cell cell : sheet.getRow(q)) {
                        if (cell.getColumn() == 0) {
                            dataXlsWithDetails.setKodEsn(cell.getContents());
                        } else if (cell.getColumn() == 1) {
                            String str = cell.getContents();
                            String parse = str.substring(0, 7);
                            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
                            try {
                                Date date = format.parse(parse);
                                java.sql.Date date1 =new java.sql.Date(date.getTime());
                                dataXlsWithDetails.setDateModify(date1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if (cell.getColumn() == 2) {
                            dataXlsWithDetails.setVidPredst(cell.getContents());
                        } else if (cell.getColumn() == 3) {
                            String str = cell.getContents();
                            String parse = str.substring(0, 7);
                            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
                            try {
                                Date date = format.parse(parse);
                                java.sql.Date date1 =new java.sql.Date(date.getTime());
                                dataXlsWithDetails.setDateFirstPreds(date1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                          //  dataXlsWithDetails.setDateFirstPreds(str.substring(0, 7));
                        } else if (cell.getColumn() == 4) {
                            dataXlsWithDetails.setLastVidPredst(cell.getContents());
                        } else if (cell.getColumn() == 5) {
                            dataXlsWithDetails.setNumdofCorrect(cell.getContents());
                        } else if (cell.getColumn() == 6) {
                            dataXlsWithDetails.setFirstVidPredst(cell.getContents());
                        } else if (cell.getColumn() == 7) {
                            dataXlsWithDetails.setNumbPac(cell.getContents());
                        } else if (cell.getColumn() == 8) {
                            dataXlsWithDetails.setStatOrg(cell.getContents());
                        } else if (cell.getColumn() == 9) {
                            dataXlsWithDetails.setContact(cell.getContents());
                        } else if (cell.getColumn() == 10) {
                            dataXlsWithDetails.setTelNumber(cell.getContents());
                        } else if (cell.getColumn() == 11) {
                            dataXlsWithDetails.setEmail(cell.getContents());
                        }
                        dataXlsWithDetails.setId(cout++);
                    }
                    list.add(dataXlsWithDetails);
                }
            }
        }
    }

