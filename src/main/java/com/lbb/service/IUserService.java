package com.lbb.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lbb.entity.User;
@Service
public interface IUserService {
	int add(User user);
	int del(String id);
	int update(User user);
	User find(String id);
	User login(String username, String password); 
}
