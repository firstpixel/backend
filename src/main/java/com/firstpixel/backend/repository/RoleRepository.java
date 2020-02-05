package com.firstpixel.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.firstpixel.backend.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}