package br.com.controlunion.iDocument.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.controlunion.iDocument.model.TypeDocument;
import br.com.controlunion.iDocument.repository.TypeDocumentRepository;

@Named
public class DefaultTypeDocumentService implements TypeDocumentService {

    @Inject
    TypeDocumentRepository typeDocumentRepository;

    @Override
    public List<TypeDocument> findAll() {
        return typeDocumentRepository.findAll();
    }

}
