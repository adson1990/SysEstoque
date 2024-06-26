package com.adsonlucas.SysEstoque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adsonlucas.SysEstoque.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
