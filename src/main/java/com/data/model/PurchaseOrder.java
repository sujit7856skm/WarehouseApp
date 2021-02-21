package com.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="purchase_order_tab")
public class PurchaseOrder {
	@Id
	@GeneratedValue(generator="purchase_order_seq")
	@SequenceGenerator(name="purchase_order_seq", sequenceName="purchase_order_seq")
	@Column(name="order_id_col")
	private Integer id;
	
	@Column(name="order_code_col")
	private String orderCode;
	
	@Column(name="order_ref_num_col")
	private String refNum;
	
	@Column(name="order_qlty_check_col")
	private String qualityCheck;
	
	@Column(name="order_status_col")
	private String status;
	
	@Column(name="order_desc_col")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="ship_id_col_fk")
	private ShipmentType shipmentType;//HAS-A variable
	
	@ManyToOne
	@JoinColumn(name="wh_user_id_fk")
	private WhUserType whUserType; //HAS-A variable

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getRefNum() {
		return refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public String getQualityCheck() {
		return qualityCheck;
	}

	public void setQualityCheck(String qualityCheck) {
		this.qualityCheck = qualityCheck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ShipmentType getShipmentType() {
		return shipmentType;
	}

	public void setShipmentType(ShipmentType shipmentType) {
		this.shipmentType = shipmentType;
	}

	public WhUserType getWhUserType() {
		return whUserType;
	}

	public void setWhUserType(WhUserType whUserType) {
		this.whUserType = whUserType;
	}
	
	
}
