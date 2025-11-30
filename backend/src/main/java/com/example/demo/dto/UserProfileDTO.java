package com.example.demo.dto;

import java.time.Instant;
import java.util.Objects;

import com.example.demo.entities.UserProfile;

public class UserProfileDTO {

	
	private Long id;
	private String bio;
	private String urlAvatar;
	private Instant dataNascimento; 


	public UserProfileDTO() {}
	
	public UserProfileDTO(UserProfile entity) {
		this.id = entity.getId();
		this.bio = entity.getBio();
		this.urlAvatar = entity.getUrlAvatar();
		this.dataNascimento = entity.getDataNascimento();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;
	}

	public Instant getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Instant dataNascimento) {
		this.dataNascimento = dataNascimento;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfileDTO other = (UserProfileDTO) obj;
		return Objects.equals(id, other.id);
	}
	
}
