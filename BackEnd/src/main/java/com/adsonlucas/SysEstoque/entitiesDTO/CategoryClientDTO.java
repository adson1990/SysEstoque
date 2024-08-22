package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;
import java.util.Objects;

import com.adsonlucas.SysEstoque.entities.CategoryClient;

public class CategoryClientDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long ID;
	private String description;
	
	public CategoryClientDTO() {}

	public CategoryClientDTO(Long iD, String descrption) {
		super();
		ID = iD;
		this.description = descrption;
	}
	
	public CategoryClientDTO(Long iD) {
		super();
		ID = iD;
	}
	
	public CategoryClientDTO(CategoryClient cat) {
		super();
		ID = cat.getID();
		this.description = cat.getDescription();
	}

	public Long getID() {
		return ID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescrption(String descrption) {
		this.description = descrption;
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
		CategoryClientDTO other = (CategoryClientDTO) obj;
		return Objects.equals(ID, other.ID);
	}
	
	

}
