package com.castgroup.api.service;

import java.util.List;
import java.util.Optional;

import com.castgroup.api.model.Signin;

public interface SigninService {

	Signin save(Signin signin);
	
	void deleteById(Integer id);
	
	Optional<Signin> findById(Integer id);
	
	Optional<Signin> findByUsername(String username);
	
	List<Signin> findAll();
	
}
