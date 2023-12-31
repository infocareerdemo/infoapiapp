package com.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.info.entity.UserKYC;

import lombok.NonNull;

public interface UserKYCRepository extends JpaRepository<UserKYC, Integer> {

	UserKYC findByEmail(String email);

	UserKYC findByPhone(String phone);

}
