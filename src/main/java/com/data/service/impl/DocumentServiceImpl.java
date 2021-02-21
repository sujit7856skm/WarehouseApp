package com.data.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.model.Document;
import com.data.repo.DocumentRepository;
import com.data.service.IDocumentService;

@Service
public class DocumentServiceImpl implements IDocumentService {

	@Autowired
	private DocumentRepository docRepo;
	
	@Override
	public void saveDocument(Document doc) {
		docRepo.save(doc);
	}
	@Override
	public List<Object> findIdAndName() {
		return docRepo.findIdAndName();
	}
	@Override
	public Optional<Document> getDocument(Integer id) {
		return docRepo.findById(id);
	}
}
