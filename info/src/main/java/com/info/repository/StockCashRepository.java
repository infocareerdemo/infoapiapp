package com.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.info.entity.StockCash;

public interface StockCashRepository extends JpaRepository<StockCash, Integer> {

	@Query(value = "select  * from stock_cash sc where symbol =:symbol and series =:series and timestamp =:timestamp", nativeQuery = true)
	StockCash findBySymbolAndSeriesAndTimestamp(@Param(value = "symbol") String symbol,
			@Param(value = "series") String series, @Param(value = "timestamp") String timestamp);

	List<StockCash> findBySeriesAndTimestamp(String series, String timestamp);

	
	// add by anu

	@Query(value = "SELECT * FROM stock_cash sc where sc.symbol=:symbolname", nativeQuery = true)
	List<StockCash> getAllRelianceData(@Param(value = "symbolname") String symbolname);

	
	@Query(value = "SELECT * FROM stock_cash sc WHERE symbol = :symbol AND timestamp = (SELECT MAX(timestamp) FROM stock_cash WHERE symbol =:symbol)",nativeQuery = true)
	List<StockCash> findBySymbol(@Param(value = "symbol")String symbol);
	       
    
}
