package com.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
@Data
@Entity
public class SaleOrderDtl {
	
	@Id
	@GeneratedValue
	@Column(name="sale_dtl_id_col")
	private Integer id;
	
	@Column(name="sale_dtl_qty_col")
	private Integer qty;
	
	@ManyToOne
	@JoinColumn(name = "part_id_fk_col")
	private Part part;
	
	@ManyToOne
	@JoinColumn(name = "po_id_fk_col")
	private PurchaseOrder purchaseOrder;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
