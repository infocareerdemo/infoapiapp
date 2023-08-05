package com.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.info.entity.RefImage;
import com.info.entity.RefVideo;
import com.info.entity.UserKYC;

public interface RefVideoRepository extends JpaRepository<RefVideo, Integer>{

	RefVideo findByUserId(UserKYC id);

}
