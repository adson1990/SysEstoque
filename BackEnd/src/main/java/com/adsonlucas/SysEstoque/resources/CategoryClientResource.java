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

import com.adsonlucas.SysEstoque.entitiesDTO.CategoryClientDTO;
import com.adsonlucas.SysEstoque.services.CategoryClientService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryClientResource {
	
	@Autowired
	private CategoryClientService service;
	
	@GetMapping
	public ResponseEntity<Page<CategoryClientDTO>> findAllPages(Pageable pageable){
		Page<CategoryClientDTO> list = service.findAllPaged(pageable);
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping
	public ResponseEntity<CategoryClientDTO> findByID(@PathVariable Long ID) {
		CategoryClientDTO dto = service.findByID(ID);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<CategoryClientDTO> insertCategoryClient(@RequestBody CategoryClientDTO catDTO){
		catDTO = service.insCatClient(catDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(catDTO.getID()).toUri();
		
		return ResponseEntity.created(uri).body(catDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryClientDTO> updateClient(@PathVariable Long ID, @RequestBody CategoryClientDTO dto) {
		dto = service.updClient(dto, ID);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Void> deleteClient(@PathVariable Long ID) {
		service.delClient(ID);
		
		return ResponseEntity.noContent().build();
	}

}
