package com.adsonlucas.SysEstoque.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.adsonlucas.SysEstoque.entitiesDTO.RolesDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_roles")
public class Roles implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	private String authority;
	
	@ManyToMany(mappedBy = "roles")
	Set<User> users = new HashSet<>();
	
	public Roles() {}

	public Roles(String authority) {
		super();
		this.authority = authority;
	}

	public Roles(RolesDTO dto) {
		this.authority = dto.getAuthority();
	}

	public Long getID() {
		return ID;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
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
		Roles other = (Roles) obj;
		return Objects.equals(ID, other.ID);
	}
	
	
}
