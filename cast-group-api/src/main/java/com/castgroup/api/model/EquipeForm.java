package com.castgroup.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EquipeForm extends BaseForm implements Form<Equipe, EquipeForm> {

	private Integer id;

	private String nome;

	@Override
	public EquipeForm convertModelToForm(Equipe o) {
		return modelMapper().map(o, EquipeForm.class);
	}

	@Override
	public Equipe convertFormToModel(EquipeForm o) {
		return modelMapper().map(o, Equipe.class);
	}

}
