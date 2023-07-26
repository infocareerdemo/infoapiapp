package com.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.info.entity.StockPrice;

public interface Get_wdgwheelRepository extends JpaRepository<StockPrice, Integer>  
{

	@Query(value = "SELECT * from public.get_wdgwheel(:from, :to)", nativeQuery = true)
	List<Object> get_wdgwheel(@Param("from") int from, @Param("to") int to);
	
	@Query(value = "SELECT * from public.get_wdgwheel_deep(:from, :to)", nativeQuery = true)
	List<Object> get_wdgwheel_deep(@Param("from") int from, @Param("to") int to);
}
