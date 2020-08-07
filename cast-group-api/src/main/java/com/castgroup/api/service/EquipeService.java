package com.castgroup.api.service;

import java.util.List;
import java.util.Optional;

import com.castgroup.api.model.Equipe;

public interface EquipeService {

	Equipe save(Equipe equipe);
	
	void deleteById(Integer id);
	
	Optional<Equipe> findById(Integer id);
	
	List<Equipe> findAll();
	
}
