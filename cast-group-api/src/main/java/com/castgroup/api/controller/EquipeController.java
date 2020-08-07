package com.castgroup.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.castgroup.api.model.Equipe;
import com.castgroup.api.model.EquipeForm;
import com.castgroup.api.model.FuncionarioForm;
import com.castgroup.api.security.jwt.JwtProvider;
import com.castgroup.api.service.EquipeServiceImpl;
import com.castgroup.api.service.SigninServiceImpl;
import com.sun.istack.logging.Logger;

@RestController
@RequestMapping("/equipe")
public class EquipeController {

	private static final Logger logger = Logger.getLogger(FuncionarioController.class);
	
	@Autowired
	private EquipeServiceImpl equipeService;
	
	@Autowired
	private SigninServiceImpl signinService;
	
	@GetMapping(path = {"/{id}"})
	@PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> findById(@PathVariable Integer id
			, @RequestHeader(name = "Authorization") String token) {
		
		return equipeService.findById(id)
				.map(p -> ResponseEntity.ok().body(p))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping(path = { "/" })
	@PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
	public List<EquipeForm> findAll(@RequestHeader(name = "Authorization") String token) {
		List<EquipeForm> list = new ArrayList<>();
		equipeService.findAll().forEach(f -> {
			list.add(new EquipeForm().convertModelToForm(f));
		});
		return list;
	}

	@PostMapping("/")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Equipe create(@RequestBody EquipeForm equipeForm
			, @RequestHeader(name = "Authorization") String token) {
		Equipe equipe = equipeForm.convertFormToModel(equipeForm);
		return equipeService.save(equipe);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> update(@PathVariable(value = "id") Integer id
			, @RequestBody EquipeForm equipeForm, @RequestHeader(name = "Authorization") String token) {
		Equipe equipe = equipeForm.convertFormToModel(equipeForm);
		return equipeService.findById(id).map(p -> {
			p.setId(equipe.getId());
			p.setNome(equipe.getNome());
			Equipe updated = equipeService.save(p);
			return ResponseEntity.ok().body(updated);
			
		}).orElse(ResponseEntity.notFound().build());
		
	}
	
	@DeleteMapping(path = {"/{id}"})
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Integer id
			, @RequestHeader(name = "Authorization") String token) {
		
		return equipeService.findById(id)
				.map(p -> {
					equipeService.deleteById(id);
					return ResponseEntity.ok().build();
				}).orElse(ResponseEntity.notFound().build());
	}
	
	
	
}
