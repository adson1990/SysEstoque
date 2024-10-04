package com.adsonlucas.SysEstoque.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.adsonlucas.SysEstoque.entities.Vendas;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@Repository
public interface VendasRepository extends JpaRepository<Vendas, Long>{

	@EntityGraph(attributePaths = "codCli")
	@Query("SELECT p FROM Vendas p WHERE p.codCli = :id")
	Page<Vendas> findAllSalesByClientID(@Param("id") @NotNull @Positive Long id, Pageable pageable);

}
