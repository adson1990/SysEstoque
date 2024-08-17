package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entities.Enderecos;

public class EnderecosDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String rua;
	private String bairro;
	private Integer num;
	private String estado;
	private String country;
	
	private Client client;
	
	public EnderecosDTO(Client client) {
		
		for(Enderecos endereco : client.getEnderecos()) {
			this.rua = endereco.getRua();
			this.bairro = endereco.getBairro();
			this.num = endereco.getNum();
			this.estado = endereco.getEstado();
			this.country = endereco.getCountry();
		}
	}
	
	public EnderecosDTO(Enderecos enderecos) {
			this.id = enderecos.getId();
			this.rua = enderecos.getRua();
			this.bairro = enderecos.getBairro();
			this.num = enderecos.getNum();
			this.estado = enderecos.getEstado();
			this.country = enderecos.getCountry();
	}
	
	public EnderecosDTO(String rua, String bairro, Integer num, String estado, String country) {
		super();
		this.rua = rua;
		this.bairro = bairro;
		this.num = num;
		this.estado = estado;
		this.country = country;
	}
	
	public EnderecosDTO(EnderecosDTO end) {
		super();
		id = end.getId();
		this.rua = end.getRua();
		this.bairro = end.getBairro();
		this.num = end.getNum();
		this.estado = end.getEstado();
		this.country = end.getCountry();
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Integer getId() {
		return id;
	}

}
