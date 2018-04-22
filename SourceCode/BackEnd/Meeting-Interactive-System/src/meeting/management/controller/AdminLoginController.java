package meeting.management.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.captcha.Captcha;

import meeting.management.DatabaseConnector;
import meeting.management.entity.AdminUser;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/admin-login")
public class AdminLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 登录成功代码
	private String loginSuccessHtml = "Success!";
	
	// 验证码错误代码
	private String captchaErrorHtml = "Captcha Error!";
	
	// 用户名或密码错误代码
	private String usernamePasswordErrorHtml = "Username or Password Error!";
	
	// 用户不存在错误代码
	private String usernameNotExistHtml = "Username Not Exist!";
	
	// 发生系统错误代码
	private String systemErrorHtml = "System Error!";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginController() {
        super();
    }
    
    /**
     * 用于处理请求的方法。
     * 
     * @param request 请求对象
     * @param response 应答对象
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws NoSuchAlgorithmException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
    	
    	HttpSession session = request.getSession();
    	
    	// 若已经登录则跳转到management主页面
    	if (session.getAttribute("username") != null) {
    		response.sendRedirect("management.jsp");
    		return;
    	}
    	
    	// 验证验证码是否正确
    	String captcha = request.getParameter("captcha");
    	Captcha captchaObj = (Captcha) session.getAttribute(Captcha.NAME);
    	if (!captchaObj.isCorrect(captcha)) {	// 验证码不正确
    		
    		// 向用户发送错误页面
    		response.getWriter().print(captchaErrorHtml);
    		return;
    	}
    	
    	// 验证码正确检查用户名和密码
    	String username = request.getParameter("username");
    	DatabaseConnector db = new DatabaseConnector();
    	AdminUser user = db.getUserInfo(username);
    	db.close();
    	
    	// 检测用户是否存在
    	if (user.getUserId().equals("-1")) {	// 用户不存在
    		
    		// 向用户发送用户不存在错误页面
    		response.getWriter().print(usernameNotExistHtml);
    		return;
    	}
    	
    	// 用户存在查看密码是否正确
    	String targetPassword = request.getParameter("password") + user.getsalt();
    	MessageDigest md = MessageDigest.getInstance("MD5");
    	md.update(targetPassword.getBytes());
    	byte[] encryptedInputPasswordByte = md.digest();
    	String encryptedInputPassword = 
    			new BigInteger(1, encryptedInputPasswordByte).toString(16);
    	
    	// 判断用户输入的密码是否和数据库中的相同
    	if (encryptedInputPassword.equals(user.getPassword())) {	// 用户输入的密码正确
    		
    		// 添加会话属性（登录）并发送登陆成功页面
    		session.setAttribute("username", user.getUsername());
    		response.getWriter().print(loginSuccessHtml);
    	} else {													// 用户输入的密码不正确
    		
    		// 发送密码错误页面给用户
    		response.getWriter().print(usernamePasswordErrorHtml);
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {

			// 发生系统错误向用户发送系统错误页面
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
