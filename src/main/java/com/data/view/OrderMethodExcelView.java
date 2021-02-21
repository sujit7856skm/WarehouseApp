package com.data.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.data.model.OrderMethod;

public class OrderMethodExcelView extends AbstractXlsxView{
	
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		// File Name
		response.addHeader("Content-Disposition", "attachment;filename=OrderMethod.xlsx");
		@SuppressWarnings("unchecked")
		List<OrderMethod> orderMethodList = (List<OrderMethod>) model.get("orderMethodList");
		Sheet sheet = workbook.createSheet("OrderMethod");
		setHeading(sheet);
		setBody(orderMethodList, sheet);
	}


	private void setHeading(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Id");
		row.createCell(1).setCellValue("Mode");
		row.createCell(2).setCellValue("Code");
		row.createCell(3).setCellValue("Type");
		row.createCell(4).setCellValue("Accept");
		row.createCell(5).setCellValue("Description");
		
	}
	private void setBody(List<OrderMethod> orderMethodList, Sheet sheet) {
		int rowNum = 1;
		for(OrderMethod orderMethod:orderMethodList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(orderMethod.getId());
			row.createCell(1).setCellValue(orderMethod.getOrderMode());
			row.createCell(2).setCellValue(orderMethod.getOrderCode());
			row.createCell(3).setCellValue(orderMethod.getOrderType());
			row.createCell(4).setCellValue(orderMethod.getOrderAccept().toString());
			row.createCell(5).setCellValue(orderMethod.getDescription());
			
		}
		
	}
	
	
	
}
