package com.castgroup.api.model;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class BaseForm {
	@Bean
	protected ModelMapper modelMapper() {
	    return new ModelMapper();
	}

}
