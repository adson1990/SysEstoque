package com.adsonlucas.SysEstoque.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adsonlucas.SysEstoque.repositories.CategoryRepository;

@RestController
@RequestMapping(value = "/categories")
public class CategoryClientResource {
	
	@Autowired
	private CategoryRepository repository;
	
	

}
