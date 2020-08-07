package com.castgroup.api.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FeriasForm extends BaseForm implements Form<Ferias, FeriasForm> {

	private Integer id;
	
	private FuncionarioForm funcionario;
	
	private LocalDate inicio;
	
	private LocalDate retorno;
	
	private String msg;
	
	private Boolean error;

	@Override
	public FeriasForm convertModelToForm(Ferias o) {
		return modelMapper().map(o, FeriasForm.class);
	}

	@Override
	public Ferias convertFormToModel(FeriasForm o) {
		return modelMapper().map(o, Ferias.class);
	}

}
