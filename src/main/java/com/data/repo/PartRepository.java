package com.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.data.model.Part;

public interface PartRepository extends JpaRepository<Part, Integer> {
	
	@Query("SELECT COUNT(PT.partCode) FROM Part PT WHERE partCode=:partCode")
	Integer getPartCodeCount(String partCode);
	
	@Query("SELECT  P.uom.uomModel, COUNT(P.uom.uomModel) FROM Part P GROUP BY P.uom.uomModel")
	List<Object[]> getUomCountList();
	
	@Query("SELECT P.id, P.partCode FROM Part P")
	List<Object[]> getPartIdAndCode();

}
