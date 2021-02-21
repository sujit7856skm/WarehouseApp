package com.data.service;

import java.util.List;
import java.util.Optional;

import com.data.model.Document;

public interface IDocumentService {
	public void saveDocument(Document doc);
	public List<Object> findIdAndName();
	public Optional<Document> getDocument(Integer Id);
}
