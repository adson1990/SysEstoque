package com.adsonlucas.SysEstoque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adsonlucas.SysEstoque.entities.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {

	Roles findByAuthority(String authority);

}
