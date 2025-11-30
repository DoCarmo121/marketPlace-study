package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserDTO;
import com.example.demo.entities.User;
import com.example.demo.entities.UserProfile;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAll(PageRequest request){
		Page<User>list = repository.findAll(request);
		return list.map(x -> new UserDTO(x));
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User>obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!: " + id));
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO insert(UserDTO dto) {
		User newUser  = new User();
		newUser.setName(dto.getName());
		newUser.setEmail(dto.getEmail());
		newUser.setPassword(dto.getPassword());
		if(dto.getProfile()!=null) {
			UserProfile newUserProfile = new UserProfile();
			newUserProfile.setBio(dto.getProfile().getBio());
			newUserProfile.setDataNascimento(dto.getProfile().getDataNascimento());
			newUserProfile.setUrlAvatar(dto.getProfile().getUrlAvatar());
			newUser.setProfile(newUserProfile);
		}
		repository.save(newUser);
		return new UserDTO(newUser);
	}

	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		try {
			User user = repository.getReferenceById(id);
			user.setName(dto.getName());
			user.setEmail(dto.getEmail());
			user.setPassword(dto.getPassword());
			if(dto.getProfile()!=null) {
				UserProfile profile = user.getProfile();
				if (profile == null) {
	                profile = new UserProfile();
	                user.setProfile(profile);
	            }
				profile.setBio(dto.getProfile().getBio());
				profile.setDataNascimento(dto.getProfile().getDataNascimento());
				profile.setUrlAvatar(dto.getProfile().getUrlAvatar());
			}
			repository.save(user);
			return new UserDTO(user);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity Not found: " + id);
		}
	}

	public void delete(Long id) {
		try {
			if(!repository.existsById(id)) {
				throw new ResourceNotFoundException("Entity Not Found: " + id);
			}	
			repository.deleteById(id);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity Not Found: " + id);
		}
		
	}
	
}
