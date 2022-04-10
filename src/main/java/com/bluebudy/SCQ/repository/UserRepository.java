package com.bluebudy.SCQ.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluebudy.SCQ.domain.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	User findByMail(String username);

	

}
