package com.info.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "user_kyc")
@AllArgsConstructor
@NoArgsConstructor
public class UserKYC {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull(message = "Firstname Required")
	private String firstname;
	private String lastname;
	@NotNull(message = "Email Required")
	private String email;
	@NotNull(message = "Phone number Required")
	private String phone;
	@NotNull(message = "Dob is Required")
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date dob;
	@NotNull(message = "Gender Required")
	private String gender;
	@NotNull(message = "Country Required")
	private String country;
	private String image;
	private String video;
	private String audio;
	
	

}
