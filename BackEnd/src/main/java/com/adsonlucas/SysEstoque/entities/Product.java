package com.adsonlucas.SysEstoque.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.adsonlucas.SysEstoque.entitiesDTO.ProductDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	private String name;
	private Double price;
	private String imgUrl;
	private String description;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime dtIncluded;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "tb_product_category",
			   joinColumns = @JoinColumn(name = "product_id"),
			   inverseJoinColumns = @JoinColumn(name = "categoryProduct_id")
			   )
	Set<CategoryProduct> categories = new HashSet<>();
	
	public Product() {
	}

	public Product(String name, Double preco, LocalDateTime dtIncluded, String description, String imgUrl) {
		super();
		this.name = name;
		this.price = preco;
		this.dtIncluded = dtIncluded;
		this.description = description;
		this.imgUrl = imgUrl;
	}
	public Product(ProductDTO productDTO) {
		this.name = productDTO.getName();
		this.price = productDTO.getPrice();
		this.dtIncluded = LocalDateTime.now();
		this.imgUrl = productDTO.getImgUrl();
		this.description = productDTO.getDescription();
	}
	
	public Product(ProductDTO produto, Set<CategoryProduct> listCategory) {
		this(produto);
		listCategory.forEach(cat -> this.categories.add(new CategoryProduct(cat.getDescription())));
	}
	
	public Product(ProductDTO produto, Long ID) {
		this(produto);
		
		if (ID != null) {
		  this.ID = ID;
		}
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

	public LocalDateTime getDtIncluded() {
		return dtIncluded;
	}

	public void setDtIncluded(LocalDateTime dtIncluded) {
		this.dtIncluded = dtIncluded;
	}

	public Set<CategoryProduct> getCategories() {
		return categories;
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
		Product other = (Product) obj;
		return Objects.equals(ID, other.ID);
	}
	
	

}
