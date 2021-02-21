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

import com.data.model.WhUserType;
import com.data.service.IWhUserTypeService;
import com.data.util.ChartGenerationUtil;
import com.data.util.EmailUtil;
import com.data.view.WhUserTypeExcelView;
import com.data.view.WhUserTypePdfView;

@Controller
@RequestMapping("/whusertype")
public class WhUserTypeController {

	Logger log = LoggerFactory.getLogger(WhUserTypeController.class);
	
	@Autowired
	private IWhUserTypeService service;
	
	@Autowired
	private ServletContext contxt;
	
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private ChartGenerationUtil util;

	// 1. show register page
	/*
	 * url : /register, Type : GET then goto : WhUserTypeRegister.html page
	 */
	@GetMapping("/register")
	public String showRegister(Model model) {
		// Form backing Object
		model.addAttribute("whUserType", new WhUserType());
		return "WhUserTypeRegister";
	}

	// 2. perform save operation
	/*
	 * When click on the Create Shipment button it will go to the url
	 * /WhUserType/save and save data to the database and send response back to
	 * the WhUserTypeRegister.html page using Model memory
	 */
	@PostMapping("/save")
	public String saveWhUserType(
			@ModelAttribute WhUserType whUserType, 
			Model model)
	{
		log.info("Entered into saveWhUserType() method");
		try {
			int id = service.saveWhUserType(whUserType);
			String message = "User '"+whUserType.getUserCode()+"' saved sucessfully!";
			log.debug(message);
			new Thread(new Runnable() {
				@Override
				public void run() {
					boolean flag = emailUtil.send(
							whUserType.getUserEmail(),
							"Wh User Type Registration",
							"Hello '"+whUserType.getUserCode()+"'!\n You are registered with id =>"+id 
						);
					System.out.println(flag ? "Email sent !" : "Unable to send email !");
				}
			}).start();
			message = message + " Your Id is sent to your Email";
			log.info(message);
			model.addAttribute("message", message);
			// Form backing Object
			model.addAttribute("whUserType", new WhUserType());
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		return "WhUserTypeRegister";
	}

	// 3. Display all
	/*
	 * From Client(Browser) enter url like /shipment/all fetch all the data and send
	 * it to the WhUserTypeData.html page using Model memory
	 */
	@GetMapping("/all")
	public String getAllWhUserType(
			@PageableDefault(page=0, size=5) Pageable pageable,
			Model model) {
		log.info("Entered into getAllWhUserType() method");
		try {
			model.addAttribute("page", service.getAllWhUserType(pageable));
			log.info("Data fetched and About to show the data in ui");
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		return "WhUserTypeData";
	}

	// 4. remove one by id
	/*
	 * From Client(Browser) enter url like /shipment/delete/10 Delete One record
	 * based on their id send message as response to the WhUserTypeData.html page
	 * using model memory
	 */
	@GetMapping("/delete/{id}/{page}")
	public String deletOneWhUserType(
			@PathVariable Integer id, 
			@PathVariable Integer page, 
			Model model) 
	{
		log.info("Entered into deletOneWhUserType() method");
		try {
			if (service.isWhUserTypeExist(id)) {
				try {
					service.deleteWhUserType(id);					
					String message = "User deleted sucessfully !";
					model.addAttribute("message", message);
					log.info(message);
				} catch (Exception e) {
					model.addAttribute("message", "Unable to delete, It is used by sale/purchase Order");
					e.printStackTrace();
					log.error("Exception : {}",e);
				}

				
				// After deleting one record show remaining records
			} else {
				String errorMessage = "User Type Not Existed!";
				model.addAttribute("errorMessage", errorMessage);
				log.warn(errorMessage);
			}
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}

		model.addAttribute("page", service.getAllWhUserType(PageRequest.of(page, 5)));
		return "WhUserTypeData";
	}

	// 5. show edit page with the data
	/*
	 * when user click on the edit button on the ui read the id from path variable
	 * and get the data based on the id if existed then send it the
	 * WhUserTypeEdit.html page else go to the same WhUserTypeData.html page
	 */
	@GetMapping("/edit/{id}")
	public String showWhUserTypeEdit(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@PathVariable Integer id,
			Model model)
	{
		log.info("Entered into showWhUserTypeEdit() method");
		String page = null;
		try {
			Optional<WhUserType> opt = service.getOneWhUserType(id);
			if (opt.isPresent()) {
				// Form Backing object
				model.addAttribute("whUserType", opt.get());
				log.info("About to show WhUserTypeEdit page");
				page = "WhUserTypeEdit";
			} else {
				log.warn("WhUserType not existed");
				model.addAttribute("page", service.getAllWhUserType(pageable));
				page = "WhUserTypeData";
			}
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		return page;
	}

	// 6. perform Update operation
	/*
	 * when request will come for the url:/WhUserType/update read data from
	 * modelAttribute and update it to the dataBase
	 */
	@PostMapping("/update")
	public String updateWhUserType(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@ModelAttribute WhUserType whUserType, Model model) {
		log.info("Entered into updateWhUserType() method");
		try {
			service.updateWhUserType(whUserType);
			String message = "User '"+whUserType.getUserCode()+"' Updated Sucessfully!";
			log.debug(message);
			model.addAttribute("message", message);
			// After deleting one record show remaining records
			model.addAttribute("page", service.getAllWhUserType(pageable));
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		return "WhUserTypeData";
	}
	// 7. show One record 
		@GetMapping("/view/{id}")
		public String getOneWhUserType(
				@PageableDefault(page=0, size=5) Pageable pageable,
				@PathVariable Integer id,
				Model model) 
		{
			log.info("Entered into getOneWhUserType() method");
			try {
				Optional<WhUserType> opt = service.getOneWhUserType(id);
				if(opt.isPresent()) {
					model.addAttribute("user", opt.get());
					log.debug("User Type fetched and about to show");
					return "WhUserTypeViewOne";
				}
				String message = "User Not Existed!";
				log.warn(message);
				model.addAttribute("errorMessage", message);
				model.addAttribute("userList", service.getAllWhUserType(pageable));
			} catch (Exception e) {
				log.error("Exception : "+e);
				e.printStackTrace();
			}
			return "WhUserTypeData";
		}
	
	// 8. generate Excel Sheet of WhUserType Data
	@GetMapping("/excel")
	public ModelAndView exportAllWhUserTypeToExcel() {
		log.info("Entered into exportAllWhUserTypeToExcel() method");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new WhUserTypeExcelView());
		try {
			
			// sending data to View Class
			List<WhUserType> whUserTypeList = service.getAllWhUserType();
			modelAndView.addObject("whUserTypeList", whUserTypeList);
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info("About to export all WhUserType");
		return modelAndView;
	}
	
	// 9. generate Excel Sheet of one row WhUserType Data
	@GetMapping("/excel/{id}")
	public ModelAndView exportOneWhUserTypeToExcel(@PathVariable Integer id) {
		log.info("Enterd into exportOneWhUserTypeToExcel() method");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new WhUserTypeExcelView());
		
		try {
			// sending data to View Class
			List<WhUserType> whUserTypeList = Arrays.asList();
			Optional<WhUserType> opt = service.getOneWhUserType(id);
			if(opt.isPresent()) {
				whUserTypeList = Arrays.asList(opt.get());
			}
			modelAndView.addObject("whUserTypeList", whUserTypeList);
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info("About to export WhUserType");
		return modelAndView;
	}
	
	//Export all User to pdf
	@GetMapping("/pdf")
	public ModelAndView exportAllUserToPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new WhUserTypePdfView());
		//getting data from Service
		List<WhUserType> userList = service.getAllWhUserType();
		m.addObject("userList", userList);
		return m;
	}
	//Export one User to pdf
	@GetMapping("/pdf/{id}")
	public ModelAndView exportOneUserToPdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new WhUserTypePdfView());
		//getting data from Service
		Optional<WhUserType> userOpt = service.getOneWhUserType(id);
		m.addObject("userList", Arrays.asList(userOpt.get()));
		return m;
	}
	
	// Chart Generation
	@GetMapping("/chart")
	public String generateChart() {
		String location = contxt.getRealPath("/");
		List<Object[]> userTypeCountList = service.getWhUserTypeCount();
		util.generatePieChart(location, "/WhUserTypePieChart.png", userTypeCountList);
		util.generateBarChart(location, "/WhUserTypeBarChart.png", userTypeCountList, "User Type");
		return "WhUserTypeChart";
	}
	
	// ajax validation for userEmail in Register Page
	@GetMapping("/validateemail")
	public @ResponseBody String validateWhUserEmail(@RequestParam String userEmail) {
		log.info("Entered into validateWhUserEmail() method");
		String message = "";
		try {
			if(service.isWhUserEmailExist(userEmail)) {
				message = "Email Already Existed !";
			}
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info("WhUser Email Validated");
		return message;
	}
	
	// ajax validation for userEmail in Edit Page
	@GetMapping("/validateemailforedit")
	public @ResponseBody String validateWhUserEmailForEdit(@RequestParam String userEmail, @RequestParam Integer id) {
		log.info("Entered into validateWhUserEmailForEdit() method");
		String message = "";
		try {
			if(service.isWhUserEmailExistForEdit(userEmail, id)) {
				message = "Email Already Existed !";
			}
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info("WhUser Email validated for Edit");
		return message;
	}
	
}
