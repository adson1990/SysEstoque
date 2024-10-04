package com.adsonlucas.SysEstoque.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adsonlucas.SysEstoque.entitiesDTO.VendasDTO;
import com.adsonlucas.SysEstoque.services.VendasService;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping(value = "/vendas")
public class VendasController {
	
	@Autowired
	private VendasService vendasService;
	
	@GetMapping
	public ResponseEntity<Page<VendasDTO>> findAll(
	@RequestParam(value = "page", defaultValue = "0") Integer page,
	@RequestParam(value = "linesPerPage", defaultValue = "4") Integer linesPerPage,
	@RequestParam(value = "direction", defaultValue = "ASC") String direction,
	@RequestParam(value = "orderBy", defaultValue = "ID") String orderBY
	){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBY);
		
		Page<VendasDTO> list = vendasService.findAllPages(pageRequest);
	
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/client/{ID}")
	public ResponseEntity<Page<VendasDTO>> findAllByClientID(
			@PathVariable @NotNull @Positive Long ID,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "3") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "ID") String orderBY
		){
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBY);
			Page<VendasDTO> list = vendasService.findByClientID(ID, pageRequest);
			return ResponseEntity.ok().body(list);
		}

}
