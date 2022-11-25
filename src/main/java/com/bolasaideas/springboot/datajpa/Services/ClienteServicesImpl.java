package com.bolasaideas.springboot.datajpa.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolasaideas.springboot.datajpa.Model.Domain.Cliente;
import com.bolasaideas.springboot.datajpa.Model.Domain.Factura;
import com.bolasaideas.springboot.datajpa.Model.Domain.Producto;
import com.bolasaideas.springboot.datajpa.Model.Entity.IClienteDao;
import com.bolasaideas.springboot.datajpa.Model.Entity.IFacturaDao;
import com.bolasaideas.springboot.datajpa.Model.Entity.IProductoDao;

@Service
public class ClienteServicesImpl implements IClienteService {

	
	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private IFacturaDao facturaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional 
	public void save(Cliente cliente) {
		// TODO Auto-generated method stub
		clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		// TODO Auto-generated method stub
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional 
	public void delete(Long id) {
		// TODO Auto-generated method stub
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
	 
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional
	public List<Producto> findByNombre(String term) {
		 
		return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		 
		facturaDao.save(factura);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
					
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaByID(Long id) {
		 
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteFactura(Long id) {
		// TODO Auto-generated method stub
		facturaDao.deleteById(id);
	}

	@Override
	@Transactional
	public Factura fetchFacturaById(Long id) {
		 		
		return facturaDao.fetchByIdWithClienteWithItemFacturaWhithProducto(id);
	}

}
