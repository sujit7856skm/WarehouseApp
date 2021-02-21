package com.data.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.data.model.WhUserType;

public interface IWhUserTypeService {
	Integer saveWhUserType(WhUserType user);
	void updateWhUserType(WhUserType user);
	void deleteWhUserType(Integer id);
	Optional<WhUserType> getOneWhUserType(Integer id);
	List<WhUserType> getAllWhUserType();
	Page<WhUserType> getAllWhUserType(Pageable pageable);
	boolean isWhUserTypeExist(Integer id);
	boolean isWhUserEmailExist(String userEmail);
	boolean isWhUserEmailExistForEdit(String userEmail, Integer id);
	List<Object[]> getWhUserTypeCount();
	Map<Integer, String> getUserIdAndUserCode(String userType);
}
