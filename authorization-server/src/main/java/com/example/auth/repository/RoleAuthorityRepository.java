package com.example.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.entity.Role;
import com.example.auth.entity.RoleAuthority;

public interface RoleAuthorityRepository extends JpaRepository<RoleAuthority, Integer> {

    List<RoleAuthority> findAllByRole(Role role);
}