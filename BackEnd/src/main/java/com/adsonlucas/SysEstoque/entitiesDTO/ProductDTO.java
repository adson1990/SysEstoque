package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.adsonlucas.SysEstoque.entities.CategoryProduct;
import com.adsonlucas.SysEstoque.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long ID;
	@Size(min = 5, max = 15, message = "O nome deve ter entre 5 e 60 caracteres.")
	@NotBlank(message = "Nome não pode ser nulo.")
	private String name;
	@Positive(message = "Preço deve ser valor positivo.")
	private Double price;
	@PastOrPresent(message = "Data do produto não pode ser posterior a data atual.")
	private LocalDateTime dtIncluded;
	private String imgUrl;
	@Length(max = 50, message = "Descrição deve conter no máximo 50 caracteres.")
	private String description;
	
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
		this.dtIncluded = client.getDtIncluded();
		this.price = client.getPrice();
		this.imgUrl = client.getImgUrl();
		this.description = client.getDescription();
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDateTime getDtIncluded() {
		return dtIncluded;
	}

	public void setDtIncluded(LocalDateTime dtIncluded) {
		this.dtIncluded = dtIncluded;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<CategoryProductDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryProductDTO> categories) {
		this.categories = categories;
	}

}
