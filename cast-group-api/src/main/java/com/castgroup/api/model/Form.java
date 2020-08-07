package com.castgroup.api.model;

public interface Form<T, G> {

	G convertModelToForm(T o);
	
	T convertFormToModel(G o);
}
