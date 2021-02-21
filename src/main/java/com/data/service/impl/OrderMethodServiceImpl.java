package com.data.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.model.OrderMethod;
import com.data.repo.OrderMethodRepository;
import com.data.service.IOrderMethodService;

@Service
public class OrderMethodServiceImpl implements IOrderMethodService {

	
	@Autowired
	private OrderMethodRepository repo;
	
	@Override
	@Transactional
	public Integer saveOrderMethod(OrderMethod om) {
		Integer id =repo.save(om).getId();
		return id;
	}

	@Override
	@Transactional
	public void updateOrderMethod(OrderMethod om) {
		repo.save(om);
	}

	@Override
	@Transactional
	public void deleteOneOrderMethod(Integer id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderMethod> getAllOrderMethod() {
		List<OrderMethod> list = repo.findAll();
		return list;
	}
	
	@Override
	public Page<OrderMethod> getAllOrderMethod(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<OrderMethod> getOneOrderMethod(Integer id) {
		Optional<OrderMethod> opt = repo.findById(id);
		return opt;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean isOrderMethodExist(Integer id) {
		boolean isExist = repo.existsById(id);
		return isExist;
	}
	
	@Override
	public Boolean isOrderMethodCodeExist(String orderCode) {
		int count = repo.isOrderMethodCodeExist(orderCode);
		boolean flag = (count == 1 ? true : false);
		return flag;
		
	}
	
	@Override
	public List<Object[]> getOrderModeCount() {
		return repo.getOrderModeCount();
	}
	@Override
	public Integer getOrderModeTotalCount() {
		return repo.getOrderModeTotalCount();
	}

}
