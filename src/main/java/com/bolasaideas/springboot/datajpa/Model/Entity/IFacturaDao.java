package com.bolasaideas.springboot.datajpa.Model.Entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolasaideas.springboot.datajpa.Model.Domain.Factura;

public interface IFacturaDao  extends CrudRepository<Factura,Long> {

	@Query("select f from Factura f join fetch f.cliente c join fetch f.itemsFacturas l join fetch l.producto where f.id =?1")
	public Factura fetchByIdWithClienteWithItemFacturaWhithProducto(Long id);
	
}
