package com.data.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.data.model.Document;
import com.data.service.IDocumentService;

@Controller
@RequestMapping("document")
public class DocumentController {

	@Autowired
	private IDocumentService docService; 
	
	// Show Doc Page
	@GetMapping("/show")
	public String showDocPage(Model model) {
		model.addAttribute("docList", docService.findIdAndName());
		return "Document";
	}
	// save doc
	@PostMapping("/save")
	public String saveDoc(
			@RequestParam Integer docId,
			@RequestParam MultipartFile doc,
			Model model
			)
	{
		if(doc !=null && docId != null) {
			Document document = new Document();
			document.setDocId(docId);
			document.setDocName(doc.getOriginalFilename());
			try {
				document.setDocData(doc.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			docService.saveDocument(document);
			model.addAttribute("message", "Document Uploaded Sucessfully");
			model.addAttribute("docList", docService.findIdAndName());
		}
		return "Document";
	}
	
	// Download Document
	@GetMapping("/download/{id}")
	public void downloadDoc(
				@PathVariable Integer id,
				HttpServletResponse resp
			) 
	{
		Optional<Document> optDoc = docService.getDocument(id);
		if(optDoc.isPresent()) {
			Document doc = optDoc.get();
			
			// This Header parameter says that, Download the Content if we comment this then the doc will be displayed only and
			//again we have to save the doc manually
			resp.addHeader("Content-Disposition", "attachment;filename="+doc.getDocName());
			try {
				FileCopyUtils.copy(doc.getDocData(), resp.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
