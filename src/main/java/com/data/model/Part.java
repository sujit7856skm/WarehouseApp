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
@Table(name="part_tab")
public class Part {
	
	@Id
	@GeneratedValue(generator="part_seq")
	@SequenceGenerator(name="part_seq", sequenceName="part_seq")
	@Column(name="part_id_col")
	private Integer id;
	
	@Column(name="part_code_col")
	private String partCode;
	
	@Column(name="part_width_col")
	private Double partWidth;
	
	@Column(name="part_len_col")
	private Double partLength;
	
	@Column(name="part_heig_col")
	private Double partHeight;
	
	@Column(name="part_base_cost_col")
	private Double baseCost;
	
	@Column(name="part_base_currency_col")
	private String baseCurrency;
	
	@Column(name="part_desc_col")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "uom_id_col_fk")
	private Uom uom; // HAS-A variable

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public Double getPartWidth() {
		return partWidth;
	}

	public void setPartWidth(Double partWidth) {
		this.partWidth = partWidth;
	}

	public Double getPartLength() {
		return partLength;
	}

	public void setPartLength(Double partLength) {
		this.partLength = partLength;
	}

	public Double getPartHeight() {
		return partHeight;
	}

	public void setPartHeight(Double partHeight) {
		this.partHeight = partHeight;
	}

	public Double getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(Double baseCost) {
		this.baseCost = baseCost;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Uom getUom() {
		return uom;
	}

	public void setUom(Uom uom) {
		this.uom = uom;
	}
	
	
}
