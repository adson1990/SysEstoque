package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendasDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long ID;
	private Long codPrd;
	private Long codCli;
	private int qtd;
	private Date dataVenda;
	private Double prcUnitario;
	
	public VendasDTO() {}

	public VendasDTO(Long codPrd, Long codCli, int qtd, Date date, Double prcUnitario) {
		super();
		this.codPrd = codPrd;
		this.codCli = codCli;
		this.qtd = qtd;
		this.dataVenda = date;
		this.prcUnitario = prcUnitario;
	}

	public VendasDTO(Long iD, Long codPrd, Long codCli, int qtd, Date dataVenda, Double prcUnitario) {
		super();
		ID = iD;
		this.codPrd = codPrd;
		this.codCli = codCli;
		this.qtd = qtd;
		this.dataVenda = dataVenda;
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

	public Double getPrcUnitario() {
		return prcUnitario;
	}

	public void setPrcUnitario(Double prcUnitario) {
		this.prcUnitario = prcUnitario;
	}

}
