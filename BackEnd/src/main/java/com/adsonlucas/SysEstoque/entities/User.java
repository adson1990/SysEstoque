package com.adsonlucas.SysEstoque.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.adsonlucas.SysEstoque.entitiesDTO.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
public class User implements UserDetails, Serializable {
	private static final long serialVersionUID = 1L;
	
	// VARIÁVEIS
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	private String nome;
	private String sobrenome;
	private String senha;
	private String email;
	private Integer idade;
	private String foto;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDate dt_nascimento;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createAt;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updateAt;
	
	@ManyToMany
	@JoinTable(name = "tb_user_role",
			   joinColumns = @JoinColumn(name = "user_id"),
			   inverseJoinColumns = @JoinColumn(name = "role_id")
			   )
	Set<Roles> roles = new HashSet<>();
	
	//CONSTRUTORES	
	public User() {}

	public User(String nome, String sobrenome, String senha, String email, Integer idade, String foto,
			LocalDate dt_nascimento) {
		super();
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.senha = senha;
		this.email = email;
		this.idade = idade;
		this.foto = foto;
		this.dt_nascimento = dt_nascimento;
	}
	
	public User(UserDTO dto) {
		super();
		this.nome = dto.getNome();
		this.sobrenome = dto.getSobrenome();
		this.senha = dto.getSenha();
		this.email = dto.getEmail();
		this.idade = dto.getIdade();
		this.foto = dto.getFoto();
		this.dt_nascimento = dto.getDt_nascimento();
	}
	
	public User(UserDTO dto, Set<Roles> listRoles) {
		this(dto);
		
		listRoles.forEach(rol -> this.roles.add(new Roles(rol.getAuthority())));
	}
	
	public User(UserDTO dto, Long ID) {
		this(dto);
		
		if (ID != null) {
			this.ID = ID;
		}
	}

	//MÉTODOS
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

	// finalidade auditoria, inserção e atualização de cadastro
	public Instant getCreatedAt() {
		return createAt; //gravação de horário de inclusão de dados
	}	
	public Instant getUpdatedAt() {
		return updateAt; // gravação de horário de atualização de dados
	}
		
	@PrePersist
	public void prePersist() {
		createAt = Instant.now(); // antes de gravar no banco receberá o horário atual
	}	
	@PreUpdate
	public void preUpdate() {
		updateAt = Instant.now(); // antes de atualizar no banco receberá o horário atual
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(ID, other.ID);
	}

	// implementação dos métodos de user detail
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

}
