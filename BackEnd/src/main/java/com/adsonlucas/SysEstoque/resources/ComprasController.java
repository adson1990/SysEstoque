package com.adsonlucas.SysEstoque.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adsonlucas.SysEstoque.entitiesDTO.CompraAgrupadaDTO;
import com.adsonlucas.SysEstoque.services.ComprasService;

@RestController
@RequestMapping(value = "/compras")
public class ComprasController {
	
	@Autowired
	private ComprasService comprasService;
	
	@GetMapping("/cliente/{codCli}")
	public ResponseEntity<List<CompraAgrupadaDTO>> listarVendasPorCliente(@PathVariable Long codCli) {
	    List<CompraAgrupadaDTO> compras = comprasService.listarVendasPorCliente(codCli);
	    return ResponseEntity.ok(compras);
	}

}
