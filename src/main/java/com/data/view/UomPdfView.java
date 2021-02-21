package com.data.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.data.model.Uom;
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

public class UomPdfView extends AbstractPdfView{
	
	@Override
	protected void buildPdfMetadata(
			Map<String, Object> model,
			Document document,
			HttpServletRequest request
			) 
	{
		document.setMargins(5.0f, 5.0f, 20.0f, 15.0f);
		
		Font headerFont = new Font(Font.HELVETICA, 12);
		HeaderFooter header = new HeaderFooter(new Phrase("UOM",headerFont),false);
		header.setAlignment(1);
		header.setBorder(Rectangle.BOTTOM);
		Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC);
		HeaderFooter footer = new HeaderFooter(new Phrase("Copyright @ SujitK 2020  |  "+ new Date().toString()+"  |  Page : ", footerFont), false);
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
			)throws Exception 
	{
		//Download Details
		response.addHeader("Content-Disposition", "attachment;filename=Uom.pdf");
		
		Image img = Image.getInstance(new ClassPathResource("/static/image/logo.png").getURL());
		img.scaleAbsolute(150.0f, 80.0f);
		img.setAlignment(1);
		document.add(img);
		
		//PDF Details
		  //paragraph details
		Font font = new Font(Font.HELVETICA, 18, Font.BOLDITALIC, new Color(2, 25, 74));
		Paragraph p = new Paragraph("UOM Data Table", font);
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
		
			//Table Details
		Font tHeadFont = new Font(Font.HELVETICA, 12, Font.BOLDITALIC, new Color(122, 10, 4));
		PdfPTable table = new PdfPTable(4);
		table.setSpacingBefore(10.0f);
		table.setTotalWidth(new float[] {1.0f, 2.5f, 2.0f, 3.0f});
		
		table.addCell(new Phrase("Id", tHeadFont));
		table.addCell(new Phrase("Type", tHeadFont));
		table.addCell(new Phrase("Model", tHeadFont));
		table.addCell(new Phrase("Description", tHeadFont));		
		//getting data
		@SuppressWarnings("unchecked")
		List<Uom> uomList = (List<Uom>) model.get("uomList");
		
		for(Uom uom:uomList) {
			table.addCell(uom.getId().toString());
			table.addCell(uom.getUomModel());
			table.addCell(uom.getUomType());
			table.addCell(uom.getDescription());
		}
		document.add(table);		
}
}