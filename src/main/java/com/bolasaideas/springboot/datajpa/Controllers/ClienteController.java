package com.bolasaideas.springboot.datajpa.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolasaideas.springboot.datajpa.Model.Domain.Cliente;

import com.bolasaideas.springboot.datajpa.Services.IClienteService;
import com.bolasaideas.springboot.datajpa.Util.paginator.PageRenders;

@Controller
public class ClienteController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	@Autowired

	private IClienteService clienteServices;

	private final Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

	@Secured("ROLE_USER")
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = clienteServices.findOne(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en le base de datos");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Detalle cliente ".concat(cliente.getNombre()));

		return "ver";
	}

	@RequestMapping(value = { "/listar", "/" }, method = RequestMethod.GET)
	public String Listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request) {

		// igual que el del parametro
		Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			log.info("Hola uausrio autenticado tu username es: ".concat(authentication.getName()));

			if (hasRoles("ROLE_ADMIN")) {
				log.info("Hola " + authentication.getName() + ",  tienes acceso");
			} else {
				log.info("Hola " + authentication.getName() + ", no tienes acceso");
			}

		}
		// Envuelve el request y valida el rol

		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request,
				"ROLE_");

		if (securityContext.isUserInRole("ADMIN")) {
			log.info("USANDO SCHR Hola " + authentication.getName() + ",  tienes acceso");
		}
		if (request.isUserInRole("ROLE_ADMIN")) {
			log.info("USANDO httpServlet Hola " + authentication.getName() + ",  tienes acceso");
		}

		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Cliente> cleintes = clienteServices.findAll(pageRequest);

		PageRenders<Cliente> pageRender = new PageRenders<>("/listar", cleintes);

		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", cleintes);
		model.addAttribute("page", pageRender);

		return "listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form")
	public String addCliente(Map<String, Object> model) {

		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario Cliente");

		return "form";
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String procesarForm(@Valid @ModelAttribute(name = "cliente") Cliente cliente, BindingResult result,
			Model model, RedirectAttributes flash, @RequestParam("file") MultipartFile foto) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario Cliente");
			return "form";
		}
		if (!foto.isEmpty()) {

			// String routePath= "C://Temp//upload";

			String uniqueFilmane = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();

			Path routePath = Paths.get("upload").resolve(uniqueFilmane);
			Path rootAbs = routePath.toAbsolutePath();
			log.info("routePath " + uniqueFilmane);
			log.info("rootAbs " + uniqueFilmane);

			try {
				// byte[] bytes= foto.getBytes();
				// Path rutaCompleta= Paths.get(routePath+"/"+foto.getOriginalFilename());
				// Files.write(rutaCompleta, bytes);

				Files.copy(foto.getInputStream(), rootAbs);

				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilmane + "'");
				cliente.setFoto(uniqueFilmane);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		String mensajeFlash = cliente.getId() != null ? "Cliente Editado con exito" : "Cliente Creado con exito";
		clienteServices.save(cliente);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{id}")
	public String editarCliente(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {
		if (id > 0) {
			Cliente cliente = clienteServices.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El Id del cliente no existe en la base de datos");
				return "redirect:/listar";
			}
			model.put("cliente", cliente);
			model.put("titulo", "Formulario editar Cliente");
			return "form";
		} else {
			flash.addFlashAttribute("error", "El Id del cliente no puede ser 0!");
			return "redirect:/listar";
		}

	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			clienteServices.delete(id);
		}
		flash.addFlashAttribute("success", "Cliente eliminado con exito");
		return "redirect:/listar";
	}

	private boolean hasRoles(String rol) {

		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return false;
		}

		Authentication auth = context.getAuthentication();
		if (auth == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		/*
		 * for(GrantedAuthority authority : authorities) {
		 * 
		 * if(rol.equals(authority.getAuthority())) {
		 * log.info("Hola "+auth.getName()+", tu rol es: "+rol);
		 * 
		 * return true; } }
		 * 
		 * return false;
		 */

		return authorities.contains(new SimpleGrantedAuthority(rol));
	}
}
