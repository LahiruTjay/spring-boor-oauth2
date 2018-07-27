package com.example.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
