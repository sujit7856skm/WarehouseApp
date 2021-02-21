package com.data.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.model.PurchaseDtls;
import com.data.model.PurchaseOrder;
import com.data.repo.PurchaseDtlsRepository;
import com.data.repo.PurchaseOrderRepository;
import com.data.service.IPurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepo;
	
	@Autowired
	private PurchaseDtlsRepository purchaseDtlRepo;
	
	@Override
	@Transactional
	public Integer savePurchaseOrder(PurchaseOrder purchaseOrder) {
		return purchaseOrderRepo.save(purchaseOrder).getId();
	}

	@Override
	@Transactional(readOnly = true)
	public List<PurchaseOrder> getAllPurchaseOrder() {
		return purchaseOrderRepo.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<PurchaseOrder> getAllPurchaseOrder(Pageable pageable) {
		return purchaseOrderRepo.findAll(pageable);
	}

	@Override
	@Transactional
	public void deleteOnePurchaseOrder(Integer id) {
		purchaseOrderRepo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PurchaseOrder> getOnePurchaseOrder(Integer id) {
		return purchaseOrderRepo.findById(id);
	}

	@Override
	@Transactional
	public void updatePurchaseOrder(PurchaseOrder purchaseOrder) {
		purchaseOrderRepo.save(purchaseOrder);
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean isPurchaseOrderExist(Integer id) {
		return purchaseOrderRepo.existsById(id);
	}
	
	@Override
	public List<Object[]> getShipmentCodeCountList() {
		return purchaseOrderRepo.getShipmentCodeCountList();
	}
	
	@Override
	public Integer getOrderCodeCount(String orderCode) {
		return purchaseOrderRepo.getOrderCodeCount(orderCode);
	}
	
	@Transactional
	@Override
	public void updatePurchaseorderStatus(Integer poId, String status) {
		purchaseOrderRepo.updatePurchaseOrderStatus(status, poId);
		
	}
	
	/******************************************************************************************/
	/********************              Screen #2                                  *************/
	/******************************************************************************************/
	
	
	@Override
	public Integer addPart(PurchaseDtls purchaseDtls) {
		return purchaseDtlRepo.save(purchaseDtls).getId();
	}
	
	@Override
	public List<PurchaseDtls> getPurchaseDtlByPurchaseOrderId(Integer purchaseOrderId) {
		return purchaseDtlRepo.getPurchaseDtlByPurchaseOrderId(purchaseOrderId);
	}
	
	@Override
	public Integer getCountPurchaseDtlByPurchaseOrderId(Integer purchaseOrderId) {
		return purchaseDtlRepo.getCountPurchaseDtlByPurchaseOrderId(purchaseOrderId);
	}
	
	@Override
	public void removeOnePart(Integer id) {
		purchaseDtlRepo.deleteById(id);
	}

}
