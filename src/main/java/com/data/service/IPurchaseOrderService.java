package com.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.data.model.PurchaseDtls;
import com.data.model.PurchaseOrder;

public interface IPurchaseOrderService {
	Integer savePurchaseOrder(PurchaseOrder purchaseOrder);
	List<PurchaseOrder> getAllPurchaseOrder();
	Page<PurchaseOrder> getAllPurchaseOrder(Pageable pageable);
	void deleteOnePurchaseOrder(Integer id);
	Optional<PurchaseOrder> getOnePurchaseOrder(Integer id);
	void updatePurchaseOrder(PurchaseOrder purchaseOrder);
	Boolean isPurchaseOrderExist(Integer id);
	List<Object[]> getShipmentCodeCountList();
	Integer getOrderCodeCount(String orderCode);
	void updatePurchaseorderStatus(Integer poId, String status);
	
	/******************************************************************************************/
	/********************              Screen #2                                  *************/
	/******************************************************************************************/
		
	Integer addPart(PurchaseDtls purchaseDtls);
	
	List<PurchaseDtls> getPurchaseDtlByPurchaseOrderId(Integer purchaseOrderId);
	
	Integer getCountPurchaseDtlByPurchaseOrderId(Integer purchaseOrderId);
	
	void removeOnePart(Integer id);
}
