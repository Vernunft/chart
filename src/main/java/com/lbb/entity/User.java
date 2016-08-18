package com.lbb.entity;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class User {
	private String id;
	private String username;
	private String password;
	private String type;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", type=" + type 
				+ "]";
	}
	public User(String id, String username, String password, String type) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.type = type;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//
//	@Override
//	public void valueBound(HttpSessionBindingEvent event) {
//		String sessionId = this.id;
//		System.out.println("进入了....");
//		HttpSession session = event.getSession();
//		Map<String, Object> userMap = (Map<String, Object>) session
//				.getServletContext().getAttribute("userMap");
//		userMap.put(this.id, this);
//	}
//	// 当session和对象解除绑定的时候
//	@Override
//	public void valueUnbound(HttpSessionBindingEvent event) {
////		System.out.println("退出了....");
////		HttpSession session = event.getSession();
////		// 获得人员列表
////		Map<String, Object> userMap = (Map<String, Object>) session
////				.getServletContext().getAttribute("userMap");
//		// 将用户移除了
//		//userMap.remove(this);implements HttpSessionBindingListener
//	}
	
}
