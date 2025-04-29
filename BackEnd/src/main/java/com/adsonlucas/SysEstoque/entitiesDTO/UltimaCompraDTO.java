package com.adsonlucas.SysEstoque.entitiesDTO;

import java.math.BigDecimal;
import java.util.Date;

public class UltimaCompraDTO {
	    private Date dataVenda;
	    private BigDecimal valor;

	    public UltimaCompraDTO(Date dataVenda, BigDecimal valor) {
	        this.dataVenda = dataVenda;
	        this.valor = valor;
	    }

		public Date getDataVenda() {
			return dataVenda;
		}

		public void setDataVenda(Date dataVenda) {
			this.dataVenda = dataVenda;
		}

		public BigDecimal getValor() {
			return valor;
		}

		public void setValor(BigDecimal valor) {
			this.valor = valor;
		}	    
    
}

