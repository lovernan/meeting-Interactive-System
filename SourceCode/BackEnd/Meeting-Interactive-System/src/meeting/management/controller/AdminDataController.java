package meeting.management.controller;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.sun.management.OperatingSystemMXBean;

import meeting.communicate.WebsocketEndpoint;
import meeting.management.DatabaseConnector;
import meeting.management.entity.Blacklist;
import priv.wrebuild.MFunction.MeetingStatus;

/**
 * Servlet implementation class AdminDataController
 */
@WebServlet("/admin-data")
public class AdminDataController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用于保存系统错误页面的代码。
	 */
	private String systemErrorHtml = "System Error!";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDataController() {
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
    	
    	HttpSession session = request.getSession();
    	
    	// 判断用户是否登录
    	if (session.getAttribute("username") == null) {		// 用户未登录不提供服务
    		
    		// 将用户重定向到management主页面
    		response.sendRedirect("management.jsp");
    		return;
    	}
    	
    	// 获取请求的数据类型
    	String dataName = request.getParameter("dataName");
    	
    	// 使用不同的方法处理不同的数据类型
    	if (dataName == null) {											// 没有说明类型
     		
     		response.getWriter().print("Please select the data type!");
     		
     	} else if (dataName.equals("socketCount")) {				// 获取当前websocket连接总数
    		
    		socketCountP(response);
    		
    	} else if (dataName.equals("userCount")) {			// 获取当前已登录用户数量
    		
    		userCountP(response);
    		
    	} else if (dataName.equals("memory")) {				// 获取系统当前内存使用状况
    		
    		memoryP(response);
    		
    	} else if (dataName.equals("meetingCount")) {		// 获取系统注册的会议总数
    		
    		meetingCountP(response);
    		
    	} else if (dataName.equals("meetingNotStart")) {	// 获取还未开始的会议数量
    		
    		meetingNotStartP(response);
    		
    	} else if (dataName.equals("meetingInProcess")) {	// 获取正在进行中的会议数量
    		
    		meetingInProcessP(response);
    		
    	} else if (dataName.equals("meetingHasFinished")) {	// 获取已经结束的会议数量
    		
    		meetingHasFinishedP(response);
    		
    	} else if (dataName.equals("blacklist")) {
    		
    		blacklistP(response);
    		
    	}
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			response.getWriter().print("System Error!");
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 用于获取websocket类型数据的方法。
	 * 
	 * @param response 应答对象
	 * @throws IOException 
	 */
	private void socketCountP(HttpServletResponse response) throws IOException {
		response.getWriter().print(WebsocketEndpoint.getCount());
	}
	
	/**
	 * 用于获取用户数量数据的方法。
	 * 
	 * @param response 应答方法
	 * @throws IOException 
	 */
	private void userCountP(HttpServletResponse response) throws IOException {
		response.getWriter().print(WebsocketEndpoint.getValidCount());
	}

	/**
	 * 用于获取内存数据的方法。
	 * 
	 * @param response 应答数据
	 * @throws IOException 
	 */
	private void memoryP(HttpServletResponse response) throws IOException {
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		
		// 获取总内存空间
		long totalMemory = osmxb.getTotalPhysicalMemorySize();
		
		// 获取剩余的内存空间
		long freeMemory = osmxb.getFreePhysicalMemorySize();
		
		// 计算使用率
		int preCompare = (int) Math.floor((1.0 - ((double)freeMemory)/((double)totalMemory)) * 10000.0);
		double compare = (double)preCompare / 100.0;
		
		// 返回数据
		response.getWriter().print(compare + "%");
	}
	
	/**
	 * 用于获取会议总数量的方法。
	 * 
	 * @param response 应答对象
	 * @throws IOException 
	 */
	private void meetingCountP(HttpServletResponse response) throws IOException {
		response.getWriter().print(MeetingStatus.allM());
	}
	
	/**
	 * 用于获取未开始会议数据的方法。
	 * 
	 * @param response 应答对象
	 * @throws IOException 
	 */
	private void meetingNotStartP(HttpServletResponse response) throws IOException {
		response.getWriter().print(MeetingStatus.notBeginM());
	}
	
	/**
	 * 用于获取正在进行中会议数量的方法。
	 * 
	 * @param response 应答对象
	 * @throws IOException 
	 */
	private void meetingInProcessP(HttpServletResponse response) throws IOException {
		response.getWriter().print(MeetingStatus.beginingM());
	}
	
	/**
	 * 用于获取已结束会议数量的方法。
	 * 
	 * @param response 应答对象
	 * @throws IOException 
	 */
	private void meetingHasFinishedP(HttpServletResponse response) throws IOException {
		response.getWriter().print(MeetingStatus.endM());
	}
	
	/**
	 * 用于获取黑名单用户。
	 * 
	 * @param response
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	private void blacklistP(HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException {
		DatabaseConnector db = new DatabaseConnector();
		Blacklist result = db.getBlackList();
		
		// 构造结果JSON
		JSONObject json = new JSONObject();
		json.put("length", result.getLength());
		for (int i = 0; i < result.getLength(); i++) {
			json.append("userId", result.getUserId(i));
			json.append("startTime", result.getStartTime(i));
		}
		
		response.getWriter().print(json.toString());
	}
}
