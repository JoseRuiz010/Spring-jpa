package com.bolasaideas.springboot.datajpa.Services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolasaideas.springboot.datajpa.Model.Domain.Role;
import com.bolasaideas.springboot.datajpa.Model.Domain.Usuario;
import com.bolasaideas.springboot.datajpa.Model.Entity.IUsuarioDao;

@Service
public class JpaUserDatailService implements UserDetailsService{

	@Autowired
	private IUsuarioDao usuarioDao; 
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario=  usuarioDao.findByUsername(username);
		
		
		if(usuario==null) {
			logger.error("Error login: no existe el usuario "+ username);
			throw new UsernameNotFoundException("Username "+ username+ " no exixte" );
		}
		
		
		List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
		
		for (Role rol : usuario.getRoles()) {
			logger.info("Role: ".concat(rol.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
		}
		
		if(authorities .isEmpty()) {
			logger.error("Error login: "+ username+ " no tiene roles, no puede iniciar session");
			throw new UsernameNotFoundException("Username "+ username+ "no tiene roles" );
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnable(), true, true,true, authorities);
	}

	
	
}
