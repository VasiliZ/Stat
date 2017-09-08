package com.todo.pojo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by I see you on 09.10.2016.
 */
@Entity
@Table(name = "DataXlsFile")
public class GetView {
    @Id
    public String id;
    @Column(name = "KodEsn")
    public String kodEsn;
    @Column(name = "dateModify")
    public Date dateModify;
    @Column(name = "VidPredst")
    public String vidPredst;
    @Column(name = "DateFirstPredst")
    public String dateFirstPreds;
    @Column(name = "LastVidPredst")
    public String lastVidPredst;
    @Column(name = "NumbOfCorrect")
    public String numdofCorrect;
    @Column(name = "FirstVidPredst")
    public String firstVidPredst;
    @Column(name = "NumbOfPacket")
    public String numbPac;
    @Column(name = "StatOrg")
    public String statOrg;
    @Column(name = "FioSend")
    public String contact;
    @Column(name = "TelNumb")
    public String telNumber;
    @Column(name = "Email")
    public String email;
    @Column(name = "timeForRespondent")
    public String timeResp;
    @Column(name = "timeForSendOnGSU")
    public String timeGSU;
    @Column(name = "timeForSendOnBelstat")
    public String timeBelstat;
    @Column(name = "Comment")
    public String comment;


    public GetView() {
    }

    public GetView(String id, String kodEsn, Date dateModify, String vidPredst, String dateFirstPreds, String lastVidPredst, String numdofCorrect, String firstVidPredst, String numbPac, String statOrg, String contact, String telNumber, String email, String timeResp, String timeGSU, String timeBelstat, String comment) {
        this.id = id;
        this.kodEsn = kodEsn;
        this.dateModify = dateModify;
        this.vidPredst = vidPredst;
        this.dateFirstPreds = dateFirstPreds;
        this.lastVidPredst = lastVidPredst;
        this.numdofCorrect = numdofCorrect;
        this.firstVidPredst = firstVidPredst;
        this.numbPac = numbPac;
        this.statOrg = statOrg;
        this.contact = contact;
        this.telNumber = telNumber;
        this.email = email;
        this.timeResp = timeResp;
        this.timeGSU = timeGSU;
        this.timeBelstat = timeBelstat;
        this.comment = comment;
    }

    public Date getDateModify() {
        return dateModify;
    }

    public void setDateModify(Date dateModify) {
        this.dateModify = dateModify;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodEsn() {
        return kodEsn;
    }

    public void setKodEsn(String kodEsn) {
        this.kodEsn = kodEsn;
    }



    public String getVidPredst() {
        return vidPredst;
    }

    public void setVidPredst(String vidPredst) {
        this.vidPredst = vidPredst;
    }

    public String getDateFirstPreds() {
        return dateFirstPreds;
    }

    public void setDateFirstPreds(String dateFirstPreds) {
        this.dateFirstPreds = dateFirstPreds;
    }

    public String getLastVidPredst() {
        return lastVidPredst;
    }

    public void setLastVidPredst(String lastVidPredst) {
        this.lastVidPredst = lastVidPredst;
    }

    public String getNumdofCorrect() {
        return numdofCorrect;
    }

    public void setNumdofCorrect(String numdofCorrect) {
        this.numdofCorrect = numdofCorrect;
    }

    public String getFirstVidPredst() {
        return firstVidPredst;
    }

    public void setFirstVidPredst(String firstVidPredst) {
        this.firstVidPredst = firstVidPredst;
    }

    public String getNumbPac() {
        return numbPac;
    }

    public void setNumbPac(String numbPac) {
        this.numbPac = numbPac;
    }

    public String getStatOrg() {
        return statOrg;
    }

    public void setStatOrg(String statOrg) {
        this.statOrg = statOrg;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimeResp() {
        return timeResp;
    }

    public void setTimeResp(String timeResp) {
        this.timeResp = timeResp;
    }

    public String getTimeGSU() {
        return timeGSU;
    }

    public void setTimeGSU(String timeGSU) {
        this.timeGSU = timeGSU;
    }

    public String getTimeBelstat() {
        return timeBelstat;
    }

    public void setTimeBelstat(String timeBelstat) {
        this.timeBelstat = timeBelstat;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
