package com.data.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.data.model.Part;

public class PartExcelView extends AbstractXlsxView{
	
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		// File Name
		response.addHeader("Content-Disposition", "attachment;filename=Part.xlsx");
		@SuppressWarnings("unchecked")
		List<Part> partList = (List<Part>) model.get("partList");
		Sheet sheet = workbook.createSheet("Part");
		setHeading(sheet);
		setBody(partList, sheet);
	}


	private void setHeading(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Id");
		row.createCell(1).setCellValue("Code");
		row.createCell(2).setCellValue("Dimension");
		row.createCell(3).setCellValue("Base Cost");
		row.createCell(4).setCellValue("Base Currency");
		row.createCell(4).setCellValue("UOM");
		row.createCell(5).setCellValue("Description");
		
	}
	private void setBody(List<Part> partList, Sheet sheet) {
		int rowNum = 1;
		for(Part part:partList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(part.getId());
			row.createCell(1).setCellValue(part.getPartCode());
			row.createCell(2).setCellValue(part.getPartWidth()+" x "+part.getPartLength()+" x "+part.getPartHeight());
			row.createCell(3).setCellValue(part.getBaseCost());
			row.createCell(4).setCellValue(part.getBaseCurrency());
			row.createCell(4).setCellValue(part.getUom().getUomModel());
			row.createCell(5).setCellValue(part.getDescription());
			
		}
		
	}
	
	
	
}
