package com.adsonlucas.SysEstoque.entities;

import java.io.Serializable;
import java.util.Objects;

import com.adsonlucas.SysEstoque.entitiesDTO.CelphoneDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_celphone")
public class Celphone implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer ddd;
	@Column(nullable = false, unique = true)
	private String number;
	private Character tipo;  
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private Client client;
	
	public Celphone() {
		
	}

	public Celphone(Integer ddd, String number, Character tipo) {
		super();
		this.ddd = ddd;
		this.number = number;
		this.tipo = tipo;
	}
	
	public Celphone(CelphoneDTO cel) {
		super();
		id = cel.getId();
		this.ddd = cel.getDdd();
		this.number = cel.getNumber();
		this.tipo = cel.getTipo();
	}
	
	public Celphone(Celphone cel) {
		super();
		id = cel.getId();
		this.ddd = cel.getDdd();
		this.number = cel.getNumber();
		this.tipo = cel.getTipo();
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Integer getId() {
		return id;
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
		Celphone other = (Celphone) obj;
		return Objects.equals(id, other.id);
	}
	
}
