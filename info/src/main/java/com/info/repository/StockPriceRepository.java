package com.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.info.entity.StockPrice;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Integer>, CrudRepository<StockPrice, Integer> {

	@Query(value = "SELECT * FROM stock_price where spid=:idValue", nativeQuery = true)
	StockPrice listAllStockPrices(@Param(value = "idValue") int idValue);

	@Query(value = "SELECT * FROM stock_price where spsymbol=:spsymbolName and spinstrument='EQ' ", nativeQuery = true)
	StockPrice getStockPricesByName(@Param(value = "spsymbolName") String spsymbolName);
	
	  @Query(value = "SELECT * FROM stock_price", nativeQuery = true)
	  List<StockPrice> listAllStockPricesObjectDatatype();
	  
	  
	 
 
	  
}
