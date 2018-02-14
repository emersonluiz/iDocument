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
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "person_id")
    private int personId;

    @ManyToOne
    @JoinColumn(name = "type_document_id")
    private TypeDocument typeDocument;

    @Column(name="expiration_date")
    private Date expirationDate;

    @Column(name="issue_date")
    private Date issueDate;

    private Integer validity;

    @Column(name="file_name")
    private String fileName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public TypeDocument getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
