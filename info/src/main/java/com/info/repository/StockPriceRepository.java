package com.info.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

	@Query(value = "select * from stock_price sp where tdclose <= :tdclose", nativeQuery = true)
	Slice<StockPrice> findAllByTdcloseLesserThen(@Param(value = "tdclose") int tdclose, Pageable pageable);

	@Query(value = "select * from stock_price sp where spsymbol =:spsymbol and spinstrument =:spinstrument", nativeQuery = true)
	StockPrice findBySpsymbolAndSpinstrument(@Param(value = "spsymbol") String spsymbol,
			@Param(value = "spinstrument") String spinstrument);

	List<StockPrice> findBySpinstrument(String spinstrument);

	@Query("SELECT sp FROM StockPrice sp WHERE CONCAT(sp.spid,' ', sp.spisin,' ', sp.spinstrument,' ', sp.spsymbol,' ') ILIKE %?1%")
    public List<StockPrice> searchList(String keyword);

//	@Query(value = "SELECT sp FROM StockPrice sp WHERE CONCAT(sp.spid, ' ', sp.spisin, ' ', sp.spinstrument, ' ', sp.spsymbol, ' ')ILIKE '%' || ?1 ||'%'", nativeQuery = true)
//	public List<StockPrice> searchList(String keyword);
	
	@Query("SELECT sp FROM StockPrice sp WHERE CONCAT(sp.spsymbol,' ') ILIKE %?1%")
    public List<StockPrice> searchSymbol(String symbol);
	
	
	/*@Query("SELECT sp FROM StockPrice sp WHERE sp.spsymbol IN " +
		       "(SELECT DISTINCT sps.spsymbol FROM StockPrice sps WHERE CONCAT(sps.spsymbol, ' ') ILIKE %?1%)")
		List<StockPrice> getUniqueSpSymbol(@Param(value = "spsymbol") String spsymbol);*/

	 /*@Query("SELECT DISTINCT sp.spsymbol FROM StockPrice sp WHERE CONCAT(sp.spsymbol, ' ') ILIKE %?1%")
	    List<StockPrice> getUniqueSpSymbol(String spsymbol);*/
	
	/* @Query("SELECT sp FROM StockPrice sp WHERE sp.spsymbol IN " +
	         "(SELECT DISTINCT sps.spsymbol FROM StockPrice sps WHERE CONCAT(sps.spsymbol, ' ') ILIKE %?1%)")
	    
	    
	
	
			
	/*@Query("SELECT DISTINCT sp FROM StockPrice sp WHERE sp.spsymbol IN " +
	          "(SELECT DISTINCT sp.spsymbol FROM StockPrice sp WHERE CONCAT(sp.spsymbol, ' ') ILIKE %?1%)")*/
	/* @Query("SELECT sp FROM StockPrice sp " +
	           "WHERE sp.spsymbol = :spsymbol " +
	           "ORDER BY sp.id ASC")*/
	  // List<StockPrice> getUniqueSpSymbol(@Param(value = "spsymbol") String spsymbol);
	
//	@Query("SELECT sp FROM StockPrice sp " +
//	           "WHERE sp.spsymbol = :spsymbol " +
//	           "ORDER BY sp.id ASC") 
	
//	   @Query(value = "SELECT * FROM stock_price WHERE spsymbol LIMIT 1")		
//	   StockPrice getUniqueSpSymbol(@Param(value = "spsymbol") String spsymbol);
	   
	
	
	 /*  @Query(value = "SELECT * FROM stock_price WHERE spsymbol = :spsymbol ORDER BY spid LIMIT 1", nativeQuery = true)
	   StockPrice getUniqueSpSymbol(@Param(value = "spsymbol") String spsymbol);*/
	
	/* @Query(value = "SELECT DISTINCT ON (spsymbol) * FROM stock_price WHERE spsymbol = :spsymbol ORDER BY spsymbol, spid", nativeQuery = true)
	 StockPrice getUniqueSpSymbol(@Param(value = "symbol") String symbol);*/

	 @Query(value = "SELECT DISTINCT ON (spsymbol) * FROM stock_price WHERE spsymbol = :symbol ORDER BY spsymbol, spid", nativeQuery = true)
	 List<StockPrice> getUniqueSpSymbol(@Param(value = "symbol") String symbol);

	 List<StockPrice> findBySpsymbol(String spSym);


}
