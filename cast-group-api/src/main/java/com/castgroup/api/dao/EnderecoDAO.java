package com.castgroup.api.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.castgroup.api.model.Endereco;

@Repository
public interface EnderecoDAO extends CrudRepository<Endereco, Integer> {

}
