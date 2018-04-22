package meeting.management.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import meeting.management.DatabaseConnector;
import meeting.management.entity.AdminUser;

/**
 * Servlet implementation class AdminResisterController
 */
@WebServlet("/admin-resister")
public class AdminRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 用于保存注册成功页面代码
	 */
	private String resisterSuccessHtml = "Success!";
	
	/**
	 * 用于保存重复用户名错误页面
	 */
	private String sameUsernameErrorHtml = "Same Username Error!";
	
	/**
	 * 用于保存系统错误页面代码
	 */
	private String systemErrorHtml = "System Error!";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminRegisterController() {
        super();
    }

    /**
     * 用于处理请求的方法。
     * 
     * @param request 请求对象
     * @param response 应答对象
     * @throws IOException 
     * @throws NoSuchAlgorithmException 
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException, SQLException, ClassNotFoundException {
    	
    	HttpSession session = request.getSession();
    	
    	// 判断用户是否登录
    	if (session.getAttribute("username") == null) {		// 用户未登录
    		
    		// 重定向到management主页面
    		response.sendRedirect("management.jsp");
    		return;
    	}
    	
    	// 用户已登录
    	// 注册新用户
    	String newUsername = request.getParameter("username");
    	String newMail = request.getParameter("mail");
    	String salt = getRandomString();
    	String targetPassword = request.getParameter("password") + salt;
    	
    	// 加密用户密码
    	MessageDigest md = MessageDigest.getInstance("MD5");
    	md.update(targetPassword.getBytes());
    	String encryptedPassword = new BigInteger(1, md.digest()).toString(16);
    	
    	// 插入数据
    	DatabaseConnector db = new DatabaseConnector();
    	AdminUser user = new AdminUser(null, newUsername, encryptedPassword, salt, newMail);
    	boolean isSuccess = db.setUserInfo(user);
    	
    	// 判断是否有相同用户名
    	if (isSuccess == false) {	// 有重复用户名
    		
    		// 向用户发送重复用户名错误页面
    		response.getWriter().print(sameUsernameErrorHtml);
    	} else {					// 注册成功
    		
    		// 向用户发送注册成功页面
    		response.getWriter().print(resisterSuccessHtml);
    	}
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {
			
			// 向用户发送系统错误页面
			e.printStackTrace();
			response.getWriter().print(systemErrorHtml);;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 用于获取随机的字符串（用作盐值）。
	 * 
	 * @return 随机的字符串（10个字符）
	 */
	private String getRandomString() {
		
		// 定义源字符串
		String source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		// 产生随机字符串
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			sb.append(source.charAt(random.nextInt(62)));
		}
		
		return sb.toString();
	}
}
