package com.castgroup.api.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.castgroup.api.model.Funcionario;

@Repository
public interface FuncionarioDAO extends CrudRepository<Funcionario, Integer> {

	List<Funcionario> findFuncionarioByEquipeId(Integer equipeId);
	
}
