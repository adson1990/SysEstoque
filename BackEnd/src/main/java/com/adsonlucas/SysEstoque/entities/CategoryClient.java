package com.adsonlucas.SysEstoque.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.adsonlucas.SysEstoque.entitiesDTO.CategoryClientDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_category_client")
public class CategoryClient implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	private String description;
	
	@ManyToMany(mappedBy = "categories")
	private Set<Client> clients = new HashSet<>();

	public CategoryClient() {}
	
	public CategoryClient(String desc) {
		super();
		this.description = desc;
	}
	
	public CategoryClient(CategoryClientDTO dto) {
		super();
		this.description = dto.getDescription();
	}

	public Long getID() {
		return ID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Client> getClients() {
		return clients;
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
