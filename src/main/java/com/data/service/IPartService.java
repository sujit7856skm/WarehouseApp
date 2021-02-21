package com.data.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.data.model.Part;

public interface IPartService {
	Integer savePart(Part part);
	List<Part> getAllPart();
	Page<Part> getAllPart(Pageable pageable);
	void deleteOnePart(Integer id);
	void updateOnePart(Part part);
	Optional<Part> getOnePart(Integer id);
	Boolean isPartExist(Integer id);
	Integer getPartCodeCount(String partCode);
	List<Object[]> getUomCountList();
	Map<Integer, String> getPartIdAndCode();
}
