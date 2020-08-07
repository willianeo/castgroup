package com.castgroup.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castgroup.api.dao.EnderecoDAO;
import com.castgroup.api.model.Endereco;
import com.castgroup.api.model.Equipe;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	@Autowired
	EnderecoDAO repository;
	
	@Override
	public Endereco save(Endereco entity) {
		return repository.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<Endereco> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public List<Endereco> findAll() {
		return (List<Endereco>) repository.findAll();
	}

}
