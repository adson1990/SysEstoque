package com.adsonlucas.SysEstoque.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adsonlucas.SysEstoque.entitiesDTO.ProductDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.services.ProductServices;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	@Autowired
	private ProductServices productService;
	
	//Select all
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(
	@RequestParam(value = "page", defaultValue = "0") Integer page,
	@RequestParam(value = "linesPerPage", defaultValue = "7") Integer linesPerPage,
	@RequestParam(value = "direction", defaultValue = "ASC") String direction,
	@RequestParam(value = "orderBy", defaultValue = "ID") String orderBY
	){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBY);
		
		Page<ProductDTO> list = productService.findAllPages(pageRequest);
	
		return ResponseEntity.ok().body(list);
	}
	
	// Select By ID
	@GetMapping(value = "/{ID}")
	public ResponseEntity<ProductDTO> findProdutoById(@PathVariable @NotNull @Positive Long ID){
		ProductDTO produtoDTO = productService.findById(ID);
	
		return ResponseEntity.ok().body(produtoDTO);
	}
	
	 @GetMapping("/search")
	    public Page<ProductDTO> searchProducts(
	            @RequestParam(required = false) String name,
	            @RequestParam(required = false) String orderBy,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "5") int size) {
	        
	        return productService.searchProducts(name, orderBy, page, size);
	    }
	
	// Insert
	@PostMapping
	public ResponseEntity<ProductDTO> insertProduto(@Valid @RequestBody ProductDTO dto){
		dto = productService.insProduct(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getID()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	// Update
	@PutMapping(value = "/{ID}")
	public ResponseEntity<ProductDTO> updateProduto(@Valid @PathVariable Long ID, @RequestBody ProductDTO dto) {
		dto = productService.updProduct(dto, ID);
		
		return ResponseEntity.ok().body(dto);
	}
	
	//DELETE
	@DeleteMapping(value = "/{ID}")
	public ResponseEntity<String> deleteProduto(@PathVariable @NotNull @Positive Long ID) {
		try {
			productService.delProduct(ID);
			  return ResponseEntity.ok("Produto com o ID " + ID +" deletado com sucesso!");
		}catch(EntidadeNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}catch (DataIntegrityViolationException d) {
			throw new DataBaseException("Violação de integridade do DB");
		}
		
		//return ResponseEntity.noContent().build();
	}

}
