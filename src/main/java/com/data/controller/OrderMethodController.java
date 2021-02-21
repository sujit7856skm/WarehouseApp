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

import com.data.model.OrderMethod;
import com.data.service.IOrderMethodService;
import com.data.util.ChartGenerationUtil;
import com.data.view.OrderMethodExcelView;
import com.data.view.OrderMethodPdfView;

@Controller
@RequestMapping("/ordermethod")
public class OrderMethodController {
	
	@Autowired
	private IOrderMethodService service;
	
	@Autowired
	private ServletContext context;
	@Autowired
	private ChartGenerationUtil util;
	
	Logger log = LoggerFactory.getLogger(OrderMethodController.class);
	
	// 1. show Register page
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("orderMethod", new OrderMethod());
		return "OrderMethodRegister";
	}
	// 2. save data 
	@PostMapping("/save")
	public String save(
			@ModelAttribute OrderMethod orderMethod, 
			Model model
		) 
	{
		log.info("Entered into Save method and about to call saveOrderMethod (service) method");
		try {
			Integer id = service.saveOrderMethod(orderMethod);
			String message = "Order Method Created with Id => "+id;
			log.debug(message);
			model.addAttribute("message", message);
			
			// To clear the form data (Form backing Object)
			model.addAttribute("orderMethod", new OrderMethod());
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info("going to UI page");
		return "OrderMethodRegister";
	}
	
	// 3. Show all data
	@GetMapping("/all")
	public String showAll(
			@PageableDefault(page=0, size=5) Pageable pageable,
			Model model)
	{
		log.info("Entered into showAll() method and About to call getAllOrderMethod() (service) method");
		try {
			model.addAttribute("page", service.getAllOrderMethod(pageable));
			log.debug("Fetched all Data and set to model memory");
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info("Going to ui page");
		return "OrderMethodData";
	}
	// 4. delete One
	@GetMapping("/delete/{id}/{page}")
	public String removeOne(
			@PathVariable Integer id,
			@PathVariable Integer page,
			Model model) 
	{
		String message = null;
		log.info("Entered into deleteOne() method and about to call isOrderMethodExist(id) service method");
		try {
			if(service.isOrderMethodExist(id)) {
				log.debug("OrderMethod Existed Now it going to call deleteOneOrderMehthod(id) service method");
				service.deleteOneOrderMethod(id);
				message = "Order Method Deleted Successfully!";
				log.debug(message);
				model.addAttribute("message", message);
			}else {
				message = "Order Method Not Existed!";
				log.warn(message);
				model.addAttribute("errorMessage", message);
			}
			model.addAttribute("page", service.getAllOrderMethod(PageRequest.of(page, 5)));
		} catch (Exception e) {
			log.error("Exeption : "+e);
			e.printStackTrace();
		}
		log.info("Going to OrderMethodData Page in UI");
		return "OrderMethodData";
	}
	
	// 5. show edit page
	@GetMapping("/edit/{id}")
	public String showEdit(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@PathVariable Integer id,
			Model model) 
	{
		try {
			log.info("Entered into showEdit(id) method and About to call getOneOrderMethod(id) service method");
			Optional<OrderMethod> opt = service.getOneOrderMethod(id);
			if(opt.isPresent()) {
				log.debug("Data fetched and about to show in ui using Model memory");
				model.addAttribute("orderMethod", opt.get());
				return "OrderMethodEdit";
			}
			String message = "Order Method Not Existed!";
			log.warn(message);
			model.addAttribute("errorMessag"
					+ "e", message);
			
			model.addAttribute("page", service.getAllOrderMethod(pageable));
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();		
		}
		log.info("About to show OrderMethodData page");
		return "OrderMethodData";
	}
	
	// 6. update data
	@PostMapping("/update")
	public String update(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@ModelAttribute OrderMethod orderMethod,
			Model model) 
	{
		log.info("Entered into update(orderMethod) method and About to call updateOrderMethod() service method");
		try {
			service.updateOrderMethod(orderMethod);
			String message = "Order Method Updated Successfully!";
			log.info(message);
			model.addAttribute("message", message);
			log.info("About to show OrderMethodData.html page");
			model.addAttribute("page", service.getAllOrderMethod(pageable));
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		
		return "OrderMethodData";
	}
	
	// 7. show One record 
	@GetMapping("/view/{id}")
	public String getOne(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@PathVariable Integer id,
			Model model) 
	{
		log.info("Entered into getOne(id) method and about to check orderMothod existed by given id or not");
		try {
			Optional<OrderMethod> opt = service.getOneOrderMethod(id);
			if(opt.isPresent()) {
				log.info("Data Existed by given id");
				model.addAttribute("obj", opt.get());
				return "OrderMethodViewOne";
			}
			String message = "Order Method Not Existed!";
			log.warn(message);
			model.addAttribute("errorMessage", message);
			model.addAttribute("page", service.getAllOrderMethod(pageable));
		} catch (Exception e) {
			log.error("Exception : "+ e);
			e.printStackTrace();
		}
		log.info("About to show OrderMethodData page");
		return "OrderMethodData";
	}
	
	//Export All into Excel Format
	@GetMapping("/excel")
	public ModelAndView exportAllOrderMethodToExcel() {
		log.info("Entered Into exportAllOrderMethod()");
		ModelAndView m = new ModelAndView();
		m.setView(new OrderMethodExcelView());
		try {
			log.info("About to call getAllOrderMethod() service method");
			List<OrderMethod> orderMethodList = service.getAllOrderMethod();
			m.addObject("orderMethodList", orderMethodList);
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info("About to export all OrderMethod Data");
		return m;
	}
	//Export one into Excel Format
	@GetMapping("/excel/{id}")
	public ModelAndView exportOneOrderMethodToExcel(@PathVariable Integer id) {
		log.info("Entered Into exportOneOrderMethod()");
		ModelAndView m = new ModelAndView();
		m.setView(new OrderMethodExcelView());
		try {
			log.info("About to call getOneOrderMethod(id) service method");
			List<OrderMethod> orderMethodList = Arrays.asList(service.getOneOrderMethod(id).get());
			m.addObject("orderMethodList", orderMethodList);
		} catch (Exception e) {
			log.error("Exception : "+ e);
			e.printStackTrace();
		}
		
		log.info("About to export all OrderMethod Data");
		return m;
	}
	
	
	// Export all to pdf
	@GetMapping("/pdf")
	public ModelAndView exportAllOrderMethodToPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new OrderMethodPdfView());
		
		//Getting data from service
		List<OrderMethod> orderMethodList = service.getAllOrderMethod();
		m.addObject("orderMethodList", orderMethodList);
		return m;
	}
	
	// Export One to pdf
	@GetMapping("/pdf/{id}")
	public ModelAndView exportOneOrderMethodToPdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new OrderMethodPdfView());
		
		//Getting data from service
		Optional<OrderMethod> orderMethodList = service.getOneOrderMethod(id);
		m.addObject("orderMethodList", Arrays.asList(orderMethodList.get()));
		return m;
	}
	
	//Chart Generation 
	@GetMapping("/chart")
	public String generateChart() {
		String location = context.getRealPath("/");
		List<Object[]> modeCountList = service.getOrderModeCount();
		util.generatePieChart(location, "/OrderMethodPieChart.png", modeCountList);
		util.generateBarChart(location, "/OrderMethodBarChart.png", modeCountList, "Order Mode");
		return "OrderMehtodChart";
	}
	
	// AJAX Validation for orderMethodCode
	@GetMapping("/validatecode")
	public @ResponseBody String validateOrderCode(@RequestParam String orderCode) {
		log.info("Entered into validateOrderCode(orderCode) method");
		String message = "";
		try {
			if(service.isOrderMethodCodeExist(orderCode)) {
				message = "Order Code Already Existed !";
			}
		} catch (Exception e) {
			log.error("Exception : "+e);
			e.printStackTrace();
		}
		log.info(message);
		return message;
	}

}
