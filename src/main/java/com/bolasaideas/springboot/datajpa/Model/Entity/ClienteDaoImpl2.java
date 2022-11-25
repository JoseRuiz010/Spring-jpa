package com.bolasaideas.springboot.datajpa.Model.Entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolasaideas.springboot.datajpa.Model.Domain.Cliente;

@Repository("clienteDaoJPA")
public class ClienteDaoImpl2 implements IClienteDao2 {

	@PersistenceContext
	private EntityManager entityManager;
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return entityManager.createQuery("from Cliente").getResultList();
	}
	@Override
	@Transactional
	public void save(Cliente cliente) {

		if(cliente.getId()!=null && cliente.getId()>0) {
			
			entityManager.merge(cliente);
		}else {
			
			entityManager.persist(cliente);
		}
       
		
	}
	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		
		return entityManager.find(Cliente.class, id);
			  
	}
	@Override
	@Transactional 
	public void delete(Long id) {
	 Cliente cliente= findOne(id); 
	 entityManager.remove(cliente);
	}

}
