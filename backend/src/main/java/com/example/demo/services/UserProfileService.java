package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserProfileDTO;
import com.example.demo.entities.User;
import com.example.demo.entities.UserProfile;
import com.example.demo.repositories.UserProfileRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserProfileService {
	
	@Autowired
	private UserProfileRepository repository;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public Page<UserProfileDTO> findAll(PageRequest pageRequest) {
		Page<UserProfile> list = repository.findAll(pageRequest);
		return list.map(x -> new UserProfileDTO(x));
	}
	
	@Transactional(readOnly = true)
	public UserProfileDTO findById(Long id) {
		Optional<UserProfile> obj = repository.findById(id);
		UserProfile entity = obj.orElseThrow(() -> new ResourceNotFoundException("User Profile not found!: " + id) );
		return new UserProfileDTO(entity);
	}

	@Transactional
	public UserProfileDTO update(Long id, UserProfileDTO dto) {
		try {
			UserProfile profile= repository.getReferenceById(id);
			profile.setBio(dto.getBio());
			profile.setUrlAvatar(dto.getUrlAvatar());
			profile.setDataNascimento(dto.getDataNascimento());
			repository.save(profile);
			return new UserProfileDTO(profile);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("User Profile not found!: " + id);
		}
	}

	@Transactional
	public void delete(Long id) {
	    UserProfile profile = repository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Profile not found " + id));
	    
	    User user = profile.getUser();
	    
	    if (user != null) {
	        user.setProfile(null); 
	        userRepository.save(user); 
	    } else {
	        repository.delete(profile);
	    }
	}

}
