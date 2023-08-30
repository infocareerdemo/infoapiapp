package com.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.info.entity.StockFuture;
import com.info.entity.StockPrice;

@Repository
public interface StockFutureRepository extends JpaRepository<StockFuture, Integer>, CrudRepository<StockFuture, Integer>{

	@Query(value = "SELECT * FROM stock_futures sc WHERE symbol =:symbol AND timestamp = (SELECT MAX(timestamp) FROM stock_futures WHERE symbol =:symbol)",nativeQuery = true)
	List<StockFuture> findBySymbol(@Param(value = "symbol")String symbol);
	
	@Query(value = "SELECT DISTINCT (symbol) FROM stock_futures ", nativeQuery = true)
	List<String> findAllSymbols();
}


