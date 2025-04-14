package com.adsonlucas.SysEstoque.entitiesDTO;

public class CompraItemResumoDTO {

	private String productName;
	private String description;
    private Integer quantidade;
    private Double prcUnitario;
    private String categoryNames;
    
    public CompraItemResumoDTO() {}

	public CompraItemResumoDTO(String productName, String description, Integer quantidade, Double prcUnitario, String categoryNames) {
		super();
		this.productName = productName;
		this.description = description;
		this.quantidade = quantidade;
		this.prcUnitario = prcUnitario;
		this.categoryNames = categoryNames;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPrcUnitario() {
		return prcUnitario;
	}

	public void setPrcUnitario(Double prcUnitario) {
		this.prcUnitario = prcUnitario;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}
    
}