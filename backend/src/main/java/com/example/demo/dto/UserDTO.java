package com.example.demo.dto;

import com.example.demo.entities.User;

public class UserDTO {

	private Long id;
	private String name;
	private String email;
	private String password;
	private UserProfileDTO profile;
	
	public UserDTO() {}

	public UserDTO(Long id, String name, String email, String password, UserProfileDTO profile) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.profile = profile;
	}
	
	public UserDTO(User entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.email = entity.getEmail();
		this.password = entity.getPassword();
		
		if(entity.getProfile() != null) {
			this.profile = new UserProfileDTO(entity.getProfile());
		}
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserProfileDTO getProfile() {
		return profile;
	}

	public void setProfile(UserProfileDTO profile) {
		this.profile = profile;
	}
	
	
	
}
