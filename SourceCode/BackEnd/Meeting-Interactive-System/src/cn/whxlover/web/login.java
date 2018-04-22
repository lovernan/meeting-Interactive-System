package cn.whxlover.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import cn.whxlover.domain.user;
import cn.whxlover.service.loginSer;
import cn.whxlover.service.impl.loginSerImpl;

public class login extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		//����ǰ̨��������Ϣ
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String person = request.getParameter("check");
		//System.out.println(person);
		loginSer loginser = new loginSerImpl();
		if(person.equals("admin")) {			
			/*//����ǹ���Ա��½
			admin isadmin = null;
			HttpSession session = request.getSession();
			try {
				isadmin = loginser.adminlogin(username,password);
				if(isadmin!=null) {					
					session.setAttribute("admin", isadmin);
					response.sendRedirect("admin/index.jsp");
					
				}else {
					response.setContentType("text/html;charset=UTF-8");
					request.setAttribute("info", "�û������������");
					request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}else{
			//������û���½
			user isuser = null;
			try {
				isuser = loginser.userlogin(username,password);
				HttpSession session = request.getSession();
				if(isuser!=null) {					
					session.setAttribute("user", isuser);
					response.sendRedirect("index.jsp");					
				}else {
					response.setContentType("text/html;charset=UTF-8");
					request.setAttribute("info", "�û������������");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}