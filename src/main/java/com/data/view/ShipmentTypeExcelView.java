package com.data.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.data.model.ShipmentType;

public class ShipmentTypeExcelView extends AbstractXlsxView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// File name
		response.addHeader("Content-Disposition", "attachment;filename=ShipmentTypes.xlsx");

		// Reading Data From Controller
		@SuppressWarnings("unchecked")
		List<ShipmentType> shipmentTypesList = (List<ShipmentType>) model.get("shipmentTypesList");

		// Create New Sheet
		Sheet sheet = workbook.createSheet("SHEEPMENT TYPES");
		setHead(sheet);
		setBody(sheet, shipmentTypesList);

	}

	// Row#0 creation
	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Shipment Mode");
		row.createCell(2).setCellValue("Shipment Code");
		row.createCell(3).setCellValue("Enable Shipment");
		row.createCell(4).setCellValue("Shipment Grade");
		row.createCell(5).setCellValue("Description");
	}
	
	private void setBody(Sheet sheet, List<ShipmentType> shipmentTypesList) {
		int rowNum = 1;
		for(ShipmentType shipmentType:shipmentTypesList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(shipmentType.getId());
			row.createCell(1).setCellValue(shipmentType.getShipmentMode());
			row.createCell(2).setCellValue(shipmentType.getShipmentCode());
			row.createCell(3).setCellValue(shipmentType.getEnableShipment());
			row.createCell(4).setCellValue(shipmentType.getShipmentGrade());
			row.createCell(5).setCellValue(shipmentType.getDescription());
		}
	}
}
