package br.com.controlunion.iDocument.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.com.controlunion.iDocument.model.Document;

public interface DocumentService {

    Document create(String metadata, MultipartFile file) throws Exception;

    Document findOne(int documentId) throws Exception;

    InputStream getFile(Document document) throws Exception;

    void update(int documentId, String data, MultipartFile file) throws Exception;

    void delete(int documentId) throws Exception;

    List<Document> findByPerson(int personId);
}
