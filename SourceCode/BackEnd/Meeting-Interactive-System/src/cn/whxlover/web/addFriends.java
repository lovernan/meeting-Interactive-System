package cn.whxlover.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.whxlover.service.addFriendsSer;
import cn.whxlover.service.impl.addFriendsSerImpl;

public class addFriends extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//���ñ���
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//��ȡǰ̨��ֵ
		String id = request.getParameter("uid");
		int uid = Integer.parseInt(id);
		String username = request.getParameter("username");
		String sign = request.getParameter("sign");
		//��ȡservice������
		addFriendsSer addFriendsSer = new addFriendsSerImpl();
		int issuccess=0;
		try {
			issuccess = addFriendsSer.addFriends(uid,username,sign);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(issuccess==0) {
			response.getWriter().write("���ʧ��");
		}else {
			response.getWriter().write("��ӳɹ�");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}