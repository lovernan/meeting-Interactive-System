package meeting.management.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminLogoutController
 */
@WebServlet("/admin-logout")
public class AdminLogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用于保存注销成功的页面代码
	 */
	private String logoutSuccessHtml = "Logout Success!";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLogoutController() {
        super();
    }
    
    /**
     * 用于处理请求的方法。
     * 
     * @param request 请求对象
     * @param response 应答对象
     * @throws IOException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	HttpSession session = request.getSession();
    	
    	// 若用户未登录重定向到主页面management
    	if (session.getAttribute("username") == null) {
    		
    		// 重定向
    		response.sendRedirect("management.jsp");
    		return;
    	}
    	
    	// 用户已登录则注销登录
    	session.invalidate();
    	
    	// 重定向到登录界面
    	response.sendRedirect("login.jsp");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
