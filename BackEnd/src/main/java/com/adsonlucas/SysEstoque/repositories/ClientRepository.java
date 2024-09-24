package com.adsonlucas.SysEstoque.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adsonlucas.SysEstoque.entities.Client;

import jakarta.validation.constraints.NotNull;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	Optional<Client> findByCpf(String cpf);
	
	@EntityGraph(attributePaths = "enderecos")
	@Query("SELECT p FROM Client p WHERE p.id = :id")
	Client findPessoaWithEnderecos(@Param("id") Long id);
	@EntityGraph(attributePaths = "cellphone")
	@Query("SELECT p FROM Client p WHERE p.id = :id")
	Client findPessoaWithCelphone(@Param("id") Long id);

	@EntityGraph(attributePaths = "email")
	@Query("SELECT p FROM Client p WHERE p.email = :email")
	Optional<Client> findByEmail(@NotNull String email);

}
