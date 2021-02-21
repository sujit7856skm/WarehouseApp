package com.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.data.model.WhUserType;

public interface WhUserTypeRepository extends JpaRepository<WhUserType, Integer> {
	@Query("SELECT COUNT(ut.userEmail) FROM WhUserType ut WHERE ut.userEmail=:userEmail")
	public Integer getWhUserTypeMailCount(String userEmail);
	
	@Query("SELECT COUNT(ut.userEmail) FROM WhUserType ut WHERE ut.userEmail=:userEmail and ut.id<>:id")
	public Integer getWhUserTypeMailCountForEdit(String userEmail,Integer id);
	
	@Query("SELECT UT.userType, COUNT(UT.userType) FROM WhUserType UT GROUP BY UT.userType")
	public List<Object[]> getUserTypeCount();
	
	@Query("SELECT U.id, U.userCode FROM WhUserType U WHERE U.userType=:userType")
	List<Object[]> getUserIdAndUserCode(String userType);
}
