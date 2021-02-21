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

import com.data.model.ShipmentType;
import com.data.repo.ShipmentTypeRepository;
import com.data.service.IShipmentTypeService;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {

	@Autowired
	private ShipmentTypeRepository repo;
	
	@Override
	@Transactional
	public Integer saveShipmentType(ShipmentType st) {
		Integer id = repo.save(st).getId();
		return id;
	}

	@Override
	@Transactional
	public void updateShipmentType(ShipmentType st) {
		repo.save(st);
	}

	@Override
	@Transactional
	public void deleteShipmentType(Integer id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ShipmentType> getOneShipmentType(Integer id) {
		Optional<ShipmentType> opt = repo.findById(id);
		return opt;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShipmentType> getAllShipmentType() {
		List<ShipmentType> list = repo.findAll();
		return list;
	}
	
	@Override
	public Page<ShipmentType> getAllShipmentType(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isShipmentTypeExist(Integer id) {
		boolean isExist = repo.existsById(id);
		return isExist;
	}
	
	@Override
	public boolean getShipmentCode(String shipmentCode) {
		int count = repo.isShipmentTypeCodeExist(shipmentCode);
		boolean flag = (count == 1 ? true : false);
		return flag;
	}
	@Override
	public List<Object[]> getShipmentTypeModeCount() {
		return repo.getShipmentTypeModeCount();
	}
	
	@Override
	public Map<Integer, String> getShipmentIdAndCode() {
		Map<Integer, String> idCodeMap = repo.getIdAndShipmentCode()
				.stream()
				.filter(array->array!=null)
				.collect(Collectors.toMap(array->Integer.valueOf(array[0].toString()), array->array[1].toString()));
		return idCodeMap;
	}
}
