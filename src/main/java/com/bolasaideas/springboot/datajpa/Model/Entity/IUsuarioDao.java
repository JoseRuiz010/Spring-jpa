package com.bolasaideas.springboot.datajpa.Model.Entity;

import org.springframework.data.repository.CrudRepository;

import com.bolasaideas.springboot.datajpa.Model.Domain.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario	, Long>{

	public Usuario findByUsername(String username);
	
}
