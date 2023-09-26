package com.backend.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.restapi.models.PropertyRole;


@Repository
public interface PropertyRoleRepository extends JpaRepository<PropertyRole, Integer> {
	boolean existsByTypeName(String typeName);
	Optional<PropertyRole> findByTypeName(String typeName);
}