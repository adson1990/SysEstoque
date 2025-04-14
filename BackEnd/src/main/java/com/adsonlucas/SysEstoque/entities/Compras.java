package com.adsonlucas.SysEstoque.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "tb_compras")
public class Compras implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cod_cli")
    private Client cliente;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<CompraItem> itens;

    private Date dataCompra;
	
	public Compras() {}

	public Compras(Client cliente, List<CompraItem> itens, Date dataCompra) {
		super();
		this.cliente = cliente;
		this.itens = itens;
		this.dataCompra = dataCompra;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getCliente() {
		return cliente;
	}

	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}

	public List<CompraItem> getItens() {
		return itens;
	}

	public void setItens(List<CompraItem> itens) {
		this.itens = itens;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
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
		Compras other = (Compras) obj;
		return Objects.equals(id, other.id);
	}

	
}
