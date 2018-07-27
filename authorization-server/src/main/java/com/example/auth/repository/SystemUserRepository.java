package com.example.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.entity.SystemUser;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    SystemUser findByUsernameIgnoreCase(String username);

}
