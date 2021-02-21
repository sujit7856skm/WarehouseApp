package com.data.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.data.model.Part;
import com.data.service.IPartService;
import com.data.service.IUomService;
import com.data.util.ChartGenerationUtil;
import com.data.view.PartExcelView;
import com.data.view.PartPdfView;

@Controller
@RequestMapping("/part")
public class PartController {
	
	Logger log = LoggerFactory.getLogger(PartController.class);
	
	@Autowired
	private IPartService partService;
	
	@Autowired
	private ChartGenerationUtil util;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private IUomService uomService;
	
	private void addUomIdModelInDropDown(Model model) {
		model.addAttribute("uomList", uomService.getUomIdAndModel());
	}
	
	//show Registration page
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("part", new Part());
		addUomIdModelInDropDown(model);
		return "PartRegister";
	}
	
	//save Part
	@PostMapping("/save")
	public String savePart(
			@ModelAttribute Part part,
			Model model
			) 
	{
		log.info("Entered into savePart() method and about to save the data");
		try {
			Integer id = partService.savePart(part);
			String message = "Part Saved Successsfullly With Id : "+id;
			log.debug(message);
			model.addAttribute("message", message);
			model.addAttribute("part", new Part());
			addUomIdModelInDropDown(model);
		} catch (Exception e) {
			log.error("Exception : {}",e);
			e.printStackTrace();
		}
		log.info("Going back to ui page with Response");
		return "PartRegister";
	}
	
	
	//Get All Part
	@GetMapping("/all")
	public String getAllPart(
			@PageableDefault(page=0, size=5) Pageable pageable,
			Model model) 
	{
		Page<Part> partPage = partService.getAllPart(pageable);
		model.addAttribute("page", partPage);
		return "PartData";
	}
	//delete one Part
	@GetMapping("delete/{id}/{page}")
	public String deleteOnePart(
			@PathVariable Integer id,
			@PathVariable Integer page,
			Model model) 
	{
		if(partService.isPartExist(id)) {
			String message = "";
			try {				
				partService.deleteOnePart(id);
				message = "Part '"+id+"' Deleted Successfully ";
			} catch (Exception e) {
				message = "Part '"+id+"' can not be deleted used in Purchase/Sale Order";
			}
			model.addAttribute("message", message);
		}else {
			String errorMessage = "Part '"+id+"' Not Existed !" ;
			model.addAttribute("errorMessage", errorMessage);
		}
		Page<Part> partPage = partService.getAllPart(PageRequest.of(page, 5));
		model.addAttribute("page", partPage);
		return "PartData";
	}
	
	// View One part
	@GetMapping("/view/{id}")
	public String viewOnePart(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@PathVariable Integer id,
			Model model) 
	{
		if(partService.isPartExist(id)) {
			Optional<Part> part = partService.getOnePart(id);
			model.addAttribute("part", part.get());
			return "PartViewOne";
		}
		model.addAttribute("page", partService.getAllPart(pageable));
		model.addAttribute("errorMessage", "Part '"+id+"' Not Existed !");
		return "PartData";
	}
	
	//show edit page with data
	@GetMapping("/edit/{id}")
	public String showPartEdit(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@PathVariable Integer id,
			Model  model) 
	{
		if(partService.isPartExist(id)) {
			Optional<Part> part = partService.getOnePart(id);
			model.addAttribute("part", part.get());
			addUomIdModelInDropDown(model);
			return "PartEdit";
		}

		model.addAttribute("page", partService.getAllPart(pageable));
		model.addAttribute("errorMessage", "Part '"+id+"' Not Existed !");
		return "PartData";
	}
	
	//Update one Part
	@PostMapping("/update")
	public String updatePart(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@ModelAttribute Part part,
			Model model) 
	{
		partService.updateOnePart(part);
		String message = "Part '"+part.getPartCode()+"' Updated Successfully ";

		model.addAttribute("page", partService.getAllPart(pageable));
		model.addAttribute("message", message);
		return "PartData";
	}
	
	//Export all in Excel
	@GetMapping("/excel")
	public ModelAndView exportAllPartToExcel() {
		ModelAndView m = new ModelAndView();
		m.setView(new PartExcelView());
		List<Part> partList = partService.getAllPart();
		m.addObject("partList", partList);
		return m;
	}
	//Export one in Excel
	@GetMapping("/excel/{id}")
	public ModelAndView exportOnewPartToExcel(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new PartExcelView());
		Optional<Part> partOpt = partService.getOnePart(id);
		List<Part> partList = Arrays.asList(partOpt.get());
		m.addObject("partList", partList);
		return m;
	}
	
	//Export All in PDF
	@GetMapping("/pdf")
	public ModelAndView exportAllPartToPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new PartPdfView());
		List<Part> partList = partService.getAllPart();
		m.addObject("partList", partList);
		return m;
	}
	//Export One in PDF
	@GetMapping("/pdf/{id}")
	public ModelAndView exportOnePartToPdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new PartPdfView());
		List<Part> partList = Arrays.asList( partService.getOnePart(id).get());
		m.addObject("partList", partList);
		return m;
	}
	
	// Chart Generation
	@GetMapping("/chart")
	public String generateChart() {
		String location = context.getRealPath("/");
		List<Object[]> uomCountList = partService.getUomCountList();
		for(Object[] ob:uomCountList) {
			System.out.println(ob[0] +"-> "+ob[1]);
		}
		util.generatePieChart(location, "/PartPieChart.png", uomCountList);
		util.generateBarChart(location, "/PartBarChart.png", uomCountList, "Part UOM");
		return "PartChart";
	}
	
	//AJAX validation for partCode
	@GetMapping("/validatecode")
	public @ResponseBody String validatePartCode(@RequestParam String partCode) {
		Integer codeCount = partService.getPartCodeCount(partCode);
		String message = "";
		if(codeCount >= 1) {
			message = "Part Code Already Existed !";
		}
		return message;
	}

}
