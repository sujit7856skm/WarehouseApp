package com.data.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.data.model.ShipmentType;

public interface IShipmentTypeService {
	Integer saveShipmentType(ShipmentType st);
	void updateShipmentType(ShipmentType st);
	void deleteShipmentType(Integer id);
	Optional<ShipmentType> getOneShipmentType(Integer id);
	List<ShipmentType> getAllShipmentType();
	Page<ShipmentType> getAllShipmentType(Pageable pageable);
	boolean isShipmentTypeExist(Integer id);
	boolean getShipmentCode(String shipmentCode);
	List<Object[]> getShipmentTypeModeCount();
	Map<Integer, String> getShipmentIdAndCode();
}
