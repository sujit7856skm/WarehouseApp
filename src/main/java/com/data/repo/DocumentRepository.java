package com.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.data.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
	@Query("SELECT doc.docId, doc.docName FROM Document doc")
	List<Object> findIdAndName();

}
