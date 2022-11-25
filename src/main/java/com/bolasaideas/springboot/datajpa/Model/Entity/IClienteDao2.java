package com.bolasaideas.springboot.datajpa.Model.Entity;

import java.util.List;

import com.bolasaideas.springboot.datajpa.Model.Domain.Cliente;

public interface IClienteDao2 {

	public List<Cliente> findAll();
	
	public void save (Cliente cliente);
	
	public Cliente findOne (Long id );
	public void delete(Long id);

}
