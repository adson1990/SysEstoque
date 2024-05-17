package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.adsonlucas.SysEstoque.entities.CategoryProduct;
import com.adsonlucas.SysEstoque.entities.Product;

public class ProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long ID;
	private String name;
	private Double price;
	private LocalDateTime dtIncluded;
	
	private List<CategoryProductDTO> categories = new ArrayList<>();
	
	public ProductDTO() {
	}

	public ProductDTO(String name, Double preco, LocalDateTime dtIncluded) {
		super();
		this.name = name;
		this.price = preco;
		this.dtIncluded = dtIncluded;
	}

	public ProductDTO(Product client) {
		super();
		this.ID = client.getID();
		this.name = client.getName();
		this.price = client.getPreco();
		this.dtIncluded = client.getDtInclusao();
	}
	
	public ProductDTO(Product entity, Set<CategoryProduct> categories) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryProductDTO(cat)));
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

	public Double getPreco() {
		return price;
	}

	public void setPreco(Double preco) {
		this.price = preco;
	}

	public LocalDateTime getDtInclusao() {
		return dtIncluded;
	}

	public void setDtInclusao(LocalDateTime dtIncluded) {
		this.dtIncluded = dtIncluded;
	}
	
	public List<CategoryProductDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryProductDTO> categories) {
		this.categories = categories;
	}

}
