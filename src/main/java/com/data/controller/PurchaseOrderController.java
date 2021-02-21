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

import com.data.model.PurchaseDtls;
import com.data.model.PurchaseOrder;
import com.data.service.IPartService;
import com.data.service.IPurchaseOrderService;
import com.data.service.IShipmentTypeService;
import com.data.service.IWhUserTypeService;
import com.data.util.ChartGenerationUtil;
import com.data.view.PurchaseOrderExcelView;
import com.data.view.PurchaseOrderPdfView;

@Controller
@RequestMapping("/purchaseorder")
public class PurchaseOrderController {
	Logger log = LoggerFactory.getLogger(PurchaseOrderController.class);
	
	@Autowired
	private IPurchaseOrderService purchaseOrderService;
	
	
	@Autowired
	private IShipmentTypeService shipmentService;
	
	@Autowired
	private IWhUserTypeService whUserService;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private ChartGenerationUtil util;
	
	private void shipmentCodeDropdown(Model model) {
		model.addAttribute("shipmentList", shipmentService.getShipmentIdAndCode());
	}
	
	private void vendorDropdown(Model model) {
		model.addAttribute("vendorList", whUserService.getUserIdAndUserCode("Vendor"));
	}

	//show purchase Order Registration page
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("purchaseOrder", new PurchaseOrder());
		shipmentCodeDropdown(model);
		vendorDropdown(model);
		return "PurchaseOrderRegister";
	}
	
	//Save purchase Order Data
	@PostMapping("/save")
	public String savePurchaseOrder(
			@ModelAttribute PurchaseOrder purchaseOrder,
			Model model) 
	{
		String message = "";
		Integer id = purchaseOrderService.savePurchaseOrder(purchaseOrder);
		message = "Order Placed with Order Id : "+id;
		model.addAttribute("message", message);
		model.addAttribute("purchaseOrder", new PurchaseOrder());
		shipmentCodeDropdown(model);
		vendorDropdown(model);
		return "PurchaseOrderRegister";
	}
	
	// Get all Purchase Order
	@GetMapping("/all")
	public String getAllPurchaseOrder(
			@PageableDefault(page=0, size=5) Pageable pageable,
			Model model) 
	{
		model.addAttribute("page", purchaseOrderService.getAllPurchaseOrder(pageable));
		return "PurchaseOrderData";
	}
	
	// Delete One Purchase Order
	@GetMapping("/delete/{id}/{page}")
	public String deleteOnePurchaseOrder(
			@PathVariable Integer id,
			@PathVariable Integer page,
			Model model)
	{
		if(purchaseOrderService.isPurchaseOrderExist(id)) {
			purchaseOrderService.deleteOnePurchaseOrder(id);
			String message = "Order '"+id+"' Deleted Successfully ";
			model.addAttribute("message", message);
		}else {
			String errorMessage = "Order '"+id+"' Not Existed ! ";
			model.addAttribute("errorMessage", errorMessage);			
		}
		model.addAttribute("page", purchaseOrderService.getAllPurchaseOrder(PageRequest.of(page, 5)));
		return "PurchaseOrderData";
	}
	
	// View One Purchase Order
	@GetMapping("/view/{id}")
	public String viewOnePurchaseOrder(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@PathVariable Integer id,
			Model model) 
	{
		if(purchaseOrderService.isPurchaseOrderExist(id)) {
			Optional<PurchaseOrder> purchaseOrderOpt = purchaseOrderService.getOnePurchaseOrder(id);
			model.addAttribute("purchaseOrder", purchaseOrderOpt.get());
			return "PurchaseOrderViewOne";
		}else {
			model.addAttribute("page", purchaseOrderService.getAllPurchaseOrder(pageable));
			String errorMessage = "Order '"+id+"' Not Existed !";
			model.addAttribute("errorMessage", errorMessage);
			return "PurchaseOrderData";			
		}
	}
	
	//Show PurchaseOrderEdit page
	@GetMapping("/edit/{id}")
	public String showPurchaseOrderEdit(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@PathVariable Integer id,
			Model model)
	{
		if(purchaseOrderService.isPurchaseOrderExist(id)) {
			Optional<PurchaseOrder> purchaseOrderOpt = purchaseOrderService.getOnePurchaseOrder(id);
			model.addAttribute("purchaseOrder", purchaseOrderOpt.get());
			shipmentCodeDropdown(model);
			vendorDropdown(model);
			return "PurchaseOrderEdit";
		}else {
			model.addAttribute("page", purchaseOrderService.getAllPurchaseOrder(pageable));
			String errorMessage = "Order '"+id+"' Not Existed !";
			model.addAttribute("errorMessage", errorMessage);
			return "PurchaseOrderData";			
		}
	}
	
	//Update the Purchase order
	@PostMapping("/update")
	public String updatePurchaseOrder(
			@PageableDefault(page=0, size=5) Pageable pageable,
			@ModelAttribute PurchaseOrder purchaseOrder,
			Model model)
	{
		String message = "";
		try {
			purchaseOrderService.updatePurchaseOrder(purchaseOrder);
			message = "Order '"+purchaseOrder.getOrderCode()+"' Updated Successfully";			
		} catch (Exception e) {
			message = "Unable to Update your Purchase Order due to some server Error";
		}
		model.addAttribute("page", purchaseOrderService.getAllPurchaseOrder(pageable));
		model.addAttribute("message", message);
		return "PurchaseOrderData";	
	}
	
	//Export All in Excel
	@GetMapping("/excel")
	public ModelAndView exportAllPurchaseOrderToExcel() {
		ModelAndView m = new ModelAndView();
		m.setView(new PurchaseOrderExcelView());
		List<PurchaseOrder> purchaseOrderList = purchaseOrderService.getAllPurchaseOrder();
		m.addObject("purchaseOrderList", purchaseOrderList);
		return m;
	}
	//Export One in Excel
	@GetMapping("/excel/{id}")
	public ModelAndView exportOnePurchaseOrderToExcel(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new PurchaseOrderExcelView());
		List<PurchaseOrder> purchaseOrderList = Arrays.asList(purchaseOrderService.getOnePurchaseOrder(id).get());
		m.addObject("purchaseOrderList", purchaseOrderList);
		return m;
	}
	//Export All in PDF
	@GetMapping("/pdf")
	public ModelAndView exportAllPurchaseOrderToPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new PurchaseOrderPdfView());
		List<PurchaseOrder> purchaseOrderList = purchaseOrderService.getAllPurchaseOrder();
		m.addObject("purchaseOrderList", purchaseOrderList);
		return m;
	}
	//Export One in Excel
	@GetMapping("/pdf/{id}")
	public ModelAndView exportOnePurchaseOrderToPdf(@PathVariable Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new PurchaseOrderPdfView());
		List<PurchaseOrder> purchaseOrderList = Arrays.asList(purchaseOrderService.getOnePurchaseOrder(id).get());
		m.addObject("purchaseOrderList", purchaseOrderList);
		return m;
	}
	
	//Generate Chart 
	@GetMapping("/chart")
	public String generateChart() {
		String location = context.getRealPath("/");
		List<Object[]> shipmentCodeCountList = purchaseOrderService.getShipmentCodeCountList();
		util.generatePieChart(location, "/PurchaseOrderPieChart.png", shipmentCodeCountList);
		util.generateBarChart(location, "/PurchaseOrderBarChart.png", shipmentCodeCountList, "Shipment Code");
		
		return "PurchaseOrderChart";
	}
	
	//AJAX Validation for Order Code
	@GetMapping("/validateordercode")
	public @ResponseBody String validateOrderCode(@RequestParam String orderCode) {
		String message = "";
		if(purchaseOrderService.getOrderCodeCount(orderCode) >= 1) {
			message = "Order Code Already Existed !";
		}
		return message;
	}
	
	/*************************************************************************************/
	/*************                Screen #2 Codding                              *********/
	/*************************************************************************************/
	
	@Autowired
	private IPartService partService;
	
	private void partCodeDropdown(Model model) {
		model.addAttribute("partList", partService.getPartIdAndCode());
	}
	
	//show PurchaseDtl Page
	@GetMapping("/partdtl/{id}")
	public String showPurchaseDtl(
			@PathVariable Integer id,
			Model model) 
	{
		String page = "";
		String message = "";
		try {
			//form backing object
			PurchaseOrder po = purchaseOrderService.getOnePurchaseOrder(id).get();
			model.addAttribute("purchaseOrder", po);
			// part Code dropdown
			partCodeDropdown(model);
			//part form backing 
			PurchaseDtls purchaseDtl = new PurchaseDtls();
			purchaseDtl.setPurchaseOrder(po);
			model.addAttribute("purchaseDtl", purchaseDtl);
			
			//showing added part
			List<PurchaseDtls> purchaseDtlList = purchaseOrderService.getPurchaseDtlByPurchaseOrderId(id);
			model.addAttribute("purchaseDtlList", purchaseDtlList);
			model.addAttribute("purchaseDtlListSize", purchaseDtlList.size());
			
			
			page = "PurchaseDtl";
		} catch (Exception e) {
			message = "Unable to add Parts Due to some Server Error";
			page = "PurchaseOrderData";
			e.printStackTrace();
		}
		model.addAttribute("message", message);
		return page;
	}
	
	//Addint part into cart
	@PostMapping("/addpart")
	public String addPart(@ModelAttribute PurchaseDtls purchaseDtls) {
		Integer poId = purchaseDtls.getPurchaseOrder().getId();
		try {
			purchaseOrderService.addPart(purchaseDtls);	
			if(purchaseOrderService.getCountPurchaseDtlByPurchaseOrderId(poId) == 1) {
				purchaseOrderService.updatePurchaseorderStatus(poId, "PICKING");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : {}",e);
		}
		return "redirect:partdtl/"+ poId;
	}
	
	//removing part from cart
	@GetMapping("/removepart/{id}/{poId}")
	public String removePart(
				@PathVariable Integer id,
				@PathVariable Integer poId
			)
	{
		purchaseOrderService.removeOnePart(id);
		if(purchaseOrderService.getCountPurchaseDtlByPurchaseOrderId(poId) == 0) {
			purchaseOrderService.updatePurchaseorderStatus(poId, "OPEN");
		}
		return "redirect:../../partdtl/"+poId;
	}
	
	//Confirming Order
	@GetMapping("/confirmorder/{poId}")
	public String confirmOrder(@PathVariable Integer poId){
		purchaseOrderService.updatePurchaseorderStatus(poId, "ORDERED");		
		return "redirect:../partdtl/"+poId;
	}
}
