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
import com.adsonlucas.SysEstoque.entities.Cellphone;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long ID;
	@NotBlank
	@Size(min = 5, max = 20, message= "Nome deve conter entre 5 e 20 caracteres")
	private String name;
	@Length(min = 11, max = 14)
	private String cpf;
	private Double income;
	private Character sexo;
	private String email;
	private Instant birthDate;
	private String senha;
	private String foto;
	
	private List<EnderecosDTO> enderecos = new ArrayList<>();
	
	private List<CellphoneDTO> cel = new ArrayList<>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<CategoryClientDTO> categories = new HashSet();
	
	public ClientDTO() {
	}

	public ClientDTO(String name, String cpf, Double income, Instant birthDate,
					String email, Character sexo, String senha) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.income = income;
		this.birthDate = birthDate;
		this.email = email;
		this.sexo = sexo;
		this.senha = senha;
	}
	
	public ClientDTO(String name, String cpf, Double income, Instant birthDate,
			String email, Character sexo, String senha, String foto) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.income = income;
		this.birthDate = birthDate;
		this.email = email;
		this.sexo = sexo;
		this.senha = senha;
		this.foto = foto;
	}

	public ClientDTO(Client client) {
		super();
		this.ID = client.getID();
		this.name = client.getName();
		this.cpf = client.getCpf();
		this.income = client.getIncome();
		this.birthDate = client.getBirthDate();
		this.email = client.getEmail();
		this.sexo = client.getSexo();
		this.senha = client.getSenha();
		this.foto = client.getFoto();
	}
	
	public ClientDTO(Client entity, Set<CategoryClient> categories) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryClientDTO(cat)));
	}
	
	public ClientDTO(Client entity, Set<CategoryClient> categories, List<Enderecos> enderecos) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryClientDTO(cat)));
		enderecos.forEach(end -> this.enderecos.add(new EnderecosDTO(end)));
	}
	
	public ClientDTO(Client entity, Set<CategoryClient> categories, List<Enderecos> enderecos, List<Cellphone> cellphone) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryClientDTO(cat)));
		enderecos.forEach(end -> this.enderecos.add(new EnderecosDTO(end)));
		cellphone.forEach(cel -> this.cel.add(new CellphoneDTO(cel)));
	}
	
	public ClientDTO(Client entity, List<Enderecos> enderecos, List<Cellphone> cellphone) {
		this(entity);
		enderecos.forEach(end -> this.enderecos.add(new EnderecosDTO(end)));
		cellphone.forEach(cel -> this.cel.add(new CellphoneDTO(cel)));
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

	public Set<CategoryClientDTO> getCategories() {
		return categories;
	}

	public void setCategories(Set<CategoryClientDTO> categories) {
		this.categories = categories;
	}

	public List<EnderecosDTO> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecosDTO> enderecos) {
		this.enderecos = enderecos;
	}
	
	public List<CellphoneDTO> getCellphone() {
		return cel;
	}

	public void setCellphone(List<CellphoneDTO> cel) {
		this.cel = cel;
	}

}
