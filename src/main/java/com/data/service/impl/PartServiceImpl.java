package com.data.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.data.model.Part;
import com.data.repo.PartRepository;
import com.data.service.IPartService;

@Service
public class PartServiceImpl implements IPartService {

	@Autowired
	private PartRepository repo;
	
	@Override
	@Transactional
	public Integer savePart(Part part) {
		return repo.save(part).getId();
	}

	@Override
	public List<Part> getAllPart() {
		return repo.findAll();
		
	}

	@Override
	@Transactional
	public void deleteOnePart(Integer id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional
	public void updateOnePart(Part part) {
		repo.save(part);
	}

	@Override
	public Optional<Part> getOnePart(Integer id) {
		return repo.findById(id);
	}

	@Override
	public Boolean isPartExist(Integer id) {
		return repo.existsById(id);
	}

	@Override
	public Integer getPartCodeCount(String partCode) {
		return repo.getPartCodeCount(partCode);
	}
	@Override
	public List<Object[]> getUomCountList() {
		return repo.getUomCountList();
	}
	
	@Override
	public Page<Part> getAllPart(Pageable pageable) {
		return repo.findAll(pageable);
	}
	
	@Override
	public Map<Integer, String> getPartIdAndCode() {
		return repo.getPartIdAndCode()
				.stream()
				.filter(array->array != null)
				.collect(Collectors.toMap(
						array->Integer.valueOf(array[0].toString()),
						array->array[1].toString()
						));
	}
}