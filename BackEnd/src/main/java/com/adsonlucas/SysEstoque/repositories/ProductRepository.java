package com.adsonlucas.SysEstoque.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adsonlucas.SysEstoque.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	 @Query("SELECT p FROM Product p WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))")
	    Page<Product> findByName(@Param("name") String name, Pageable pageable);

}
