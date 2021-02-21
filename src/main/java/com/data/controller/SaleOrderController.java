package com.data.controller;

import java.util.Arrays;

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

import com.data.model.PurchaseOrder;
import com.data.model.SaleOrder;
import com.data.service.ISaleOrderService;
import com.data.service.IShipmentTypeService;
import com.data.service.IWhUserTypeService;
import com.data.util.ChartGenerationUtil;
import com.data.view.SaleOrderExcelView;
import com.data.view.SaleOrderPdfView;

@Controller
@RequestMapping("/saleorder")
public class SaleOrderController {
	Logger log = LoggerFactory.getLogger(SaleOrderController.class);
	
	@Autowired
	private ISaleOrderService saleOrderService;
	
	@Autowired
	private IShipmentTypeService shipmentTypeService;
	
	@Autowired
	private IWhUserTypeService whUserTypeService;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private ChartGenerationUtil chartUtil;
	
	private void shipmentcodeDropdown(Model model) {
		model.addAttribute("shipmentList", shipmentTypeService.getShipmentIdAndCode());
	}
	
	private void customerDropdown(Model model) {
		model.addAttribute("customerList", whUserTypeService.getUserIdAndUserCode("Customer"));
	}
	
	//show saleOrder Registration page
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("saleOrder", new SaleOrder());
		shipmentcodeDropdown(model);
		customerDropdown(model);
		return "SaleOrderRegister";
	}
	
	//Save sale Order data to Database
	@PostMapping("/save")
	public String saveSaleOrder(
			@ModelAttribute SaleOrder saleOrder,
			Model model) 
	{
		log.info("Entered into the Sve SaleOrder() method and about to call the service method");
		String message = "";
		try {
			Integer id = saleOrderService.saveSaleOrder(saleOrder);
			message = "Order '"+saleOrder.getOrderCode()+"' Successfully saved with id : "+id;
			log.debug(message);
		} catch (Exception e) {
			message = "Failed to save Order :"+saleOrder.getOrderCode();
			log.error("Exception : "+e);
			model.addAttribute("errorMessage", message);
			e.printStackTrace();
		}
		model.addAttribute("saleOrder", new SaleOrder());
		model.addAttribute("message", message);
		shipmentcodeDropdown(model);
		customerDropdown(model);
		log.info("About to go back to the Ui (SaleOrderRegister) page with response ");
		return "SaleOrderRegister";
	}
	
	// Get All Sale Order
	@GetMapping("/all")
	public String getAllSaleOrder(
			@PageableDefault(page=0, size=5) Pageable pageable,
			Model model)
	{
		log.info("Entered into getAllSaleOrder() method and about to call service method");
		String message = "";
		try {
			model.addAttribute("page", saleOrderService.getAllSaleOrder(pageable));
			log.debug("Data Fetched and About to show data in ui (SaleOrderData)");
		} catch (Exception e) {
			message = "Unable to fetch the data due to some Server Error";
			model.addAttribute("errorMessage", message);
			log.error("Exception : {}",e);
			e.printStackTrace();
		}
		return "SaleOrderData";
	}
	
	// View One Sale Order
	@GetMapping("/view/{id}")
	public String viewOneSaleOrder(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@PathVariable Integer id,
			Model model)
	{
		log.info("Entered into viewOneSaleOrder() method and about to call service method");
		String message = "";
		try {
			if(saleOrderService.isSaleOrderExist(id)) {
				model.addAttribute("saleOrder", saleOrderService.getOneSaleOrder(id).get());
				log.debug("Data Fetched and about to show data in Ui ");
				return "SaleOrderViewOne";
			}else{
				message = "Sale Order '"+id+"' Not Existed !";
				log.info(message);
			}
		} catch (Exception e) {
			message = "Unable to Fetch the data due to some server Error !";
			log.error("Exception : {}",e);
			e.printStackTrace();
		}
		model.addAttribute("page", saleOrderService.getAllSaleOrder(pageable));
		model.addAttribute("errorMessage", message);
		return "SaleOrderData";
	}
	
	// Show SaleOrderEdit Page
	@GetMapping("/edit/{id}")
	public String showSaleOrderEdit(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@PathVariable Integer id, 
			Model model) 
	{
		log.info("Entered into showSaleOrderEdit() method and about to chaeck sale order exist or not");
		String message = "";
		try {
			if(saleOrderService.isSaleOrderExist(id)) {
				model.addAttribute("saleOrder", saleOrderService.getOneSaleOrder(id).get());
				log.debug("Data fetched and about to show in ui page");
				shipmentcodeDropdown(model);
				customerDropdown(model);
				return "SaleOrderEdit";
			}else{
				message = "Sale Order '"+id+"' Not Existed !";
			}
		} catch (Exception e) {
			log.error("Exception : {}",e);
			message = "Unable to Fetch the data due to some server Error !";
			e.printStackTrace();
		}
		log.info(message);
		model.addAttribute("page", saleOrderService.getAllSaleOrder(pageable));
		model.addAttribute("errorMessage", message);
		return "SaleOrderData";
	}
	
	//Update SaleOrder
	@PostMapping("/update")
	public String updateSaleOrder(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@ModelAttribute SaleOrder saleOrder, 
			Model model) 
	{
		log.info("Entered into updateSaleOrder() method and about to call service method to update");
		String message = "";
		try {
			saleOrderService.updateOneSaleOrder(saleOrder);
			message = "Sale Order '"+saleOrder.getOrderCode()+"' Updated Successfully";
			log.debug(message);
		} catch (Exception e) {
			message = "Failed to Update Sale Order due to some server Error";
			log.error("Exception : {}",e);
		}
		model.addAttribute("page", saleOrderService.getAllSaleOrder(pageable));
		model.addAttribute("message", message);
		return "SaleOrderData";
	}
	
	// Delete One SaleOrder
	@GetMapping("/delete/{id}/{page}")
	public String deleteOneSaleOrder(
			@PathVariable Integer id,
			@PathVariable Integer page,
			Model model)
	{
	
		log.info("Entered into delteOneSaleOrder() method");
		String message = "";
		try {
			if(saleOrderService.isSaleOrderExist(id)) {
				saleOrderService.deleteOneSaleOrder(id);
				message = "Order '"+id+"' deleted Successfully ";
			}else {
				message = "Order '"+id+"' Not Existed !";				
			}
		} catch (Exception e) {
			message = "Unable to delete due to some Server Error !";
			log.error("Exception : {}",e);
			e.printStackTrace();
		}
		model.addAttribute("message", message);
		
		model.addAttribute("page", saleOrderService.getAllSaleOrder(PageRequest.of(page, 5)));
		log.info(message);
		return "SaleOrderData";
	}

	//Export All Sale Order to Excel
	@GetMapping("/excel")
	public ModelAndView exportAllSaleOrderToExcel() {
		ModelAndView m = new ModelAndView();
		m.setView(new SaleOrderExcelView());
		
		// Getting Data from service method and setting to view object
		m.addObject("saleOrderList", saleOrderService.getAllSaleOrder());
		
		return m;
	}
	//Export One Sale Order to Excel
	@GetMapping("/excel/{id}")
	public ModelAndView exportOneSaleOrderToExcel(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new SaleOrderExcelView());
		
		// Getting Oner row Data from service method and setting to view object
		m.addObject("saleOrderList", Arrays.asList(saleOrderService.getOneSaleOrder(id).get()));
		
		return m;
	}
	
	//Export All SaleOrder to PDF
	@GetMapping("/pdf")
	public ModelAndView exportAllSaleOrderToPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new SaleOrderPdfView());
		
		// Getting Data from service method and setting to view object
		m.addObject("saleOrderList", saleOrderService.getAllSaleOrder());
		
		return m;
	}
	//Export One Sale Order to PDF
	@GetMapping("/pdf/{id}")
	public ModelAndView exportOneSaleOrderToPdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new SaleOrderPdfView());
		
		// Getting Oner row Data from service method and setting to view object
		m.addObject("saleOrderList", Arrays.asList(saleOrderService.getOneSaleOrder(id).get()));
		
		return m;
	}
	
	//Chart Generation
	@GetMapping("/chart")
	public String generateChart() {
		
		String location = context.getRealPath("/");
		chartUtil.generatePieChart(
					location,
					"/SaleOrderPieChart.png",
					saleOrderService.getShipmentCodeCountList()
					);
		chartUtil.generateBarChart(
					location,
					"/SaleOrderBarChart.png",
					saleOrderService.getShipmentCodeCountList(),
					"Shipment Code"
					);
		
		return "SaleOrderChart";
		
		
		
		
	}
	
	// AJAX Validation for orderCode
	@GetMapping("/validateordercode")
	public @ResponseBody String validateOrderCode(@RequestParam String orderCode) {
		String message = "";
		if(saleOrderService.getOrderCodeCount(orderCode) >= 1) {
			message = "Order Code Already Existed !";
		}
		return message;
	}
	
	/**********************************************************************************/
	/************                       Screen #2                        **************/
	/**********************************************************************************/
	
	@GetMapping("/cart/{id}")
	public String showSaleDetails(@PathVariable Integer id, Model model) {
		String page = "";
			try {
				SaleOrder saleOrder = saleOrderService.getOneSaleOrder(id).get();
				model.addAttribute("saleOrder", saleOrder);
				
				page = "SaleDtl";
			} catch (Exception e) {
				e.printStackTrace();
			}
		return page;
	}
	
}
