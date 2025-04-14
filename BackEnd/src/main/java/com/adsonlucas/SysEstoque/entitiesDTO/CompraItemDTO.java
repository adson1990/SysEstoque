package com.adsonlucas.SysEstoque.entitiesDTO;

import java.math.BigDecimal;

import com.adsonlucas.SysEstoque.entities.Compras;

public class CompraItemDTO {
	
	private Long id;
	private Compras compra;
	private Long produtoId;
	private Long prdCategoria;
	private BigDecimal preco;
	private int quantidade;
	
	public CompraItemDTO() {
	}

	public CompraItemDTO(Compras compra, Long produtoId, Long categoria, BigDecimal preco, int quantidade) {
		super();
		this.compra = compra;
		this.produtoId = produtoId;
		this.prdCategoria = categoria;
		this.preco = preco;
		this.quantidade = quantidade;
	}

	public Compras getCompra() {
		return compra;
	}

	public void setCompra(Compras compra) {
		this.compra = compra;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Long getPrdCategoria() {
		return prdCategoria;
	}

	public void setPrdCategoria(Long prdCategoria) {
		this.prdCategoria = prdCategoria;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Long getId() {
		return id;
	}
	

}
