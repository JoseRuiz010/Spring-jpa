package com.bolasaideas.springboot.datajpa.Controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	
	
	
	@GetMapping("/login")
	public String login(@RequestParam( name = "error",required = false) String error,
			@RequestParam( name = "logout",required = false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
		if(principal!=null) {
			flash.addFlashAttribute("info", "Ya tiene una sesion activa");  
			return "redirect:/listar";
		}
		if(error != null) {
			model.addAttribute("error", "Error en el login, usuario o password incorrectos");
		}
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesion con exito");
		}
		
		
			
		return "login";
	}
}
