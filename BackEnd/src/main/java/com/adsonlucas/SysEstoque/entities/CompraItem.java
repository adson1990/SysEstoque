package com.adsonlucas.SysEstoque.entities;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_compras_itens")
public class CompraItem {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Product produto;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compras compra;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoryProduct prdCategoria;

	public CompraItem(Integer quantidade, BigDecimal preco, Product produto, Compras compra,
				      CategoryProduct prdCategoria) {
		super();
		this.quantidade = quantidade;
		this.preco = preco;
		this.produto = produto;
		this.compra = compra;
		this.prdCategoria = prdCategoria;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Product getProduto() {
		return produto;
	}

	public void setProduto(Product produto) {
		this.produto = produto;
	}

	public Compras getCompra() {
		return compra;
	}

	public void setCompra(Compras compra) {
		this.compra = compra;
	}

	public CategoryProduct getPrdCategoria() {
		return prdCategoria;
	}

	public void setPrdCategoria(CategoryProduct prdCategoria) {
		this.prdCategoria = prdCategoria;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompraItem other = (CompraItem) obj;
		return Objects.equals(id, other.id);
	}
    
    
}