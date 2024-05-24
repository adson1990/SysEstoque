package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.adsonlucas.SysEstoque.entities.Roles;
import com.adsonlucas.SysEstoque.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long ID;	
	@NotBlank(message = "Campo obrigatório")
	private String nome;
	private String sobrenome;
	@Length(min = 5, max = 12)
	private String senha;
	@Email(message = "Entrar com e-mail válido")
	private String email;	
	@Positive
	private Integer idade;
	private String foto;
	@PastOrPresent(message = "Não pode ser data maior que a atual")
	private LocalDate dt_nascimento;
	Set<Roles> roles = new HashSet<>();
	
	
	public UserDTO() {}
	
	public UserDTO(Long iD, String nome, String sobrenome, String senha, String email, Integer idade, String foto,
			LocalDate dt_nascimento) {
		super();
		ID = iD;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.senha = senha;
		this.email = email;
		this.idade = idade;
		this.foto = foto;
		this.dt_nascimento = dt_nascimento;
	}
	
	public UserDTO(User user) {
		super();
		ID = user.getID();
		this.nome = user.getNome();
		this.sobrenome = user.getSobrenome();
		this.senha =user.getSenha();
		this.email = user.getEmail();
		this.idade = user.getIdade();
		this.foto = user.getFoto();
		this.dt_nascimento = user.getDt_nascimento();
	}
	
	public Long getID() {
		return ID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public LocalDate getDt_nascimento() {
		return dt_nascimento;
	}

	public void setDt_nascimento(LocalDate dt_nascimento) {
		this.dt_nascimento = dt_nascimento;
	}	
	
	
}
