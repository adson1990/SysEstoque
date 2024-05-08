package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.adsonlucas.SysEstoque.entities.CategoryClient;
import com.adsonlucas.SysEstoque.entities.Client;

public class ClientDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long ID;
	private String name;
	private String CPF;
	private Double income;
	private Instant birthDate;
	private Integer children;
	
	private List<CategoryClientDTO> categories = new ArrayList<>();
	
	public ClientDTO() {
	}

	public ClientDTO(String name, String cPF, Double income, Instant birthDate, Integer children) {
		super();
		this.name = name;
		this.CPF = cPF;
		this.income = income;
		this.birthDate = birthDate;
		this.children = children;
	}

	public ClientDTO(Client client) {
		super();
		this.ID = client.getID();
		this.name = client.getName();
		this.CPF = client.getCPF();
		this.income = client.getIncome();
		this.birthDate = client.getBirthDate();
		this.children = client.getChildren();
	}
	
	public ClientDTO(Client entity, Set<CategoryClient> categories) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryClientDTO(cat)));
	}

	public Long getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Instant getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Instant birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getChildren() {
		return children;
	}

	public void setChildren(Integer children) {
		this.children = children;
	}	
	
	public List<CategoryClientDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryClientDTO> categories) {
		this.categories = categories;
	}

}
