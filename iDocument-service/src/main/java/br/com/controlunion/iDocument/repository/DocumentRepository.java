package br.com.controlunion.iDocument.repository;

import java.util.List;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.controlunion.iDocument.model.Document;

@Named
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    @Query("SELECT d FROM Document d WHERE d.personId = ?1 ORDER BY d.id DESC")
    List<Document> findByPersonId(int personId);

    @Query("SELECT distinct d FROM Document d WHERE d.id NOT IN (SELECT d.id FROM Alarm a JOIN a.document d)")
    List<Document> findValidity();
}