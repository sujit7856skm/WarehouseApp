package com.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "wh_user_type_tab")
public class WhUserType {
	
	@Id
	@GeneratedValue(generator = "whusertype_seq")
	@SequenceGenerator(name = "whusertype_seq", sequenceName = "whusertype_seq")
	@Column(name = "wh_user_id")
	private Integer id;
	
	@Column(name = "wh_user_type")
	private String userType;
	
	@Column(name = "wh_user_code")
	private String userCode;
	
	@Column(name = "wh_user_for")
	private String userFor;
	
	@Column(name = "wh_user_email")
	private String userEmail;
	
	@Column(name = "wh_user_contact")
	private String userContact;
	
	@Column(name = "wh_user_id_type")
	private String userIdType;
	
	@Column(name = "wh_user_if_other")
	private String ifOther;
	
	@Column(name = "wh_user_id_number")
	private String idNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserFor() {
		return userFor;
	}

	public void setUserFor(String userFor) {
		this.userFor = userFor;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserContact() {
		return userContact;
	}

	public void setUserContact(String userContact) {
		this.userContact = userContact;
	}

	public String getUserIdType() {
		return userIdType;
	}

	public void setUserIdType(String userIdType) {
		this.userIdType = userIdType;
	}

	public String getIfOther() {
		return ifOther;
	}

	public void setIfOther(String ifOther) {
		this.ifOther = ifOther;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	
}
