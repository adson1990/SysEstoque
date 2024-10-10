package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendasDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long ID;
	private Long codPrd;
	private String name;
	private Long codCli;
	private int qtd;
	private Date dataVenda;
	private Double valor;
	private Double prcUnitario;
	
	public VendasDTO() {}

	public VendasDTO(Long codPrd, String name, Long codCli, int qtd, Date date, Double valor, Double prcUnitario) {
		super();
		this.codPrd = codPrd;
		this.name = name;
		this.codCli = codCli;
		this.qtd = qtd;
		this.dataVenda = date;
		this.valor = valor;
		this.prcUnitario = prcUnitario;
	}

	public VendasDTO(Long iD, String name, Long codPrd, Long codCli, int qtd, Date dataVenda, Double valor, Double prcUnitario) {
		super();
		ID = iD;
		this.codPrd = codPrd;
		this.name = name;
		this.codCli = codCli;
		this.qtd = qtd;
		this.dataVenda = dataVenda;
		this.valor = valor;
		this.prcUnitario = prcUnitario;
	}

	public Long getID() {
		return ID;
	}

	public Long getCodPrd() {
		return codPrd;
	}

	public void setCodPrd(Long codPrd) {
		this.codPrd = codPrd;
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getPrcUnitario() {
		return prcUnitario;
	}

	public void setPrcUnitario(Double prcUnitario) {
		this.prcUnitario = prcUnitario;
	}

}
