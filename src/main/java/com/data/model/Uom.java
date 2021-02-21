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
@Table(name = "uom_tab")
public class Uom {
	@Id
	@GeneratedValue(generator = "uom_seq_name")
	@SequenceGenerator(name = "uom_seq_name", sequenceName = "uom_seq")
	@Column(name = "uom_id_col", length= 10, nullable = false)
	private Integer id;
	@Column(name = "uom_type_col", length= 10, nullable = false)
	private String uomType;
	@Column(name = "uom_model_col", length= 20, nullable = false)
	private String uomModel;
	@Column(name = "description_col", length= 200, nullable = false)
	private String description;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUomType() {
		return uomType;
	}
	public void setUomType(String uomType) {
		this.uomType = uomType;
	}
	public String getUomModel() {
		return uomModel;
	}
	public void setUomModel(String uomModel) {
		this.uomModel = uomModel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
