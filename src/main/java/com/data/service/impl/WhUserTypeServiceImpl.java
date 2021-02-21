package com.data.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.model.WhUserType;
import com.data.repo.WhUserTypeRepository;
import com.data.service.IWhUserTypeService;

@Service
public class WhUserTypeServiceImpl implements IWhUserTypeService {

	@Autowired
	private WhUserTypeRepository repo;
	
	@Override
	@Transactional
	public Integer saveWhUserType(WhUserType user) {
		Integer id = repo.save(user).getId();
		return id;
	}

	@Override
	@Transactional
	public void updateWhUserType(WhUserType user) {
		repo.save(user);
	}

	@Override
	@Transactional
	public void deleteWhUserType(Integer id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<WhUserType> getOneWhUserType(Integer id) {
		Optional<WhUserType> opt = repo.findById(id);
		return opt;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WhUserType> getAllWhUserType() {
		List<WhUserType> list = repo.findAll();
		return list;
	}
	
	@Override
	public Page<WhUserType> getAllWhUserType(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isWhUserTypeExist(Integer id) {
		boolean isExist = repo.existsById(id);
		return isExist;
	}
	//Ajax validation for userEmail in Register page
	@Override
	public boolean isWhUserEmailExist(String userEmail) {
		int emailCount = repo.getWhUserTypeMailCount(userEmail);
		boolean flag = (emailCount == 1 ? true : false);
		return flag;
	}
	
	//Ajax validation for userEmail in Edit page
	@Override
	public boolean isWhUserEmailExistForEdit(String userEmail,Integer id) {
		int emailCount = repo.getWhUserTypeMailCountForEdit(userEmail, id);
		boolean flag = (emailCount == 1 ? true : false);
		return flag;
	}
	
	////get User type Count
	@Override
	public List<Object[]> getWhUserTypeCount() {
		return repo.getUserTypeCount();
	}
	
	@Override
	public Map<Integer, String> getUserIdAndUserCode(String userType) {
		return repo.getUserIdAndUserCode(userType)
				.stream()
				.filter(array->array!=null)
				.collect(Collectors.toMap(
						array->Integer.valueOf(array[0].toString()),
						array->array[1].toString()
						));
	}
}
