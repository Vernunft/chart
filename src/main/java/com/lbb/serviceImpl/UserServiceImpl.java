package com.lbb.serviceImpl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.lbb.entity.User;
import com.lbb.service.IUserService;
import com.lbb.utils.MapToObject;
public class UserServiceImpl implements IUserService {
	private JdbcTemplate jdbcTemplate;    
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int add(User user) {
		String id = user.getId();
		jdbcTemplate.update("insert into T_USER(Id,username,PASSWORD) values(?,?,?)",new Object[]{id,user.getUsername(),user.getPassword()});  
		return 1;
	}

	@Override
	public int del(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User login(String username, String password) {
		String sql = "select * from t_user where username=? and password=?";
		List<Map<String, Object>> users = jdbcTemplate.queryForList(sql, new Object[]{username,password});
		if (users.size()>0) {
			User user = new User();
			try {
				user = (User) MapToObject.convertMap(User.class, (Map)users.get(0));
			} catch (IntrospectionException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return user ;
		}
		return null;
	}

}
