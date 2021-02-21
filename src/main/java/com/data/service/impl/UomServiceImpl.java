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

import com.data.model.Uom;
import com.data.repo.UomRepository;
import com.data.service.IUomService;

@Service
public class UomServiceImpl implements IUomService {

	@Autowired
	private UomRepository repo;
	
	@Override
	@Transactional
	public Integer saveUom(Uom uom) {
		Integer id = repo.save(uom).getId();
		return id;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Uom> getAllUom() {
		List<Uom> list = repo.findAll();
		return list;
	}

	@Override
	public Page<Uom> getAllUom(Pageable pageable) {
		return repo.findAll(pageable);
	}
	
	@Override
	@Transactional
	public void deleteOneUom(Integer id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Uom> getOneUom(Integer id) {
		Optional<Uom> uom = repo.findById(id);
		return uom;
	}

	@Override
	@Transactional
	public void updateUom(Uom uom) {
		repo.save(uom);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isUomExist(Integer id) {
		boolean isExist = repo.existsById(id);
		return isExist;
	}
	
	@Override
	public List<Object[]> getUomTypeCount() {
		return repo.getUomTypeCount();
	}
	
	@Override
	public Map<Integer, String> getUomIdAndModel() {
		Map<Integer, String> idModelMap = repo.getIdAndModel()
		.stream()
		.filter(array->array != null)
		.collect(Collectors.toMap(
				array->Integer.valueOf(array[0].toString()), 
				array->array[1].toString()
			));
		return idModelMap;
	}
	
	@Override
	public Integer getUomModelCount(String uomModel) {
		return repo.getUomModelCount(uomModel);
	}

}