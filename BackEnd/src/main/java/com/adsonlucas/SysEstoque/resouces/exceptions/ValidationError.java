package com.adsonlucas.SysEstoque.resouces.exceptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> listError = new ArrayList<>();

	public List<FieldMessage> getListError() {
		return listError;
	}
	
	public void addError(String fieldName, String message) {
		listError.add(new FieldMessage(fieldName, message));
	}
}
