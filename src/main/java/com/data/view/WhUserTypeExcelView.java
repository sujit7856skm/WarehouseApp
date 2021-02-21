package com.data.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.data.model.WhUserType;

public class WhUserTypeExcelView extends AbstractXlsxView{
	
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		// File Name
		response.addHeader("Content-Disposition", "attachment;filename=WhUserType.xlsx");
		@SuppressWarnings("unchecked")
		List<WhUserType> whUserTypeList = (List<WhUserType>) model.get("whUserTypeList");
		Sheet sheet = workbook.createSheet("WhUserType");
		setHeading(sheet);
		setBody(whUserTypeList, sheet);
	}


	private void setHeading(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Id");
		row.createCell(1).setCellValue("Type");
		row.createCell(2).setCellValue("Code");
		row.createCell(3).setCellValue("For");
		row.createCell(4).setCellValue("Email");
		row.createCell(5).setCellValue("Contact");
		row.createCell(5).setCellValue("Id Type");
		row.createCell(5).setCellValue("If Other");
		row.createCell(5).setCellValue("Id Number");
		
	}
	private void setBody(List<WhUserType> whUserTypeList, Sheet sheet) {
		int rowNum = 1;
		for(WhUserType whUserType:whUserTypeList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(whUserType.getId());
			row.createCell(1).setCellValue(whUserType.getUserType());
			row.createCell(2).setCellValue(whUserType.getUserCode());
			row.createCell(3).setCellValue(whUserType.getUserFor());
			row.createCell(4).setCellValue(whUserType.getUserEmail().toString());
			row.createCell(5).setCellValue(whUserType.getUserContact());
			row.createCell(5).setCellValue(whUserType.getUserIdType());
			row.createCell(5).setCellValue(whUserType.getIfOther());			
			row.createCell(5).setCellValue(whUserType.getIdNumber());			
		}
		
	}
	
	
	
}
