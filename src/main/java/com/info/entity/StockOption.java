package com.info.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "stock_options")
public class StockOption {
	
	   @Id
	   private Integer id;
	   @Column(name="instrument")
	   private String instrument;
	   @Column(name="symbol")
	   private String symbol;
	   @Column(name="expiry_dt")
	   private String expiry_dt;
	   @Column(name="strike_pr")
	   private Float strike_pr;
	   @Column(name="option_typ")
	   private String option_typ;
	   @Column(name="open")
	   private Float open;
	   @Column(name="high")
	   private Float high;
	   @Column(name="low")
	   private Float low;
	   @Column(name="close")
	   private Float close;
	   @Column(name="settle_pr")
	   private Float settle_pr;
	   @Column(name="contracts")
	   private Integer contracts;
	   @Column(name="val_inlakh")
	   private Float val_inlakh;
	   @Column(name="open_int")
	   private Integer open_int;
	   @Column(name="chg_in_oi")
	   private Integer chg_in_oi;
	   @Column(name="timestamp")
	   private String timestamp;
	   @Column(name="lastupdatedate")
	   private Date lastupdatedate;

}
