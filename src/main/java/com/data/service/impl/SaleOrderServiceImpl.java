package com.data.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.data.model.SaleOrder;
import com.data.repo.SaleOrderRepository;
import com.data.service.ISaleOrderService;

@Service
public class SaleOrderServiceImpl implements ISaleOrderService {
	
	@Autowired
	private SaleOrderRepository saleOrderRepo;

	@Override
	public Integer saveSaleOrder(SaleOrder saleOrder) {
		return saleOrderRepo.save(saleOrder).getId();
	}

	@Override
	public List<SaleOrder> getAllSaleOrder() {
		return saleOrderRepo.findAll();
	}
	@Override
	public Page<SaleOrder> getAllSaleOrder(Pageable pageable) {
		return saleOrderRepo.findAll(pageable);
	}
	@Override
	public Optional<SaleOrder> getOneSaleOrder(Integer id) {
		return saleOrderRepo.findById(id);
	}

	@Override
	public void updateOneSaleOrder(SaleOrder saleOrder) {
		saleOrderRepo.save(saleOrder);
	}

	@Override
	public void deleteOneSaleOrder(Integer id) {
		saleOrderRepo.deleteById(id);
	}

	@Override
	public Boolean isSaleOrderExist(Integer id) {
		return saleOrderRepo.existsById(id);
	}
	
	@Override
	public List<Object[]> getShipmentCodeCountList() {
		return saleOrderRepo.getShipCodeCountList();
	}
	
	@Override
	public Integer getOrderCodeCount(String orderCode) {
		return saleOrderRepo.getOrderCodeCount(orderCode);
	}
}
