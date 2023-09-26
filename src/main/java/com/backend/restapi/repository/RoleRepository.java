package com.backend.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.restapi.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
