package com.bolasaideas.springboot.datajpa.Controllers;

 
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolasaideas.springboot.datajpa.Model.Domain.Cliente;
import com.bolasaideas.springboot.datajpa.Model.Domain.Factura;
import com.bolasaideas.springboot.datajpa.Model.Domain.ItemFactura;
import com.bolasaideas.springboot.datajpa.Model.Domain.Producto;
import com.bolasaideas.springboot.datajpa.Services.IClienteService;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController  {
	
	
	private final Logger log= LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		//Factura factura= clienteService.findFacturaByID(id);
		Factura factura= clienteService.fetchFacturaById(id);
		if(factura==null) {
			flash.addFlashAttribute("error", "La factura no exixte en la base de datos");
			return"redirect:/listar";
		}
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));
		
		return "factura/ver";
	}
	@GetMapping("/form/{clienteId}")
	public String creat(@PathVariable(name = "clienteId")Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Cliente cliente= clienteService.findOne(id);
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no exixte en la base de datos");
			return "redirect:/listar";
		}
		Factura factura= new Factura();
		factura.setCliente(cliente);
		model.put("factura", factura);
		model.put("titulo", "Crear factura");
		return"factura/form";
	}
	@GetMapping(value = "/cargar-productos/{term}",produces = {"application/json"})
	public @ResponseBody List<Producto> cargarProducto(@PathVariable String term){
		return clienteService.findByNombre(term);
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Factura factura,
			BindingResult result,
			Model model,
			@RequestParam (name="item_id[]", required = false )Long[] itemID,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
			RedirectAttributes flash,
			SessionStatus status
			){
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Crear factura");
			return"factura/form";
		}
		if(itemID==null || itemID.length==0) {
			model.addAttribute("titulo", "Crear factura");
			model.addAttribute("error", "La factura no puede no tener lineas");
			return"factura/form";
		}
		for (int i= 0 ; i<itemID.length;i++) {
		 
			Producto p= clienteService.findProductoById(itemID[i]);
			ItemFactura linea= new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(p);
			factura.addItemFactura(linea);
			log.info("ID: "+itemID[i].toString()+" , cantidad: "+ cantidad[i]);
		}
		
		clienteService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("sucess", "Factura creada con exito");
		
		return"redirect:/ver/"+factura.getCliente().getId();
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(name = "id") Long id, RedirectAttributes fash) {
		Factura factura= clienteService.findFacturaByID(id);
		if(factura!=null) {
			clienteService.deleteFactura(id);
			fash.addFlashAttribute("success", "Factura eliminada con exito!!");
			return "redirect:/ver/"+factura.getCliente().getId();
		}
		fash.addFlashAttribute("error", "Factura n exixte en la base de datos");
		return "redirect:/listar";
	}
	
	
}
