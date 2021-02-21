package com.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.data.model.SaleOrder;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Integer> {
	
	@Query("select count(SO.orderCode) from SaleOrder SO where SO.orderCode=:orderCode")
	Integer getOrderCodeCount(String orderCode);
	
	@Query("SELECT SO.shipmentType.shipmentCode, COUNT(SO.shipmentType.shipmentCode) FROM SaleOrder SO GROUP BY SO.shipmentType.shipmentCode")
	List<Object[]> getShipCodeCountList();
	
}
