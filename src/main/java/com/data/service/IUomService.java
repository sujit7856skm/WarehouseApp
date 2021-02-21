package com.data.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.data.model.Uom;

public interface IUomService {
	Integer saveUom(Uom uom);
	List<Uom> getAllUom();
	Page<Uom> getAllUom(Pageable pageable);
	void deleteOneUom(Integer id);
	Optional<Uom> getOneUom(Integer id);
	void updateUom(Uom uom);
	boolean isUomExist(Integer id);
	List<Object[]> getUomTypeCount();
	Map<Integer, String> getUomIdAndModel();
	Integer getUomModelCount(String uomModel);
}
