package com.bolasaideas.springboot.datajpa.Model.Entity;

 
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolasaideas.springboot.datajpa.Model.Domain.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {
 

}
