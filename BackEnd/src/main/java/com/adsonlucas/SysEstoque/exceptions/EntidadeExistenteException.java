package com.adsonlucas.SysEstoque.exceptions;

public class EntidadeExistenteException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public EntidadeExistenteException(String msg) {
		super(msg);
	}

}
