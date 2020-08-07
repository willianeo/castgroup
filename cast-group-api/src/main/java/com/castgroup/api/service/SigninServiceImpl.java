package com.castgroup.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castgroup.api.dao.SigninDAO;
import com.castgroup.api.model.Signin;
import com.castgroup.api.security.jwt.JwtProvider;

@Service
public class SigninServiceImpl implements SigninService {

	@Autowired
	private SigninDAO repository;

	@Override
	public Signin save(Signin signin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Signin> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Signin> findByUsername(String username) {
		return repository.findByEmail(username);
	}

	@Override
	public List<Signin> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Signin findSigninByToken(String token) {
		JwtProvider provider = new JwtProvider();
		String username = provider.getUserNameFromJwtToken(token);
		Signin signin = this.findByUsername(username).get();
		return signin;
	}

}
