package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;
import java.util.Objects;

public class CategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long ID;
	private String descrption;
	
	public CategoryDTO() {}

	public CategoryDTO(Long iD, String descrption) {
		super();
		ID = iD;
		this.descrption = descrption;
	}

	public Long getID() {
		return ID;
	}

	public String getDescrption() {
		return descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
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
		CategoryDTO other = (CategoryDTO) obj;
		return Objects.equals(ID, other.ID);
	}
	
	

}
