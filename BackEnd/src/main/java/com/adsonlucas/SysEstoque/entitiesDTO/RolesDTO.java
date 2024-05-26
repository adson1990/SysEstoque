package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;
import java.util.Objects;

import com.adsonlucas.SysEstoque.entities.Roles;

public class RolesDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long ID;
	private String authority;
	
	public RolesDTO () {
		
	}
	
	public RolesDTO (Long ID, String authority) {
		this.ID = ID;
		this.authority = authority;
	}

	public RolesDTO(Roles role) {
		this.ID = role.getID();
		this.authority = role.getAuthority();
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Long getID() {
		return ID;
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
		RolesDTO other = (RolesDTO) obj;
		return Objects.equals(ID, other.ID);
	}
	
	
}
