package meeting.management.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import meeting.management.DatabaseConnector;
import meeting.management.entity.SystemConfig;

/**
 * Servlet implementation class AdminConfigController
 */
@WebServlet("/admin-config")
public class AdminConfigController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用于保存设置成功页面的代码。
	 */
	private String configSuccessHtml = "Config Success!";
	
	/**
	 * 用于保存系统错误页面代码。
	 */
	private String systemErrorHtml = "System Error!";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminConfigController() {
        super();
    }

    /**
     * 用于处理请求的方法。
     * 
     * @param request 请求对象
     * @param response 应答对象
     * @throws IOException 
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException {
    	
    	HttpSession session = request.getSession();
    	
    	// 判断用户是否登录
    	if (session.getAttribute("username") == null) {		// 用户未登录
    		
    		// 将用户重定向到management主页面
    		response.sendRedirect("management.jsp");
    		return;
    	}
    	
    	// 用户已登录
    	// 获取用户的新设置
    	String[] ifUserResisterInput = request.getParameterValues("ifUserResister");
    	String[] ifUserLoginInput = request.getParameterValues("ifUserLogin");
    	String ifUserResister = null;
    	String ifUserLogin = null;
    	String[] meetingConfig = request.getParameterValues("meetingConfig");
    	String meetingFunctions = "";
    	
    	// 处理数据
    	for (int i = 0; i < meetingConfig.length; i++) {
    		meetingFunctions += meetingConfig[i] + ";";
    	}
    	if (ifUserResisterInput.length == 0) {
    		ifUserResister = "No";
    	} else {
    		ifUserResister = "Yes";
    	}
    	if (ifUserLoginInput.length == 0) {
    		ifUserLogin = "No";
    	} else {
    		ifUserLogin = "Yes";
    	}
    	SystemConfig config = new SystemConfig(meetingFunctions, ifUserLogin, ifUserResister);
    	
    	// 更新数据库
    	DatabaseConnector db = new DatabaseConnector();
    	db.setSystemConfig(config);
    	db.close();
    	
    	// 向用户发送设置成功页面
    	response.getWriter().print(configSuccessHtml);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			
			// 向用户发送系统错误页面
			e.printStackTrace();
			response.getWriter().print(systemErrorHtml);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
