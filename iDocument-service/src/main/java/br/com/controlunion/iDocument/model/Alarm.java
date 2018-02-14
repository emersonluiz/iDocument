package br.com.controlunion.iDocument.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alarm")
public class Alarm {

    @Id
    @GeneratedValue
    private int id;

    @Column(name="alarm_date")
    private Date alarmDate;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

}
