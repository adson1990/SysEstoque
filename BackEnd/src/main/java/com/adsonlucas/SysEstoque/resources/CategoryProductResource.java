package com.adsonlucas.SysEstoque.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adsonlucas.SysEstoque.entitiesDTO.CategoryProductDTO;
import com.adsonlucas.SysEstoque.services.CategoryProductService;

@RestController
@RequestMapping(value = "/categorie/product")
public class CategoryProductResource {
	
	@Autowired
	private CategoryProductService service;
	
	@GetMapping
	public ResponseEntity<Page<CategoryProductDTO>> findAllPages(Pageable pageable){
		Page<CategoryProductDTO> list = service.findAllPaged(pageable);
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryProductDTO> findByID(@PathVariable Long ID) {
		CategoryProductDTO dto = service.findByID(ID);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<CategoryProductDTO> insertCategoryProduct(@RequestBody CategoryProductDTO catDTO){
		catDTO = service.insCatProduct(catDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(catDTO.getID()).toUri();
		
		return ResponseEntity.created(uri).body(catDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryProductDTO> updateProduct(@PathVariable Long ID, @RequestBody CategoryProductDTO dto) {
		dto = service.updProduct(dto, ID);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long ID) {
		service.delProduct(ID);
		
		return ResponseEntity.noContent().build();
	}

}
