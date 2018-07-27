package com.example.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String name);
    
}
