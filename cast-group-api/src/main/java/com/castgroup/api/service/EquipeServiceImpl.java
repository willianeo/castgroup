package com.castgroup.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castgroup.api.dao.EquipeDAO;
import com.castgroup.api.model.Equipe;
import com.castgroup.api.model.Ferias;
import com.castgroup.api.model.Funcionario;

@Service
public class EquipeServiceImpl implements EquipeService {

	@Autowired
	private EquipeDAO equipeDao;
	
	@Override
	public Equipe save(Equipe entity) {
		return equipeDao.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		equipeDao.deleteById(id);
	}

	@Override
	public Optional<Equipe> findById(Integer id) {
		return equipeDao.findById(id);
	}

	@Override
	public List<Equipe> findAll() {
		return (List<Equipe>) equipeDao.findAll();
	}

}
