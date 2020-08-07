package com.castgroup.api.service;

import java.util.List;
import java.util.Optional;

import com.castgroup.api.model.Ferias;

public interface FeriasService {

	Ferias save(Ferias ferias) throws Exception;
	
	void deleteById(Integer id);
	
	Optional<Ferias> findById(Integer id);
	
	List<Ferias> findAll();
	
}
