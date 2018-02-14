package br.com.controlunion.iDocument.service;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.web.multipart.MultipartFile;

import br.com.controlunion.iDocument.exception.BadRequestException;
import br.com.controlunion.iDocument.exception.NotFoundException;
import br.com.controlunion.iDocument.model.Alarm;
import br.com.controlunion.iDocument.model.Document;
import br.com.controlunion.iDocument.model.TypeDocument;
import br.com.controlunion.iDocument.repository.AlarmRepository;
import br.com.controlunion.iDocument.repository.DocumentRepository;
import br.com.controlunion.iDocument.repository.TypeDocumentRepository;
import br.com.controlunion.iDocument.util.DocumentStore;
import br.com.controlunion.iDocument.util.JsonUtil;

@Named
public class DefaultDocumentService implements DocumentService {

    @Inject
    DocumentRepository documentRepository;

    @Inject
    TypeDocumentRepository typeDocumentRepository;

    @Inject
    AlarmRepository alarmRepository;

    @Inject
    DocumentStore documentStore;

    @Override
    public Document create(String data, MultipartFile file) throws Exception {

        if(data == null && file == null) {
            throw new BadRequestException("Document or File is required!");
        }

        Document document = JsonUtil.jsonToString(data);
        if(document.getTypeDocument() == null) {
            throw new BadRequestException("TypeDocument is not valid!");
        }

        String fileName = verifyFile(file, document);

        documentRepository.save(document);

        if(file != null) {
            documentStore.upload(String.valueOf(document.getId()), file.getInputStream(), fileName);
        }
        return document;
    }

    @Override
    public Document findOne(int documentId) throws Exception {
        Document document = documentRepository.findOne(documentId);
        if(document== null) {
            throw new NotFoundException("Document was not found!");
        }
        return document;
    }

    public InputStream getFile(Document document) throws Exception {
        return documentStore.download(String.valueOf(document.getId()), document.getFileName()); 
    }

    @Override
    public void update(int documentId, String data, MultipartFile file) throws Exception {

        if(data == null && file == null) {
            throw new BadRequestException("Document or File is required!");
        }

        Document document = JsonUtil.jsonToString(data);
        findOne(documentId);
        document.setId(documentId);

        if(document.getTypeDocument() == null) {
            throw new BadRequestException("TypeDocument is not valid!");
        }

        String fileName = verifyFile(file, document);

        documentRepository.save(document);

        if(file != null) {
            documentStore.upload(String.valueOf(document.getId()), file.getInputStream(), fileName);
        }
    }

    @Override
    public void delete(int documentId) throws Exception {
        Document document = findOne(documentId);
        Alarm alarm = alarmRepository.findByDocumentId(documentId);
        alarmRepository.delete(alarm);
        documentRepository.delete(document);
    }

    @Override
    public List<Document> findByPerson(int personId) {
        List<Document> documents = documentRepository.findByPersonId(personId);
        return documents;
    }

    private String verifyFile(MultipartFile file, Document document) throws BadRequestException {
        String fileName = "";
        if(file != null) {
            TypeDocument typeDocument = getTypeDocument(document.getTypeDocument().getId());
            String[] name = file.getOriginalFilename().split("\\.");
            fileName = (name.length > 1)?typeDocument.getName() + "." + name[1] : typeDocument.getName();
            document.setFileName(fileName);
        }
        return fileName;
    }

    private TypeDocument getTypeDocument(int id) throws BadRequestException {
        TypeDocument typeDocument = typeDocumentRepository.findOne(id);
        if(typeDocument == null) {
            throw new BadRequestException("TypeDocument is not valid!");
        }

        return typeDocument;
    }

}
