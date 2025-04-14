package com.adsonlucas.SysEstoque.entitiesDTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompraAgrupadaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String clientName;
    private LocalDateTime dataCompra;
    private Double price;
    private List<CompraItemResumoDTO> itens;
    
    public CompraAgrupadaDTO(){}

	public CompraAgrupadaDTO(String clientName, LocalDateTime dataCompra, Double priceTotal, List<CompraItemResumoDTO> itens) {
		super();
		this.clientName = clientName;
		this.dataCompra = dataCompra;
		this.price = priceTotal;
		this.itens = itens;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public LocalDateTime getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDateTime dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Double getPriceTotal() {
		return price;
	}

	public void setPriceTotal(Double priceTotal) {
		this.price = priceTotal;
	}

	public List<CompraItemResumoDTO> getItens() {
		return itens;
	}

	public void setItens(List<CompraItemResumoDTO> itens) {
		this.itens = itens;
	}
	
}