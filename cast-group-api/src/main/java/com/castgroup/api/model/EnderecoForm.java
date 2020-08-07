package com.castgroup.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EnderecoForm extends BaseForm implements Form<Endereco, EnderecoForm> {

	private Integer id;
	
	private String rua;
	
	private Integer numero;
	
	private String complemento;
	
	private String bairro;
	
	private String cidade;
	
	private String estado;

	@Override
	public EnderecoForm convertModelToForm(Endereco o) {
		return modelMapper().map(o, EnderecoForm.class);
	}

	@Override
	public Endereco convertFormToModel(EnderecoForm o) {
		return modelMapper().map(o, Endereco.class);
	}

}
