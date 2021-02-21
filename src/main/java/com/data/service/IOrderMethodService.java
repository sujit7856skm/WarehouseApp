package com.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.data.model.OrderMethod;

public interface IOrderMethodService {
	
	Integer saveOrderMethod(OrderMethod om);
	void updateOrderMethod(OrderMethod om);
	void deleteOneOrderMethod(Integer id);
	List<OrderMethod>getAllOrderMethod();
	Page<OrderMethod>getAllOrderMethod(Pageable pageable);
	Optional<OrderMethod>getOneOrderMethod(Integer id);
	Boolean isOrderMethodExist(Integer id);
	Boolean isOrderMethodCodeExist(String orderCode);
	List<Object[]> getOrderModeCount();
	Integer getOrderModeTotalCount();
}
