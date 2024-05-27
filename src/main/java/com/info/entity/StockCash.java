package com.info.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "stock_cash")
public class StockCash {
	@Id
	private int id;
	@Column(name ="symbol")
	private String symbol;
	@Column(name ="series")
	private String series;
	@Column(name ="open")
	private Float open;
	@Column(name ="high")
	private Float high;
	@Column(name ="low")
	private Float low;
	@Column(name ="close")
	private Float close;
	@Column(name ="prevclose")
	private Float prevclose;
	@Column(name ="tottrdqty")
	private Integer tottrdqty;
	@Column(name ="tottrdval")
	private Float tottrdval;
	@Column(name ="timestamp")
	private String timestamp;
	@Column(name ="totaltrades")
	private Integer totaltrades;
	@Column(name ="isin")
	private String isin;
	@Column(name ="lastupdatedate")
	private String lastupdatedate;

	
}
