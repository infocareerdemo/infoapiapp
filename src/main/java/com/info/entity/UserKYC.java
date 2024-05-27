package com.info.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
	@Column(name ="user_kyc_id")
	private int id;
	@NotNull(message = "Firstname is Required")
	@NotEmpty(message = "Firstname is Required")
	private String firstname;
	private String lastname;
	@NotEmpty(message = "Email is Required")
	@Email(message = "Invalid Email Address")
	private String email;
	@NotNull(message = "Phone number is Required")
	@NotEmpty(message = "Phone number is Required")
	@Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
	private String phone;
	@NotNull(message = "Dob is Required")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dob;
	@NotNull(message = "Gender is Required")
	@NotEmpty(message = "Gender is Required")
	private String gender;
	@NotBlank(message = "Country is Required")
	private String country;
	private String image;
	private String video;

}
