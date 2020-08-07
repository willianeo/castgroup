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
@SequenceGenerator(name = "seq_equipe", initialValue = 5, allocationSize = 100)
public class Equipe {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_equipe")
	private Integer id;

	@Column(nullable = false)
	private String nome;
	
}
