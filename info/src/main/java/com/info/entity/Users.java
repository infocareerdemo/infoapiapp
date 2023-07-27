package com.info.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class Users {
	@Id
	private int id;
	private String username;
	private String password;
	private String email;
	private String role;

}
