package com.bolasaideas.springboot.datajpa.authHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;


@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SessionFlashMapManager flasManager= new SessionFlashMapManager();
		
		FlashMap flasMap= new FlashMap();
		
		flasMap.put("success", "Hola "+ authentication.getName()+", haz iniciado session con exito");
		flasMap.put("info", passwordEncoder.encode("1234"));
		flasMap.put("info", passwordEncoder.encode("1234"));
		flasManager.saveOutputFlashMap(flasMap, request, response);
		if(authentication!=null) {
			logger.info("El usuario "+ authentication.getName()+" ha iniciado session con exito");
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
