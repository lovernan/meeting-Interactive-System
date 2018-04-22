package cn.whxlover.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/***
 * 
 * @author haixue Wang
 *����û�ע��
 */
public class userLoginOut extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);//��ֹ����Session  
		session.removeAttribute("user");
		String path = request.getContextPath();
        response.sendRedirect(path+"/index.jsp"); 
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
