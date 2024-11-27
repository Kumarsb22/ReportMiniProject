package com.secondminiproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondminiproject.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	public User findByEmailAndPwd(String username, String password);

}
