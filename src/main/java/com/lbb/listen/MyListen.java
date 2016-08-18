package com.lbb.listen;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import com.lbb.entity.User;

public class MyListen implements ServletContextListener{
	// ServletContext对象创建 下面这个方法就会执行
	// ServletContextEvent事件对象. 监听器对象---》ServletContext对象.(事件源)
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Map<User,HttpSession> userMap = new HashMap<User,HttpSession>();
		sce.getServletContext().setAttribute("userMap", userMap);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
