package com.data.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.data.model.PurchaseOrder;

public class PurchaseOrderExcelView extends AbstractXlsxView{
	
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		// File Name
		response.addHeader("Content-Disposition", "attachment;filename=PurchaseOrder.xlsx");
		@SuppressWarnings("unchecked")
		List<PurchaseOrder> purchaseOrderList = (List<PurchaseOrder>) model.get("purchaseOrderList");
		Sheet sheet = workbook.createSheet("PurchaseOrder");
		setHeading(sheet);
		setBody(purchaseOrderList, sheet);
	}


	private void setHeading(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Id");
		row.createCell(1).setCellValue("Code");
		row.createCell(2).setCellValue("Shipment Code");
		row.createCell(2).setCellValue("Vendor");
		row.createCell(3).setCellValue("Reference Number");
		row.createCell(4).setCellValue("Quality Check");
		row.createCell(4).setCellValue("Status");
		row.createCell(5).setCellValue("Description");
		
	}
	private void setBody(List<PurchaseOrder> purchaseOrderList, Sheet sheet) {
		int rowNum = 1;
		for(PurchaseOrder purchaseOrder:purchaseOrderList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(purchaseOrder.getId());
			row.createCell(1).setCellValue(purchaseOrder.getOrderCode());
			row.createCell(2).setCellValue(purchaseOrder.getShipmentType().getShipmentCode());
			row.createCell(2).setCellValue(purchaseOrder.getWhUserType().getUserCode());
			row.createCell(3).setCellValue(purchaseOrder.getRefNum());
			row.createCell(4).setCellValue(purchaseOrder.getQualityCheck());
			row.createCell(4).setCellValue(purchaseOrder.getStatus());
			row.createCell(5).setCellValue(purchaseOrder.getDescription());
			
		}
		
	}
	
	
	
}
