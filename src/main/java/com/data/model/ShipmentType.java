package com.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="shipment_type_tab")
public class ShipmentType {
	@Id
	@GeneratedValue(generator = "shipment_type_seq_name")
	@SequenceGenerator(name = "shipment_type_seq_name", sequenceName="shipment_type_seq")
	@Column(name = "ship_id_col")
	private Integer id;
	
	@Column(name = "shipment_mode_col", length= 10, nullable=false)
	private String shipmentMode;
	@Column(name = "shipment_code_col", length= 20, nullable=false)
	private String shipmentCode;
	@Column(name = "enable_shipment_col", length= 3, nullable=false)
	private String enableShipment;
	@Column(name = "shipment_grade_col", length= 1, nullable=false)
	private String shipmentGrade;
	@Column(name = "description_col", length= 200, nullable=false)
	private String description;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getShipmentMode() {
		return shipmentMode;
	}
	public void setShipmentMode(String shipmentMode) {
		this.shipmentMode = shipmentMode;
	}
	public String getShipmentCode() {
		return shipmentCode;
	}
	public void setShipmentCode(String shipmentCode) {
		this.shipmentCode = shipmentCode;
	}
	public String getEnableShipment() {
		return enableShipment;
	}
	public void setEnableShipment(String enableShipment) {
		this.enableShipment = enableShipment;
	}
	public String getShipmentGrade() {
		return shipmentGrade;
	}
	public void setShipmentGrade(String shipmentGrade) {
		this.shipmentGrade = shipmentGrade;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
