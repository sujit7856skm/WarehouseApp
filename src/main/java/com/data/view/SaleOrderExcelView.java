package com.data.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.data.model.SaleOrder;

public class SaleOrderExcelView extends AbstractXlsxView{
	
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		// File Name
		response.addHeader("Content-Disposition", "attachment;filename=SaleOrder.xlsx");
		@SuppressWarnings("unchecked")
		List<SaleOrder> saleOrderList = (List<SaleOrder>) model.get("saleOrderList");
		Sheet sheet = workbook.createSheet("saleOrder");
		setHeading(sheet);
		setBody(saleOrderList, sheet);
	}


	private void setHeading(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Id");
		row.createCell(1).setCellValue("Code");
		row.createCell(2).setCellValue("Shipment Code");
		row.createCell(2).setCellValue("Customer");
		row.createCell(3).setCellValue("Reference Number");
		row.createCell(4).setCellValue("Stock Mode");
		row.createCell(4).setCellValue("Stock Source");
		row.createCell(4).setCellValue("Status");
		row.createCell(5).setCellValue("Description");
		
	}
	private void setBody(List<SaleOrder> saleOrderList, Sheet sheet) {
		int rowNum = 1;
		for(SaleOrder saleOrder:saleOrderList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(saleOrder.getId());
			row.createCell(1).setCellValue(saleOrder.getOrderCode());
			row.createCell(2).setCellValue(saleOrder.getShipmentType().getShipmentCode());
			row.createCell(2).setCellValue(saleOrder.getWhUserType().getUserCode());
			row.createCell(3).setCellValue(saleOrder.getRefNum());
			row.createCell(4).setCellValue(saleOrder.getStockMode());
			row.createCell(4).setCellValue(saleOrder.getStockSource());
			row.createCell(4).setCellValue(saleOrder.getStatus());
			row.createCell(5).setCellValue(saleOrder.getDescription());
			
		}
		
	}
	
	
	
}
