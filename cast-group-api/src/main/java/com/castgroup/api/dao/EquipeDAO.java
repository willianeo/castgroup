package com.castgroup.api.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.castgroup.api.model.Equipe;

@Repository
public interface EquipeDAO extends CrudRepository<Equipe, Integer> {

}
