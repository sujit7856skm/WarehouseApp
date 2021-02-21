package com.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.data.model.SaleOrder;

public interface ISaleOrderService {
	
	Integer saveSaleOrder(SaleOrder saleOrder);
	List<SaleOrder> getAllSaleOrder();
	Page<SaleOrder> getAllSaleOrder(Pageable pageable);
	Optional<SaleOrder> getOneSaleOrder(Integer id);
	void updateOneSaleOrder(SaleOrder saleOrder);
	void deleteOneSaleOrder(Integer id);
	Boolean isSaleOrderExist(Integer id);
	List<Object[]> getShipmentCodeCountList();
	Integer getOrderCodeCount(String orderCode);

}
