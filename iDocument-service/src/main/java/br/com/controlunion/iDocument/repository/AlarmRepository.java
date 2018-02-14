package br.com.controlunion.iDocument.repository;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.controlunion.iDocument.model.Alarm;

@Named
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

    Alarm findByDocumentId(int documentId);
}
