package com.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.data.model.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
	
	@Query("SELECT count(PO.orderCode) FROM PurchaseOrder PO WHERE PO.orderCode=:orderCode")
	Integer getOrderCodeCount(String orderCode);
	
	@Query("SELECT PO.shipmentType.shipmentCode, COUNT(PO.shipmentType.shipmentCode) FROM PurchaseOrder PO GROUP BY PO.shipmentType.shipmentCode")
	List<Object[]> getShipmentCodeCountList();
	
	@Modifying
	@Query("UPDATE PurchaseOrder SET status=:status WHERE id=:poId")
	void updatePurchaseOrderStatus(String status, Integer poId);
}
