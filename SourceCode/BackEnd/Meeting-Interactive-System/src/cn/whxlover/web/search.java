package cn.whxlover.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.whxlover.domain.user;
import cn.whxlover.service.searchSer;
import cn.whxlover.service.impl.searchSerImpl;



public class search extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ����
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String text = request.getParameter("text");
		try {
			searchSer searchSer = new searchSerImpl();
			List<user> searchUser = searchSer.search(text);
			request.setAttribute("searchUser", searchUser);
			// request.setAttribute("academycount", academyList.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.getRequestDispatcher("/searchUser.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}