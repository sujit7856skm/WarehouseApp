package com.data.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.data.model.Uom;

public class UomExcelView extends AbstractXlsxView{
	
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model, 
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		// setting file name
		response.addHeader("Content-Disposition", "attachment;filename=Uom.xlsx");
		
		// Reading Data from Controller
		@SuppressWarnings("unchecked")
		List<Uom> uomList = (List<Uom>) model.get("uomList");
		Sheet sheet = workbook.createSheet("UOM");
		
		setHeading(sheet);
		setBody(uomList, sheet);
	}

	// Creating Row#0(Headings)
	private void setHeading(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Id");
		row.createCell(1).setCellValue("Type");
		row.createCell(2).setCellValue("Model");
		row.createCell(3).setCellValue("Description");
	}
	// Setting Value into the Cell 
	private void setBody(List<Uom> uomList, Sheet sheet) {
		int rowNum = 1;
		for(Uom uom:uomList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(uom.getId());
			row.createCell(1).setCellValue(uom.getUomType());
			row.createCell(2).setCellValue(uom.getUomModel());
			row.createCell(3).setCellValue(uom.getDescription());
		}
	}
	
	
}
