package com.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name="purchase_dtl_tab")
public class PurchaseDtls {
	
	@Id
	@GeneratedValue
	@Column(name="pur_dtl_id_col")
	private Integer id;
	
	@Transient
	private Integer slNo;
	
	@Column(name="pur_dtl_qty_col")
	private Integer qty;
	
	@ManyToOne
	@JoinColumn(name="part_id_fk")
	private Part part; // HAS-A Variable
	
	@ManyToOne
	@JoinColumn(name="po_id_fk")
	private PurchaseOrder purchaseOrder; //HAS-A Variable

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSlNo() {
		return slNo;
	}

	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	
	

}
