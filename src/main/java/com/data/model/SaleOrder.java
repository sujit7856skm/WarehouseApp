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
@Table(name="sale_order_tab")
public class SaleOrder {
	
	@Id
	@GeneratedValue(generator="sale_order_seq")
	@SequenceGenerator(name="sale_order_seq", sequenceName="sale_order_seq")
	@Column(name="order_id_col")
	private Integer id;
	
	@Column(name="order_code_col")
	private String orderCode;
	
	@Column(name="ref_Num_col")
	private String refNum;
	
	@Column(name="stock_mode_col")
	private String stockMode;
	
	@Column(name="stock_source_col")
	private String stockSource;
	
	@Column(name="order_status_col")
	private String status;
	
	@Column(name="order_desc_col")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="ship_id_col_fk")
	private ShipmentType shipmentType; //HAS-A Variable
	
	@ManyToOne
	@JoinColumn(name="wh_user_id_fk")
	private WhUserType whUserType; //HAS-A Variable

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

	public String getStockMode() {
		return stockMode;
	}

	public void setStockMode(String stockMode) {
		this.stockMode = stockMode;
	}

	public String getStockSource() {
		return stockSource;
	}

	public void setStockSource(String stockSource) {
		this.stockSource = stockSource;
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
