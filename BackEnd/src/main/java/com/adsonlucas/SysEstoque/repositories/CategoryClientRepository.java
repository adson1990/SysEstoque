package com.adsonlucas.SysEstoque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adsonlucas.SysEstoque.entities.CategoryClient;

@Repository
public interface CategoryClientRepository extends JpaRepository<CategoryClient, Long> {

}
