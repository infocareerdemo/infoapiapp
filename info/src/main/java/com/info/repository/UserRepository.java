package com.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.info.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

}
