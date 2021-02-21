package com.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.data.model.ShipmentType;

public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Integer> {
	@Query("SELECT COUNT(st.shipmentCode) FROM ShipmentType st WHERE st.shipmentCode=:shipmentCode")
	public Integer isShipmentTypeCodeExist(String shipmentCode);
	
	@Query("SELECT ST.shipmentMode, COUNT(ST.shipmentMode) FROM ShipmentType ST GROUP BY shipmentMode")
	public List<Object[]> getShipmentTypeModeCount();
	
	@Query("SELECT ship.id, ship.shipmentCode FROM ShipmentType ship WHERE ship.enableShipment='Yes'")
	List<Object[]> getIdAndShipmentCode();
}
