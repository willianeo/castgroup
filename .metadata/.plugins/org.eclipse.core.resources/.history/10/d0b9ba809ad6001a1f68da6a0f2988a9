package com.castgroup.api.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.castgroup.api.model.Signin;

@Repository
public interface SigninDAO extends CrudRepository<Signin, Integer> {
	
	Optional<Signin> findByEmail(String email);
    Boolean existsByEmail(String email);
    
}
