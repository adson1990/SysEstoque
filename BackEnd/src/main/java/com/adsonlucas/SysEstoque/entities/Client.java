package com.adsonlucas.SysEstoque.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
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
	private Character sexo;
	private String email;
	private String senha;
	@Lob
	private String foto;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant birthDate;
	
	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Enderecos> enderecos = new ArrayList<>();
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonManagedReference
	private List<Cellphone> cellphones = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "tb_client_category",
			   joinColumns = @JoinColumn(name = "client_id"),
			   inverseJoinColumns = @JoinColumn(name = "categoryclient_id")
			   )
	Set<CategoryClient> categories = new HashSet<>();
	
	public Client() {
	}
	
	public Client(String name, String cpf, Double income, Instant birthDate,
				  Character sexo, String email, String senha) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.income = income;
		this.birthDate = birthDate;
		this.sexo = sexo;
		this.email = email;
		this.senha = senha;
	}
	
	public Client(String name, String cpf, Double income, Instant birthDate,
			  Character sexo, String email, String senha, String foto) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.income = income;
		this.birthDate = birthDate;
		this.sexo = sexo;
		this.email = email;
		this.senha = senha;
		this.foto = foto;
	}
	
	public Client(ClientDTO clientDTO) {
		this.name = clientDTO.getName();
		this.cpf = clientDTO.getCpf();
		this.income = clientDTO.getIncome();
		this.birthDate = clientDTO.getBirthDate();
		this.sexo = clientDTO.getSexo();
		this.email = clientDTO.getEmail();
		this.senha = clientDTO.getSenha();
		this.foto = clientDTO.getFoto();
	}
	
	public Client(ClientDTO clientDTO, Long ID) {
		this(clientDTO);
		
		if (ID != null) {
			this.ID = ID;
		}	
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
	
	public Client(ClientDTO cliente, Set<CategoryClient> listCategory, List<Enderecos> listEnderecos, List<Cellphone> listCelphone) {
		this(cliente);
		listCategory.forEach(cat -> this.categories.add(new CategoryClient(cat.getDescription())));
		listEnderecos.forEach(end -> this.enderecos.add(new Enderecos(end)));
		listCelphone.forEach(cel -> this.cellphones.add(new Cellphone(cel)));
	}
	
	public Client(ClientDTO cliente, List<Enderecos> listEnderecos, List<Cellphone> listCellphones) {
		this(cliente);
		listEnderecos.forEach(end -> this.enderecos.add(new Enderecos(end)));
		listCellphones.forEach(cel -> this.cellphones.add(new Cellphone(cel)));
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

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Cellphone> getCel() {
		return cellphones;
	}

	public void setCel(List<Cellphone> cel) {
		this.cellphones = cel;
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

	public void setCategories(Set<CategoryClient> categories) {
		this.categories = categories;
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
