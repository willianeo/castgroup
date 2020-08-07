package com.castgroup.api.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FuncionarioForm extends BaseForm implements Form<Funcionario, FuncionarioForm> {

	private Integer id;

	private String nome;

	private LocalDate dataNascimento;

	private EnderecoForm endereco;

	private LocalDate dataContratacao;

	private String foto;

	private EquipeForm equipe;

	private String matricula;

	@Override
	public FuncionarioForm convertModelToForm(Funcionario o) {
		return modelMapper().map(o, FuncionarioForm.class);
	}

	@Override
	public Funcionario convertFormToModel(FuncionarioForm o) {
		return modelMapper().map(o, Funcionario.class);
	}

}
