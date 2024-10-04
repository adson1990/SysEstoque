package com.adsonlucas.SysEstoque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.entities.Vendas;
import com.adsonlucas.SysEstoque.entitiesDTO.VendasDTO;
import com.adsonlucas.SysEstoque.repositories.VendasRepository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public class VendasService {
	
	@Autowired
	private VendasRepository vendasRepository;

	@Transactional(readOnly = true)
	public Page<VendasDTO> findAllPages(PageRequest pageRequest) {
		Page<Vendas> pageList = vendasRepository.findAll(pageRequest);
		
		return pageList.map(x -> new VendasDTO(x.getCodPrd(), x.getCodCli(), x.getQtd(), 
							x.getDataVenda(), x.getTotal(), x.getPrcUnitario()));
	}

	public Page<VendasDTO> findByClientID(@NotNull @Positive Long ID, Pageable pageable) {
		Page<Vendas> allSales = vendasRepository.findAllSalesByClientID(ID, pageable);
		
		return allSales.map(x -> new VendasDTO(x.getCodPrd(), x.getCodCli(), x.getQtd(), 
							x.getDataVenda(), x.getTotal(), x.getPrcUnitario()));
	}

}
