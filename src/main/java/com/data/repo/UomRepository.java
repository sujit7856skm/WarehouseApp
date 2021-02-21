package com.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.data.model.Uom;

public interface UomRepository extends JpaRepository<Uom, Integer> {
	@Query("SELECT U.uomType, COUNT(U.uomType) FROM Uom U GROUP BY U.uomType")
	List<Object[]> getUomTypeCount();
	
	@Query("SELECT id, uomModel FROM Uom")
	List<Object[]> getIdAndModel();
	
	@Query("SELECT COUNT(U.uomModel) FROM Uom U WHERE U.uomModel=:uomModel")
	Integer getUomModelCount(String uomModel);
}
