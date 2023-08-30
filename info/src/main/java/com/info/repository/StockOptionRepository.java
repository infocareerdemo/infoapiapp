package com.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.info.entity.StockOption;

@Repository
public interface StockOptionRepository extends JpaRepository<StockOption, Integer>, CrudRepository<StockOption, Integer> {
	
	@Query(value = "SELECT * FROM stock_options sc WHERE symbol =:symbol AND timestamp = (SELECT MAX(timestamp) FROM stock_options WHERE symbol =:symbol)",nativeQuery = true)
	List<StockOption> findBySymbol(@Param(value = "symbol")String symbol);

	@Query(value = "SELECT DISTINCT (symbol) FROM stock_options ", nativeQuery = true)
	List<String> findAllSymbols();

}
