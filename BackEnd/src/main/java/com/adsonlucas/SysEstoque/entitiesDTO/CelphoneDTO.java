package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.adsonlucas.SysEstoque.entities.Celphone;
import com.adsonlucas.SysEstoque.entities.Client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CelphoneDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	@NotBlank
	@Length(min = 2, max = 2)
	private Integer ddd;
	@NotBlank
	@Size(min = 8, max = 9, message= "Telefone deve ter entre 8 e 9 dígitos")
	private String number;
	private Character tipo;
	
	//private Client client;
	
	
	public CelphoneDTO(Integer ddd, String number, Character tipo) {
		super();
		this.ddd = ddd;
		this.number = number;
		this.tipo = tipo;
	}
	
	public CelphoneDTO(Celphone cel) {
		this.id = cel.getId();
		this.ddd = cel.getDdd();
		this.number = cel.getNumber();
		this.tipo = cel.getTipo();
	}
	
	public CelphoneDTO(Client client) {
		
		for(Celphone cel : client.getCel()){
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

	/*public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}*/

	public Integer getId() {
		return id;
	}

}
