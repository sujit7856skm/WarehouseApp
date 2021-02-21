package com.data.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "order_method_tab")
public class OrderMethod {
	
	@Id
	@GeneratedValue(generator = "order_method_seq")
	@SequenceGenerator(name = "order_method_seq", sequenceName = "order_method_seq")
	@Column(name = "ord_id_col")
	private Integer id;
	
	@Column(name = "ord_mode_col")
	private String orderMode;
	
	@Column(name = "ord_code_col")
	private String orderCode;
	
	@Column(name = "ord_type_col")
	private String orderType;
	
	@ElementCollection
	@CollectionTable(
			name = "ord_acpt_tab",
			joinColumns = @JoinColumn(name = "ord_id_col")
		)
	private List<String> orderAccept;
	
	@Column(name = "ord_desc_col")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderMode() {
		return orderMode;
	}

	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public List<String> getOrderAccept() {
		return orderAccept;
	}

	public void setOrderAccept(List<String> orderAccept) {
		this.orderAccept = orderAccept;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
