package com.castgroup.api.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.castgroup.api.model.Endereco;
import com.castgroup.api.model.EnderecoForm;
import com.castgroup.api.model.Equipe;
import com.castgroup.api.model.Ferias;
import com.castgroup.api.model.FeriasForm;
import com.castgroup.api.model.Funcionario;
import com.castgroup.api.model.FuncionarioForm;
import com.castgroup.api.model.Signin;
import com.castgroup.api.security.jwt.JwtProvider;
import com.castgroup.api.service.Email;
import com.castgroup.api.service.EnderecoServiceImpl;
import com.castgroup.api.service.EquipeServiceImpl;
import com.castgroup.api.service.FeriasServiceImpl;
import com.castgroup.api.service.FuncionarioServiceImpl;
import com.castgroup.api.service.ImageService;
import com.castgroup.api.service.SigninServiceImpl;
import com.sun.istack.logging.Logger;

import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:5000/funcionario", maxAge = 3600)
@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

	private static final Logger logger = Logger.getLogger(FuncionarioController.class);

	@Autowired
	ImageService imageService;

	@Autowired
	private FuncionarioServiceImpl funcionarioService;

	@Autowired
	private EnderecoServiceImpl enderecoService;

	@Autowired
	private FeriasServiceImpl feriasService;

	@Autowired
	private EquipeServiceImpl equipeService;
	
	@Autowired
	Email email;

	@GetMapping(path = { "/{id}" })
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> findById(@PathVariable Integer id, @RequestHeader(name = "Authorization") String token) {
		return funcionarioService.findById(id).map(p -> ResponseEntity.ok().body(p))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping(path = { "/" })
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public List<FuncionarioForm> findAll(@RequestHeader(name = "Authorization") String token) {
		List<FuncionarioForm> list = new ArrayList<>();
		funcionarioService.findAll().forEach(f -> {
			list.add(new FuncionarioForm().convertModelToForm(f));
		});
		return list;
	}

	@GetMapping(path = { "/qrcode/{text}" }, produces = MediaType.IMAGE_PNG_VALUE)
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public Mono<ResponseEntity<byte[]>> getQRCode(@PathVariable String text
			, @RequestHeader(name = "Authorization") String token) {
		return imageService.generateQRCode(text, 256, 256).map(imageBuff ->  
			ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES)).body(imageBuff)
		);
	}
	
	@PostMapping(path = "/", produces = MediaType.IMAGE_PNG_VALUE)
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<ResponseEntity<byte[]>> create(@RequestBody FuncionarioForm funcionarioForm,
			@RequestHeader(name = "Authorization") String token) {
		Funcionario funcionario = funcionarioForm.convertFormToModel(funcionarioForm);
		funcionario = funcionarioService.save(funcionario);
		FuncionarioForm cfuncionarioForm = funcionarioForm.convertModelToForm(funcionario);
		
		return imageService.generateQRCode(cfuncionarioForm.toString(), 256, 256).map(imageBuff ->  
		
			ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES)).body(imageBuff)
		);
		//return cfuncionarioForm;
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> update(@PathVariable(value = "id") Integer id,
			@RequestBody FuncionarioForm funcionarioForm, @RequestHeader(name = "Authorization") String token) {
		Funcionario funcionario = funcionarioForm.convertFormToModel(funcionarioForm);
		return funcionarioService.findById(id).map(p -> {
			p.setDataContratacao(funcionario.getDataContratacao());
			p.setDataNascimento(funcionario.getDataNascimento());
			p.setEndereco(funcionario.getEndereco());
			p.setEquipe(funcionario.getEquipe());
			p.setFoto(funcionario.getFoto());
			p.setId(funcionario.getId());
			p.setMatricula(funcionario.getMatricula());
			p.setNome(funcionario.getNome());
			Funcionario cfuncionario = funcionarioService.save(p);
			FuncionarioForm cfuncionarioForm = funcionarioForm.convertModelToForm(cfuncionario);

			return ResponseEntity.ok().body(cfuncionarioForm);

		}).orElse(ResponseEntity.notFound().build());

	}

	@DeleteMapping(path = { "/{id}" })
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Integer id, @RequestHeader(name = "Authorization") String token) {
		return funcionarioService.findById(id).map(p -> {
			funcionarioService.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

//	cadastro de ferias

	@GetMapping(path = { "/{matricula}/ferias" })
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public List<FeriasForm> findFeriasByMatricula(@PathVariable String matricula,
			@RequestHeader(name = "Authorization") String token) {
		List<FeriasForm> list = new ArrayList<>();
		feriasService.findByMatriculaFuncionario(matricula).forEach(f -> {
			list.add(new FeriasForm().convertModelToForm(f));
		});
		return list;
	}

	@GetMapping(path = { "/ferias/{meses}" })
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public List<FuncionarioForm> findFeriasPendenteByMeses(@PathVariable Integer meses,
			@RequestHeader(name = "Authorization") String token) {
		List<Funcionario> avfuncionarios = funcionarioService.findAll();
		List<FuncionarioForm> cfuncionarioForms = new ArrayList<>();
		for (Funcionario funcionario : avfuncionarios) {
			if (feriasService.feriasVencido(funcionario, meses)) {
				FuncionarioForm cfuncionarioForm = new FuncionarioForm().convertModelToForm(funcionario);
				cfuncionarioForms.add(cfuncionarioForm);
			}
		}
		return cfuncionarioForms;
	}

	@PostMapping("/ferias")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public FeriasForm cadastrarFerias(@RequestBody FeriasForm feriasForm,
			@RequestHeader(name = "Authorization") String token) {
		FeriasForm cform = feriasForm;
		Ferias ferias = feriasForm.convertFormToModel(feriasForm);
		Integer funcionarioId = feriasForm.getFuncionario().getId();
		Funcionario funcionario = funcionarioService.findById(funcionarioId).get();
		Equipe equipe = funcionario.getEquipe();
		List<Funcionario> equipeFuncionarios = funcionarioService.findFuncionarioByEquipeId(equipe.getId());
		try {
			feriasService.checkPeriodoFerias(equipeFuncionarios, ferias);
			ferias.setFuncionario(funcionario);
			ferias = feriasService.save(ferias);
			cform = cform.convertModelToForm(ferias);
			cform.setError(false);
			return cform;
		} catch (Exception e) {
			cform.setError(true);
			cform.setMsg(e.getMessage());
			return cform;
		}
	}

	@DeleteMapping(path = { "/ferias/{id}" })
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteFerias(@PathVariable Integer id,
			@RequestHeader(name = "Authorization") String token) {
		return feriasService.findById(id).map(p -> {
			feriasService.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

	// cadasatro de endereço

	@GetMapping(path = { "/endereco/{id}" })
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> findEnderecoById(@PathVariable Integer id,
			@RequestHeader(name = "Authorization") String token) {
		return enderecoService.findById(id).map(p -> ResponseEntity.ok().body(p))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/endereco/")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public EnderecoForm createEndereco(@RequestBody EnderecoForm enderecoForm,
			@RequestHeader(name = "Authorization") String token) {
		Endereco endereco = enderecoForm.convertFormToModel(enderecoForm);
		endereco = enderecoService.save(endereco);
		return enderecoForm.convertModelToForm(endereco);
	}

	@PutMapping("/endereco/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> updateEndereco(@PathVariable(value = "id") Integer id,
			@RequestBody EnderecoForm enderecoForm, @RequestHeader(name = "Authorization") String token) {
		Endereco endereco = enderecoForm.convertFormToModel(enderecoForm);

		return enderecoService.findById(id).map(p -> {
			p.setBairro(endereco.getBairro());
			p.setCidade(endereco.getCidade());
			p.setComplemento(endereco.getComplemento());
			p.setEstado(endereco.getEstado());
			p.setId(endereco.getId());
			p.setNumero(endereco.getNumero());
			p.setRua(endereco.getRua());
			Endereco updated = enderecoService.save(p);
			return ResponseEntity.ok().body(updated);

		}).orElse(ResponseEntity.notFound().build());

	}

	@DeleteMapping(path = { "/endereco/{id}" })
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteEndereco(@PathVariable Integer id,
			@RequestHeader(name = "Authorization") String token) {
		return enderecoService.findById(id).map(p -> {
			enderecoService.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
