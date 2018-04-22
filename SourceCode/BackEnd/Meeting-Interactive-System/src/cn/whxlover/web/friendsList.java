package cn.whxlover.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.whxlover.domain.friends;
import cn.whxlover.domain.user;
import cn.whxlover.service.friendsListSer;
import cn.whxlover.service.searchSer;
import cn.whxlover.service.impl.friendsListSerImpl;
import cn.whxlover.service.impl.searchSerImpl;

public class friendsList extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ����
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String uid = request.getParameter("uid");
		
		//�������ύ��service��
		try {
			friendsListSer friendsListSer = new friendsListSerImpl();
			List<friends> friendsList = friendsListSer.friendsList(uid);
			request.setAttribute("friendsList", friendsList);
			// request.setAttribute("academycount", academyList.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher("/friendsList.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}