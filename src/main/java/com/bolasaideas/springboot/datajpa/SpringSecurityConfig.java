package com.bolasaideas.springboot.datajpa;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bolasaideas.springboot.datajpa.Services.JpaUserDatailService;
import com.bolasaideas.springboot.datajpa.authHandler.LoginSuccessHandler;


//Habilitamos el uso de anotaciones
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig  extends WebSecurityConfigurerAdapter{

	@Autowired
	private LoginSuccessHandler successHandler;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JpaUserDatailService userDatailService;
	
/*	@Bean
	  static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	
	}
	*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http.authorizeHttpRequests()
		.antMatchers("/","/css/**","/js/**","/images/**", "/listar/")
		.permitAll()
		//.antMatchers("/ver/**").hasAnyRole("USER")
		//.antMatchers("/uploads/**").hasAnyAuthority("USER")
		//.antMatchers("/form/**").hasAnyRole("ADMIN")
		//.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		//.antMatchers("/factura/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.successHandler(successHandler)
		.loginPage("/login")
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403")
		;
	}


	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
	
		PasswordEncoder encoder= this.passwordEncoder;
		UserBuilder users = User.builder().passwordEncoder(encoder::encode); //pass -> encoder.encode(pass)
	
		/*builder.inMemoryAuthentication()
		.withUser(users.username("admin").password("1234").roles("ADMIN","USER"))
		.withUser(users.username("jose").password("1234").roles("USER"));
 */
		
		
		/*
		 * JDBC autneticacion
		 * builder.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("select  username, password, enable from users where username =?")
		.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on a.user_id=u.id  where u.username=?");
		*/
		builder.userDetailsService(userDatailService)
		.passwordEncoder(passwordEncoder);
		
		
	}
	
}
