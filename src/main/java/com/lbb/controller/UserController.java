package com.lbb.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lbb.entity.User;
import com.lbb.service.IUserService;
import com.lbb.utils.JsonUtils;

@Controller
@RequestMapping("/userController")
public class UserController {

    @Resource
    IUserService userService;

    private static Map<String, Object> userMap = new HashMap<String, Object>();

    @RequestMapping(params = "add")
    public void add(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setId("333f");
        user.setUsername("dad");
        user.setPassword("das");
        System.out.println(user);
        userService.add(user);
    }

    /**
     * 
     * 描述
     * @author Icey Li
     * @created 2016-8-18 上午11:20:31
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "exit")
    public String exit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("existUser");
        userMap.remove(user.getId());
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return null;
    }

    /**
     * 登陆
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.login(username, password);
        if (user != null) {
            map.put("msg", 1);
            // 第一个BUG的解决:第二个用户登录后将之前的session销毁!
            // request.getSession().removeAttribute("existUser");
            // request.getSession().invalidate();
            // 获得到ServletCOntext中存的Map集合.
            // Map<User, HttpSession> userMap = (Map<User, HttpSession>)
            // request.getSession().getServletContext()
            // .getAttribute("userMap");
            // 判断用户是否已经在map集合中'
            // if(userMap.containsKey(user)){
            // // 说用map中有这个用户.
            // HttpSession session = request.getSession();
            // session = userMap.get(user);
            // // 将这个session销毁./WEB-INF/view/
            // session.invalidate();
            // }
            // for (Entry<User, HttpSession> entry : userMap.entrySet()) {
            // System.out.println(entry.getKey()+" "+entry.getValue());
            // }
            if (!userMap.containsKey(user.getId())) {
                userMap.put(user.getId(), user);
            }
            request.getSession().getServletContext().setAttribute("userMap", userMap);
            for (Entry<String, Object> entry : userMap.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
            request.getSession().setAttribute("existUser", user);
            System.out.println(user.toString());
            ServletContext application = request.getSession().getServletContext();
            String sourceMessage = "";
            if (null != application.getAttribute("message")) {
                sourceMessage = application.getAttribute("message").toString();
            }
            sourceMessage += "系统公告：<font color='gray'>" + user.getUsername() + "走进了聊天室！</font><br>";
            application.setAttribute("message", sourceMessage);

            return "main";
        }
        map.put("msg", 0);
        request.setAttribute("msg", "用户名或密码错误！");
        // JsonUtils.printJson(response, map);
        return "index";
    }

    /**
     * 踢人
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "kick")
    public String kick(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        // Map<User, HttpSession> userMap = (Map<User, HttpSession>)
        // request.getSession().getServletContext().getAttribute("userMap");
        Map<String, Object> userMap = (Map<String, Object>) request.getSession().getServletContext()
                .getAttribute("userMap");
        User user = new User();
        user.setId(id);
        // HttpSession session = userMap.get(user);
        // session.invalidate();
        userMap.remove(user.getId());
        ServletContext application = request.getSession().getServletContext();
        String sourceMessage = "";
        if (null != application.getAttribute("message")) {
            sourceMessage = application.getAttribute("message").toString();
        }
        sourceMessage += "系统公告：<font color='gray'>" + user.getUsername() + "离开了聊天室！</font><br>";
        application.setAttribute("message", sourceMessage);

        response.sendRedirect(request.getContextPath() + "/main.jsp");
        return null;
    }

    /**
     * 检查session是否过期
     * 
     * @throws IOException
     */
    @RequestMapping(params = "check")
    public String check(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 从session中获得用户的信息
        User existUser = (User) request.getSession().getAttribute("existUser");
        // 判断session中的用户是否过期
        if (existUser == null) {
            // 登录的信息已经过期了!
            response.getWriter().println("1");
        } else {
            // 登录的信息没有过期
            response.getWriter().println("2");
        }
        return null;
    }

    /**
     * 显示聊天内容
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(params = "getMessage")
    public String getMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = (String) request.getSession().getServletContext().getAttribute("message");
        if (message != null) {
            response.getWriter().println(message);
        }
        return null;
    }

    /**
     * 发送聊天内容
     * 
     * @throws IOException
     */
    @RequestMapping(params = "sendMessage")
    public String sendMessage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1.接收数据 。
        System.out.println("sendMessage invoke....");
        String from = req.getParameter("from"); // 发言人
        String face = req.getParameter("face"); // 表情
        String to = req.getParameter("to"); // 接收者
        String color = req.getParameter("color"); // 字体颜色
        String content = req.getParameter("content"); // 发言内容
        // 发言时间 正常情况下使用SimpleDateFormat
        String sendTime = new Date().toLocaleString(); // 发言时间
        // 获得ServletContext对象.
        ServletContext application = req.getSession().getServletContext();
        // 从ServletContext中获取消息
        String sourceMessage = (String) application.getAttribute("message");
        // 拼接发言的内容:xx 对 yy 说 xxx
        sourceMessage += "<font color='blue'><strong>" + from + "</strong></font><font color='#CC0000'>" + face
                + "</font>对<font color='green'>[" + to + "]</font>说：" + "<font color='" + color + "'>" + content
                + "</font>（" + sendTime + "）<br>";
        // 将消息存入到application的范围
        application.setAttribute("message", sourceMessage);
        return getMessage(req, resp);
    }
}
