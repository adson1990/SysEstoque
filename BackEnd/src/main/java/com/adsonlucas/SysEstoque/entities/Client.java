package com.adsonlucas.SysEstoque.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_client")
public class Client implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	@Column(nullable = false, length = 25)
	private String name;
	@Column(nullable = false, unique = true)
	private String cpf;
	private Double income;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant birthDate;
	private Integer children;
	
	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
	private List<Enderecos> enderecos = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "tb_client_category",
			   joinColumns = @JoinColumn(name = "client_id"),
			   inverseJoinColumns = @JoinColumn(name = "categoryclient_id")
			   )
	Set<CategoryClient> categories = new HashSet<>();
	
	public Client() {
	}

	public Client(String name, String cpf, Double income, Instant birthDate, Integer children) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.income = income;
		this.birthDate = birthDate;
		this.children = children;
	}
	
	public Client(ClientDTO clientDTO) {
		this.name = clientDTO.getName();
		this.cpf = clientDTO.getCpf();
		this.income = clientDTO.getIncome();
		this.birthDate = clientDTO.getBirthDate();
		this.children = clientDTO.getChildren();
	}
	
	public Client(ClientDTO clientDTO, Long ID) {
		this(clientDTO);
		
		if (ID != null) {
			this.ID = ID;
		}

		//clientDTO.getCategories().forEach(cat -> this.categories.add(new CategoryClient(cat.getDescrption())));		
	}
	
	public Client(ClientDTO cliente, Set<CategoryClient> listCategory) {
		this(cliente);
		listCategory.forEach(cat -> this.categories.add(new CategoryClient(cat.getDescription())));
	}
	
	public Client(ClientDTO cliente, Set<CategoryClient> listCategory, List<Enderecos> listEnderecos) {
		this(cliente);
		listCategory.forEach(cat -> this.categories.add(new CategoryClient(cat.getDescription())));
		listEnderecos.forEach(end -> this.enderecos.add(new Enderecos(end)));
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

	public List<Enderecos> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Enderecos> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<CategoryClient> getCategories() {
		return categories;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(ID, other.ID) && Objects.equals(cpf, other.cpf);
	}
	
	
}
