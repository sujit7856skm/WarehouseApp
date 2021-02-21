package com.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "document_tab")
public class Document {
	@Id
	@Column(name = "doc_id_col")
	private Integer docId;
	@Column(name = "doc_name_col")
	private String docName;
	@Column(name = "doc_data_col")
	@Lob //LOB + Byte[] = BLOB
	private byte[] docData;
	public Integer getDocId() {
		return docId;
	}
	public void setDocId(Integer docId) {
		this.docId = docId;
	}
	public byte[] getDocData() {
		return docData;
	}
	public void setDocData(byte[] docData) {
		this.docData = docData;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	
}
