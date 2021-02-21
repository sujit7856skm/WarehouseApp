package com.data.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.ModelAndView;

import com.data.model.Uom;
import com.data.service.IUomService;
import com.data.util.ChartGenerationUtil;
import com.data.view.UomExcelView;
import com.data.view.UomPdfView;

@Controller
@RequestMapping("/uom")
public class UomController {
	
	Logger log = LoggerFactory.getLogger(UomController.class);
	
	@Autowired
	private IUomService service;
	
	@Autowired
	private ServletContext context;
	@Autowired
	private ChartGenerationUtil util;
	
	// Show the UomRegister.html page
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("uom", new Uom());
		return "UomRegister";
	}
	
	// perfoem save Operation
	@PostMapping("/save")
	public String saveUom(
			@ModelAttribute Uom uom,
			Model model) 
	{
		log.info("Entered into saveUom() method");
		try {
			service.saveUom(uom);
			String message = "Uom '"+uom.getUomModel()+"' Registered sucessfully";
			log.info(message);
			model.addAttribute("message", message);
			//form backing Object
			model.addAttribute("uom", new Uom());
		} catch (Exception e) {
			log.error("Exception : "+ e);
			e.printStackTrace();
		}
		log.info("About to send Response in UI page");
		return "UomRegister";
	}

	// show all UomData
	@GetMapping("/all")
	public String getAllUom(
			@PageableDefault(page=0, size=5) Pageable pageable,
			Model model) 
	{
		log.info("Entered into getAllUom() method");
		try {
			model.addAttribute("page", service.getAllUom(pageable));
			log.debug("Data got and about to send UI page");
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		return "UomData";
	}
	
	// Delete One Uom by id
	@GetMapping("/delete/{id}/{page}")
	public String deleteOneUom(
			@PathVariable Integer id,
			@PathVariable Integer page,
			Model model) 
	{
		log.info("Entered into deleteOneUom() method");
		String message = null;
			log.info("About to call isUomExiste(id) method to check whether UOM exists or not");
			if(service.isUomExist(id)) {
				log.debug("Uom Existed!");
				try {
					service.deleteOneUom(id);
					message = "Uom delete sucessfully!";
					log.debug(message);					
				} catch (Exception e) {
					String errorMessage = "Uom can not be deleted, it is used by part";
					model.addAttribute("errorMessage", errorMessage);
					e.printStackTrace();
					log.error("Exception : "+e);
				}
			}else {
				message = "Uom Not Existed!";
				log.warn(message);
			}
			
			model.addAttribute("message", message);
			model.addAttribute("page", service.getAllUom(PageRequest.of(page, 5)));

		log.info("About to show UomData page");
		return "UomData";
	}
	
	// Show UomEdit.html page with the data
	@GetMapping("/edit/{id}")
	public String showEditUom(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@PathVariable Integer id,
			Model model) 
	{
		log.info("Entered into showEditUom(id) method");
		try {
			Optional<Uom> opt = service.getOneUom(id);
			if(opt.isPresent()) {
				model.addAttribute("uom", opt.get());
				log.info("About to show editUom page");
				return "UomEdit";
			}
			log.warn("Data not existed with given id");
			model.addAttribute("message", "Uom Not Existed");
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		model.addAttribute("page", service.getAllUom(pageable));
		return "UomData";
	}
	
	// Update Uom
	@PostMapping("/update")
	public String updateUom(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@ModelAttribute Uom uom,
			Model model)
	{
		log.info("Entered into updateUom() method");
		try {
			service.updateUom(uom);
			log.debug("Uom Updated..");
			model.addAttribute("page", service.getAllUom(pageable));
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info("Going back to UomData Page");
		return "UomData";	
	}
	
	//Export All in Excel Format
	@GetMapping("/excel")
	public ModelAndView exportAllUom() {
		log.info("Entered into exportAllUom() method");
		ModelAndView m = new ModelAndView();
		m.setView(new UomExcelView());
		try {
			
			List<Uom> uomList = service.getAllUom();
			m.addObject("uomList", uomList);
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info("All Uom Exported");
		return m;
	}
	//Export One in Excel Format
	@GetMapping("/excel/{id}")
	public ModelAndView oneExcelExport(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new UomExcelView());
		
		try {
			List<Uom> uomList = Arrays.asList(service.getOneUom(id).get());
			m.addObject("uomList", uomList);
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info("Seleted Uom exported");
		return m;
	}
	
	// Export all to pdf
	@GetMapping("/pdf")
	public ModelAndView exortAllUomToPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new UomPdfView());
		//getting data from service
		List<Uom> uomList = service.getAllUom();
		m.addObject("uomList", uomList);
		return m;
	}
	// Export one to pdf
	@GetMapping("/pdf/{id}")
	public ModelAndView exortOneUomToPdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new UomPdfView());
		//getting data from service
		Optional<Uom> uomOpt = service.getOneUom(id);
		m.addObject("uomList", Arrays.asList(uomOpt.get()));
		return m;
	}
	
	@GetMapping("/chart")
	public String generateChart() {
		String location = context.getRealPath("/");
		
		//getting data from Service 
		List<Object[]> uomTypeCountList = service.getUomTypeCount();
		util.generatePieChart(location, "/UomTypePieChart.png", uomTypeCountList);
		util.generateBarChart(location, "/UomTypeBarChart.png", uomTypeCountList, "UOM Type");
		return "UomTypeChart";
	}
}
