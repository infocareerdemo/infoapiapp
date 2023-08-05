package com.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.info.entity.RefImage;
import com.info.entity.UserKYC;

public interface RefImageRepository extends JpaRepository<RefImage, Integer>{

	RefImage findByUserId(UserKYC id);

}
