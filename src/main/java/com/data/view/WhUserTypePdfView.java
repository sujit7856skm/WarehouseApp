package com.data.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.data.model.WhUserType;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class WhUserTypePdfView extends AbstractPdfView{
	
	@Override
	protected void buildPdfMetadata(
			Map<String, Object> model,
			Document document,
			HttpServletRequest request
			) 
	{
		document.setMargins(5.0f, 5.0f, 20.0f, 15.0f);
		Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
		HeaderFooter header = new HeaderFooter(new Phrase("WH USER TYPE",headerFont), false);
		header.setAlignment(1);
		header.setBorder(Rectangle.BOTTOM);
		Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC);
		HeaderFooter footer = new HeaderFooter(new Phrase("Copyright @ SujitK 2020  |  "+new Date().toString()+"  |  Page : ",footerFont), new Phrase("."));
		footer.setAlignment(2);
		footer.setBorder(Rectangle.TOP);
		
		document.setHeader(header);
		document.setFooter(footer);
 	}
	
	@Override
	protected void buildPdfDocument(
			Map<String, Object> model,
			Document document, 
			PdfWriter writer,
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception
	{
		//Download Details
		response.addHeader("Content-Disposition", "attachment;filename=WhUserType.pdf");
		
		//logo
		Image img = Image.getInstance(new ClassPathResource("/static/image/logo.png").getURL());
		img.setAlignment(1);
		img.scaleAbsolute(150.0f, 80.0f);
		document.add(img);
		
		//PDF Details
			// Paragraph Details
		Font font = new Font(Font.HELVETICA, 18, Font.BOLDITALIC, Color.BLUE);
		Paragraph p = new Paragraph("Wh User Type Data Table", font);
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
		
			//Table Details
		PdfPTable table = new PdfPTable(9);
		table.setSpacingBefore(10.0f);
		table.setTotalWidth(new float[] {0.5f, 1.0f, 1.0f, 1.0f, 2.5f, 1.0f, 1.0f, 1.0f, 1.0f});
		table.addCell("Id");
		table.addCell("Type");
		table.addCell("Code");
		table.addCell("For");
		table.addCell("Email");
		table.addCell("Contact");
		table.addCell("Id Type");
		table.addCell("Other Type");
		table.addCell("Id Number");
		
		
		//getting data
		@SuppressWarnings("unchecked")
		List<WhUserType> userList = (List<WhUserType>) model.get("userList");
		for(WhUserType user:userList) {
			table.addCell(user.getId().toString());
			table.addCell(user.getUserType());
			table.addCell(user.getUserCode());
			table.addCell(user.getUserFor());
			table.addCell(user.getUserEmail());
			table.addCell(user.getUserContact());
			table.addCell(user.getUserIdType());
			table.addCell(user.getIfOther());
			table.addCell(user.getIdNumber());
		}
		document.add(table);
	}
}
