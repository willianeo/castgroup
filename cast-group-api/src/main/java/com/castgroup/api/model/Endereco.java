package com.castgroup.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
@SequenceGenerator(name = "seq_endereco", initialValue = 9, allocationSize = 100)
public class Endereco {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_endereco")
	private Integer id;
	
	@Column(nullable = false)
	private String rua;
	
	@Column(nullable = false)
	private Integer numero;
	
	@Column
	private String complemento;
	
	@Column(nullable = false)
	private String bairro;
	
	@Column(nullable = false)
	private String cidade;
	
	@Column(nullable = false)
	private String estado;
	
}
