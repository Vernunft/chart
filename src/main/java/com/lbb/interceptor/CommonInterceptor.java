package com.lbb.interceptor;
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
  
import org.springframework.web.servlet.HandlerInterceptor;  
import org.springframework.web.servlet.ModelAndView;  
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lbb.controller.UserController;
import com.lbb.entity.User;
  
  
/**
 * 登陆拦截器.
 *
 * @author leizhimin 2014/6/26 16:08
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
    private static final String[] IGNORE_URI = {"/index.jsp"};
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String url = request.getRequestURL().toString();
        System.out.println(">>>: " + url);
        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            User user = (User) request.getSession().getAttribute("existUser");
            if (user != null) flag = true;
        }
        return flag;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}