package com.bolasaideas.springboot.datajpa.Services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolasaideas.springboot.datajpa.Model.Domain.Cliente;
import com.bolasaideas.springboot.datajpa.Model.Domain.Factura;
import com.bolasaideas.springboot.datajpa.Model.Domain.Producto;

public interface IClienteService {
	public List<Cliente> findAll();
	public Page<Cliente> findAll(Pageable pageable);
	
	public void save (Cliente cliente);
	
	public Cliente findOne (Long id );
	public void delete(Long id);
	
	public List<Producto>findByNombre(String producto); 
	public void saveFactura(Factura factura);
	public Producto findProductoById(Long id); 
	public Factura findFacturaByID(Long id);
	public void deleteFactura(Long id);
	
	public Factura fetchFacturaById(Long id);
}
