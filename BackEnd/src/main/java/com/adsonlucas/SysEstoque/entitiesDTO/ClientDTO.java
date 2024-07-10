package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.adsonlucas.SysEstoque.entities.CategoryClient;
import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entities.Enderecos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClientDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long ID;
	@NotBlank
	@Size(min = 5, max = 20, message= "Nome deve conter entre 5 e 20 caracteres")
	private String name;
	@Length(min = 11, max = 14)
	private String cpf;
	private Double income;
	private Instant birthDate;
	private Integer children;
	
	private List<Enderecos> enderecos = new ArrayList<>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Set<CategoryClientDTO> categories = new HashSet();
	
	public ClientDTO() {
	}

	public ClientDTO(String name, String cpf, Double income, Instant birthDate, Integer children) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.income = income;
		this.birthDate = birthDate;
		this.children = children;
	}

	public ClientDTO(Client client) {
		super();
		this.ID = client.getID();
		this.name = client.getName();
		this.cpf = client.getCpf();
		this.income = client.getIncome();
		this.birthDate = client.getBirthDate();
		this.children = client.getChildren();
	}
	
	public ClientDTO(Client entity, Set<CategoryClient> categories) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryClientDTO(cat)));
	}
	
	public ClientDTO(Client entity, Set<CategoryClient> categories, List<Enderecos> enderecos) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryClientDTO(cat)));
		enderecos.forEach(end -> this.enderecos.add(new Enderecos(end)));
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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
	
	public Set<CategoryClientDTO> getCategories() {
		return categories;
	}

	public void setCategories(Set<CategoryClientDTO> categories) {
		this.categories = categories;
	}

	public List<Enderecos> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Enderecos> enderecos) {
		this.enderecos = enderecos;
	}

}
