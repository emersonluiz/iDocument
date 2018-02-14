package br.com.controlunion.iDocument.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.controlunion.iDocument.model.TypeDocument;
import br.com.controlunion.iDocument.service.TypeDocumentService;

@RestController
@RequestMapping("/type-documents")
public class TypeDocumentResource extends ExceptionResource {

    @Inject
    TypeDocumentService typeDocumentService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
    public List<TypeDocument> findAll() throws Exception {
        return typeDocumentService.findAll();
    }
}
