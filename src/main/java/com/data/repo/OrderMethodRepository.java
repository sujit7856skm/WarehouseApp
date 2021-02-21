package com.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.data.model.OrderMethod;

public interface OrderMethodRepository extends JpaRepository<OrderMethod, Integer> {
	@Query("SELECT COUNT(om.orderCode) FROM OrderMethod om WHERE om.orderCode=:orderCode")
	public Integer isOrderMethodCodeExist(String orderCode);
	
	@Query("SELECT OM.orderMode, COUNT(OM.orderMode) FROM OrderMethod OM GROUP BY OM.orderMode")
	public List<Object[]> getOrderModeCount();
	
	@Query("SELECT COUNT(OM.orderMode) FROM OrderMethod OM")
	public Integer getOrderModeTotalCount();
	
}
