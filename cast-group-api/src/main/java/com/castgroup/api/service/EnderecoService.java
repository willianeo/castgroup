package com.castgroup.api.service;

import java.util.List;
import java.util.Optional;

import com.castgroup.api.model.Endereco;

public interface EnderecoService {

	Endereco save(Endereco entity);
	
	void deleteById(Integer id);
	
	Optional<Endereco> findById(Integer id);
	
	List<Endereco> findAll();
	
}
