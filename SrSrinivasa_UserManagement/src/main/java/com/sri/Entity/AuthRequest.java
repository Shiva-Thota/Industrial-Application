package com.sri.Entity;

import java.io.Serializable;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthRequest implements Serializable{

	private String username;
	private String password;
	private Set<String> roles;
	
}
