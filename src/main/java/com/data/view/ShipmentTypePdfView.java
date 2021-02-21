package com.data.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.data.model.ShipmentType;
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

public class ShipmentTypePdfView extends AbstractPdfView{
	
	@Override
	protected void buildPdfMetadata(
			Map<String, Object> model,
			Document document,
			HttpServletRequest request
			)
	{
		Font hFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.DARK_GRAY);
		HeaderFooter header = new HeaderFooter(new Phrase("SHIPMENT TYPE", hFont), false);
		header.setAlignment(1);
		header.setBorder(Rectangle.BOTTOM);
		HeaderFooter footer = new HeaderFooter(new Phrase("Copyrights @ SujitK 2020 | "+ new Date().toString()+" |   Page : "), new Phrase("."));
		footer.setAlignment(2);
		footer.setBorder(Rectangle.TOP);
		
		document.setHeader(header);
		document.setFooter(footer);
		
		document.setMargins(5.0f, 5.0f, 20.0f, 15.0f);
		//document.setMargins(marginLeft, marginRight, marginTop, marginBottom)
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
		//file Download
		response.addHeader("Content-Disposition", "attachment;filename=Shipment.pdf");
		
		//Image
		Image img = Image.getInstance(new ClassPathResource("/static/image/logo.png").getURL());
		img.setAlignment(Image.ALIGN_CENTER);
		//img.setAbsolutePosition(470.0f, 40.0f);
		img.scaleAbsolute(120.0f, 50.0f);
		document.add(img);
		
		//paragraph 
		Font font = new Font(Font.HELVETICA, 18, Font.BOLDITALIC, new Color(2, 25, 74));
		Paragraph p = new Paragraph("Shipment Type Data",font);
		p.setSpacingBefore(0.0f);
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
		
		PdfPTable table = new PdfPTable(6);
		table.setSpacingBefore(10.5f);
		table.setTotalWidth(new float[] {1.0f, 1.0f, 2.5f, 1.0f, 1.0f, 3.5f});
		Font fthead = new Font(Font.HELVETICA, 12, Font.BOLD, new Color(122, 10, 4));
		table.addCell(new Phrase("Id",fthead));
		table.addCell(new Phrase("Mode",fthead));
		table.addCell(new Phrase("Code",fthead));
		table.addCell(new Phrase("Enable",fthead));
		table.addCell(new Phrase("Grade",fthead));
		table.addCell(new Phrase("Description",fthead));
		
		@SuppressWarnings("unchecked")
		List<ShipmentType> shipmentTypeList = (List<ShipmentType>) model.get("shipmentTypeList");
		for(ShipmentType shipmentType:shipmentTypeList) {
			table.addCell(shipmentType.getId().toString());
			table.addCell(shipmentType.getShipmentMode());
			table.addCell(shipmentType.getShipmentCode());
			table.addCell(shipmentType.getEnableShipment());
			table.addCell(shipmentType.getShipmentGrade());
			table.addCell(shipmentType.getDescription());
		}
		
		document.add(table);
	}
}
