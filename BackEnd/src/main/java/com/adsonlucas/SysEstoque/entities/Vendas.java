package com.adsonlucas.SysEstoque.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_vendas")
public class Vendas implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	@Column(nullable = false)
	private Long codPrd;
	@Column(nullable = false)
	private Long codCli;
	@Column(nullable = false)
	private int qtd;
	@Column(nullable = false)
	private Date dataVenda;
	@Column(nullable = false)
	private Double total;
	@Column(nullable = false)
	private Double prcUnitario;
	
	public Vendas() {}
	
	
	public Vendas(Long codPrd, Long codCli, int qtd, Date dataVenda, double valor, double prcUnitario) {
		super();
		this.codPrd = codPrd;
		this.codCli = codCli;
		this.qtd = qtd;
		this.dataVenda = dataVenda;
		this.total = valor;
		this.prcUnitario = prcUnitario;
	}
	

	public Long getCodPrd() {
		return codPrd;
	}
	public void setCodPrd(Long codPrd) {
		this.codPrd = codPrd;
	}
	public Long getCodCli() {
		return codCli;
	}
	public void setCodCli(Long codCli) {
		this.codCli = codCli;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	public Date getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double valor) {
		this.total = valor;
	}
	public double getPrcUnitario() {
		return prcUnitario;
	}
	public void setPrcUnitario(double prcUnitario) {
		this.prcUnitario = prcUnitario;
	}
	public Long getID() {
		return ID;
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
		Vendas other = (Vendas) obj;
		return Objects.equals(ID, other.ID);
	}
	

}
