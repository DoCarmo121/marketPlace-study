package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{

}
