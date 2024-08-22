package com.adsonlucas.SysEstoque.entities;

import java.io.Serializable;
import java.util.Objects;

import com.adsonlucas.SysEstoque.entitiesDTO.EnderecosDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_enderecos")
public class Enderecos implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String rua;
	private String bairro;
	private Integer num;
	private String estado;
	private String country;
	private String cep;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private Client client;

	public Enderecos() {}
	
	public Enderecos(String rua, String bairro, Integer num, String estado, String country, String cep) {
		super();
		this.rua = rua;
		this.bairro = bairro;
		this.num = num;
		this.estado = estado;
		this.country = country;
		this.cep = cep;
	}
	
	public Enderecos(Client client) {
		
		for(Enderecos endereco : client.getEnderecos()) {
			this.rua = endereco.getRua();
			this.bairro = endereco.getBairro();
			this.num = endereco.getNum();
			this.estado = endereco.getEstado();
			this.country = endereco.getCountry();
			this.cep = endereco.getCep();
		}
	}
	
	public Enderecos(EnderecosDTO end) {
		super();
		id = end.getId();
		this.rua = end.getRua();
		this.bairro = end.getBairro();
		this.num = end.getNum();
		this.estado = end.getEstado();
		this.country = end.getCountry();
		this.cep = end.getCep();
	}
	
	public Enderecos(Enderecos end) {
		super();
		id = end.getId();
		this.rua = end.getRua();
		this.bairro = end.getBairro();
		this.num = end.getNum();
		this.estado = end.getEstado();
		this.country = end.getCountry();
		this.cep = end.getCep();
	}

	public Integer getId() {
		return id;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enderecos other = (Enderecos) obj;
		return Objects.equals(id, other.id);
	}	
	
}
