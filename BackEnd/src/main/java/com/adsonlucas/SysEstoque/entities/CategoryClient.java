package com.adsonlucas.SysEstoque.entities;

import java.io.Serializable;
import java.util.Objects;

import com.adsonlucas.SysEstoque.entitiesDTO.CategoryClientDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_category_client")
public class CategoryClient implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	private String description;

	public CategoryClient() {}
	
	public CategoryClient(Long ID, String desc) {
		super();
		this.ID = ID;
		this.description = desc;
	}
	
	public CategoryClient(CategoryClientDTO dto) {
		super();
		ID = dto.getID();
		this.description = dto.getDescrption();
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		CategoryClient other = (CategoryClient) obj;
		return Objects.equals(ID, other.ID);
	}
	
	
}
