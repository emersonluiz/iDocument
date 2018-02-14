package br.com.controlunion.iDocument.rest;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.controlunion.iDocument.model.Document;
import br.com.controlunion.iDocument.service.DocumentService;

@RestController
@RequestMapping("/documents")
public class DocumentResource extends ExceptionResource {

    @Inject
    DocumentService documentService;

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = { "multipart/form-data" }, produces = { "application/json" })
    public Document create(
            @RequestPart(value = "data", required = false) String data,
            @RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
        Document document = documentService.create(data, file);
        return document;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/{documentId}", method = RequestMethod.GET, produces = { "application/json" })
    public Document findOne(@PathVariable("documentId") int documentId) throws Exception {
        return documentService.findOne(documentId);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/{documentId}/file", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<InputStreamResource> getFile(@PathVariable("documentId") int documentId) throws Exception {
        Document document = findOne(documentId);

        InputStream is = documentService.getFile(document);
        InputStreamResource isr = new InputStreamResource(is);

        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set("Content-Disposition", "attachment; filename="+document.getFileName());
        return (new ResponseEntity<InputStreamResource>(isr, headers, HttpStatus.OK));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value="/{documentId}", method = RequestMethod.PUT)
    public void update(
            @PathVariable("documentId") int documentId,
            @RequestPart(value = "data", required = false) String data,
            @RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
        documentService.update(documentId, data, file);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value="/{documentId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("documentId") int documentId) throws Exception {
        documentService.delete(documentId);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/person/{personId}", method = RequestMethod.GET, produces = { "application/json" })
    public List<Document> findByPerson(@PathVariable("personId") int personId) throws Exception {
        return documentService.findByPerson(personId);
    }
}
