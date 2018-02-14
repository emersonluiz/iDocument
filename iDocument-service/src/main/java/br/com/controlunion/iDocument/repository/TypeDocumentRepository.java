package br.com.controlunion.iDocument.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.controlunion.iDocument.model.TypeDocument;

public interface TypeDocumentRepository extends JpaRepository<TypeDocument, Integer> {

}
