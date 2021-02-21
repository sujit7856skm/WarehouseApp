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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.data.model.ShipmentType;
import com.data.service.IShipmentTypeService;
import com.data.util.ChartGenerationUtil;
import com.data.view.ShipmentTypeExcelView;
import com.data.view.ShipmentTypePdfView;

@Controller
@RequestMapping("/shipmenttype")
public class ShipmentTypeController {

	Logger log = LoggerFactory.getLogger(ShipmentTypeController.class);
	
	@Autowired
	private IShipmentTypeService service;	
	@Autowired
	private ServletContext context;
	@Autowired
	private ChartGenerationUtil util;

	// 1. show register page
	/*
	 * url : /register, Type : GET then goto : ShipmentTypeRegister.html page
	 */
	@GetMapping("/register")
	public String showRegister(Model model) {
		// Form backing Object
		model.addAttribute("shipmentType", new ShipmentType());
		return "ShipmentTypeRegister";
	}

	// 2. perform save operation
	/*
	 * When click on the Create Shipment button it will go to the url
	 * /shipmenttype/save and save data to the database and send response back to
	 * the ShipmentTypeRegister.html page using Model memory
	 */
	@PostMapping("/save")
	public String saveShipmentType(
			@ModelAttribute ShipmentType shipmentType,
			Model model) 
	{
		log.info("Entered into saveShipmentType(shipmentType) method");
		try {
			service.saveShipmentType(shipmentType);
			String message = "Shipment '"+shipmentType.getShipmentCode()+"' saved sucessfully!";
			model.addAttribute("message", message);
			log.info(message);
			// Form backing Object
			model.addAttribute("shipmentType", new ShipmentType());
		} catch (Exception e) {
			log.error("Exception : "+ e);
			e.printStackTrace();
		}
		return "ShipmentTypeRegister";
	}

	// 3. Display all
	/*
	 * From Client(Browser) enter url like /shipment/all fetch all the data and send
	 * it to the ShipmentTypeData.html page using Model memory
	 */
	@GetMapping("/all")
	public String getAllShipmentType(
			@PageableDefault(page=0, size=5) Pageable pageable,
			Model model) 
	{
		log.info("Entered into getAllShipmentType() method");
		try {
			model.addAttribute("page", service.getAllShipmentType(pageable));
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info("About to show all shipmentType data");
		return "ShipmentTypeData";
	}

	// 4. remove one by id
	/*
	 * From Client(Browser) enter url like /shipment/delete/10 Delete One record
	 * based on their id send message as response to the ShipmentTypeData.html page
	 * using model memory
	 */
	@GetMapping("/delete/{id}/{page}")
	public String deleteShipmentType(
			@PathVariable Integer id,
			@PathVariable Integer page,
			Model model)
	{
		log.info("Entered into deleteShipmentType() method");
		String message = "";
		String errorMessage = "";
		try {
			if (service.isShipmentTypeExist(id)) {
				try {
					service.deleteShipmentType(id);
					message = "Shipment Type deleted sucessfully !";					
				} catch (Exception e) {
					errorMessage = "Can Not be Deleted, It is used by Purchase Order";
					model.addAttribute("errorMessage", errorMessage);
					e.printStackTrace();
				}
				log.info(message);
				model.addAttribute("message", message);

				// After deleting one record show remaining records
				model.addAttribute("page", service.getAllShipmentType(PageRequest.of(page, 5)));
			} else {
				errorMessage = "Shipment Type Not Existed!";
				log.info(errorMessage);
				model.addAttribute("errorMessage", errorMessage);

				// After deleting one record show remaining records
				model.addAttribute("page", service.getAllShipmentType(PageRequest.of(page, 5)));
			}
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}

		return "ShipmentTypeData";
	}

	// 5. show edit page with the data
	/*
	 * when user click on the edit button on the ui read the id from path variable
	 * and get the data based on the id if existed then send it the
	 * ShipmentTypeEdit.html page else go to the same ShipmentTypeData.html page
	 */
	@GetMapping("/edit/{id}")
	public String showShipmentTypeEdit(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@PathVariable Integer id,
			Model model) 
	{
		log.info("Entered into showEditShipmentType() method");
		String page = null;
		try {
			Optional<ShipmentType> opt = service.getOneShipmentType(id);
			if (opt.isPresent()) {
				// Form Backing object
				model.addAttribute("shipmentType", opt.get());
				page = "ShipmentTypeEdit";
				log.info("ShipmentType Existed, About to ShipmentTypeEdit page");
			} else {
				log.info("ShipmentType Not Existed !");
				model.addAttribute("message", "Shipment Type '"+id+"' not Existed");
				model.addAttribute("page", service.getAllShipmentType(pageable));
				page = "ShipmentTypeData";
			}
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		return page;
	}

	// 6. perform Update operation
	/*
	 * when request will come for the url:/shipmenttype/update read data from
	 * modelAttribute and update it to the dataBase
	 */
	@PostMapping("/update")
	public String updateShipmentType(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@ModelAttribute ShipmentType shipmentType,
			Model model) 
	{
		log.info("Entered into updateShipmentType() method");
		try {
			service.updateShipmentType(shipmentType);
			String message = "Shipment '"+shipmentType.getShipmentCode()+"' Updated Sucessfully!";
			model.addAttribute("message", message);
			log.info(message);
			// After deleting one record show remaining records
			model.addAttribute("page", service.getAllShipmentType(pageable));
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		return "ShipmentTypeData";
	}
	
	// 7. generate Excel Sheet of shipmentType Data
	@GetMapping("/excel")
	public ModelAndView exportAllSipmenttypeToExcel() {
		log.info("Entered into exportAllSipmenttypeToExcel() method");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new ShipmentTypeExcelView());
		try {
			// sending data to View Class
			List<ShipmentType> shipmentTypesList = service.getAllShipmentType();
			modelAndView.addObject("shipmentTypesList", shipmentTypesList);
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info("About to Export AllShipmentType");
		return modelAndView;
	}
	
	// 8. generate Excel Sheet of one row shipmentType Data
	@GetMapping("/excele/{id}")
	public ModelAndView exportOneShipmentTypeToExcel(@PathVariable Integer id) {
		log.info("Entered into exportOneShipmentTypeToExcel() method");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new ShipmentTypeExcelView());
		try {
			// sending data to View Class
			List<ShipmentType> shipmentTypesList = Arrays.asList();
			Optional<ShipmentType> opt = service.getOneShipmentType(id);
			if(opt.isPresent()) {
				shipmentTypesList = Arrays.asList(opt.get());
			}
			modelAndView.addObject("shipmentTypesList", shipmentTypesList);
		} catch (Exception e) {
			// TODO: handle exception
		}
		log.info("About to exportOneShipmentTypeToExcel");
		return modelAndView;
	}
	//9. Pdf Export All
	@GetMapping("/pdf")
	public ModelAndView exportPdfAll() {
		ModelAndView m = new ModelAndView();
		m.setView(new ShipmentTypePdfView());
		
		//get data from service
		List<ShipmentType> shipmentTypeList = service.getAllShipmentType();
		m.addObject("shipmentTypeList", shipmentTypeList);
		return m;
	}
	//10. Pdf Export one
	@GetMapping("/pdf/{id}")
	public ModelAndView exportPdfOne(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new ShipmentTypePdfView());
		
		//get data from service
		Optional<ShipmentType> shipmentTypeOpt = service.getOneShipmentType(id);
		m.addObject("shipmentTypeList", Arrays.asList(shipmentTypeOpt.get()));
		return m;
	}
	
	//11. Chart Generation
	@GetMapping("/chart")
	public String generateChart() {
		List<Object[]> modeCountList = service.getShipmentTypeModeCount();
		
		// getting real (temp) path from servlet Context
		String location = context.getRealPath("/");
		
		util.generatePieChart(location, "/ShipmentTypePieChart.png", modeCountList);
		util.generateBarChart(location, "/ShipmentTypeBarChart.png", modeCountList, "Shipment Mode");
		return "ShipmentTypeModeChart";
	}
	
	
	// AJAX Validation for Code
	@GetMapping("/validatecode")
	public @ResponseBody String validateShipmentCode(@RequestParam String shipmentCode) {
		log.info("Entered into validateShipmentTyoeCode() method");
		String message = "";
		if(service.getShipmentCode(shipmentCode)) {
			message = "Shipment Code Already Existed !";
		}
		log.info("validateShipmentTyoeCode validated..");
		return message;  
	}
	

}
