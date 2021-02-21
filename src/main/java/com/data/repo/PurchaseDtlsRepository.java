package com.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.data.model.PurchaseDtls;

public interface PurchaseDtlsRepository extends JpaRepository<PurchaseDtls, Integer> {

	@Query("SELECT PD FROM PurchaseDtls PD WHERE PD.purchaseOrder.id=:purchaseOrderId")
	List<PurchaseDtls> getPurchaseDtlByPurchaseOrderId(Integer purchaseOrderId);
	
	@Query("SELECT COUNT(PD.id) FROM PurchaseDtls PD WHERE PD.purchaseOrder.id=:purchaseOrderId")
	Integer getCountPurchaseDtlByPurchaseOrderId(Integer purchaseOrderId);
	
	
}
