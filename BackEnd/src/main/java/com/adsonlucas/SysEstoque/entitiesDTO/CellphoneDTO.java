package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.adsonlucas.SysEstoque.entities.Cellphone;
import com.adsonlucas.SysEstoque.entities.Client;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CellphoneDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	@NotBlank
	@Length(min = 2, max = 2)
	private Integer ddd;
	@NotBlank
	@Size(min = 8, max = 9, message= "Telefone deve ter entre 8 e 9 dígitos")
	private String number;
	private Character tipo;
	
	
	public CellphoneDTO(Integer ddd, String number, Character tipo) {
		super();
		this.ddd = ddd;
		this.number = number;
		this.tipo = tipo;
	}
	
	public CellphoneDTO(Cellphone cel) {
		this.id = cel.getId();
		this.ddd = cel.getDdd();
		this.number = cel.getNumber();
		this.tipo = cel.getTipo();
	}
	
	public CellphoneDTO(Client client) {
		
		for(Cellphone cel : client.getCel()){
			this.ddd = cel.getDdd();
			this.number = cel.getNumber();
			this.tipo = cel.getTipo();
		}
	}

	public Integer getDdd() {
		return ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

}
