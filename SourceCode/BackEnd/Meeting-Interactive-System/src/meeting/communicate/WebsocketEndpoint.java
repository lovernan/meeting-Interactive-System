package meeting.communicate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.websocket.*;
import javax.websocket.server.*;

import org.json.*;

import meeting.communicate.controller.LoginPasswordController;
import meeting.communicate.controller.LoginTokenController;
import meeting.communicate.controller.LogoutController;
import meeting.communicate.controller.MeetingShowAdminLoginController;
import meeting.communicate.controller.MeetingShowController;
import meeting.communicate.controller.MessageController;
import meeting.communicate.controller.SendController;
import meeting.data.DatabaseConnector;

@ServerEndpoint("/endpoint")
/**
 * 作为Websocket端点的类。
 * 
 * 端点用于与客户端通信，接收Websocket消息。功能有：<br/>
 * 一、接收客户端发来的消息并将消息传送给不同的模块。<br/>
 * 二、将用户之间的即时消息转发。<br/>
 * 三、向客户端发送系统生成的消息。<br/>
 * 
 * @author Shuolin Yang
 *
 */
public class WebsocketEndpoint {
	
	/**
	 * 用于发送消息到系统不同模块的转发器对象。
	 */
	private static MessageRedirector redirector = new MessageRedirector();
	
	/**
	 * 用于来读写数据库的数据库连接器对象。
	 */
	private static DatabaseConnector dbConnector = null;
	static {
		try {
			dbConnector = new DatabaseConnector();
		} catch (Exception e) {
			// 找不到驱动或者连接出错，记录日志并退出
			e.printStackTrace();
			LogGenerator log = new LogGenerator();
			log.log(e);
			System.exit(1);
		}
	}
	
	/** 
	 * 用于记录当前连接数的变量（包括未验证的连接）。
	 */
	private static long clientCount = 0;
	
	/**
	 * 用于记录经过验证连接数的变量。
	 */
	private static long validClientCount = 0;
	
	/**
	 * 用于保存Session id和用户 UserId 对应关系的Map（查询SessionID找到用户），
	 * 经过验证后的Session id会和对应的UserId保存到此表中。
	 */
	private static HashMap<String, String> sessUserMap = new HashMap<>();
	
	/**
	 *  用于保存当前连接会话对象的Map，用户ID作为键，会话对象作为值。
	 */
	private static HashMap<String, Session> clientMap = new HashMap<>();
	
	/**
	 * 用于保存当前连接的会议展示页面会话，展示页面所属会议id作为键，会话对象作为值。
	 */
	private static HashMap<String, Session> meetingMap = new HashMap<>();
	
	/**
	 *  此方法将当前总连接数加一。
	 */
	public static synchronized void addCount() {
		clientCount++;
	}
	
	/**
	 *  此方法将当前总连接数减一。
	 */
	public static synchronized void subCount() {
		clientCount--;
	}
	
	/**
	 * 此方法将有效连接数加一。
	 */
	public static synchronized void addValidCount() {
		validClientCount++;
	}
	
	/**
	 * 此方法将有效连接数减一。
	 */
	public static synchronized void subValidCount() {
		validClientCount--;
	}
	
	/**
	 * sessUserMap的更改器，向其中添加一个元素。
	 * 
	 * @param key 要添加的键
	 * @param value 要添加的值
	 */
	public static void putSessUserMap(String key, String value) {
		sessUserMap.put(key, value);
	}
	
	/**
	 * sessUserMap的更改器，删除一个元素。
	 * 
	 * @param key 要删除元素的键
	 */
	public static void removeSessUserMap(String key) {
		sessUserMap.remove(key);
	}
	
	/**
	 * sessUserMap的访问器，返回一个元素的值。
	 * 
	 * @param key 要访问元素的键
	 * @return 元素的值
	 */
	public static String getSessUserMap(String key) {
		return sessUserMap.get(key);
	}
	
	/**
	 * clientMap的更改器，添加一个元素。
	 * 
	 * @param key 要添加元素的键
	 * @param value 要添加元素的值
	 */
	public static void putClientMap(String key, Session value) {
		clientMap.put(key, value);
	}
	
	/**
	 * clientMap的更改器，删除一个元素。
	 * 
	 * @param key 要删除元素的键
	 */
	public static void removeClientMap(String key) {
		clientMap.remove(key);
	}
	
	/**
	 * clientMap的访问器，返回一个元素的值。
	 * 
	 * @param key 要访问元素的键
	 * @return 元素的值
	 */
	public static Session getClientMap(String key) {
		return clientMap.get(key);
	}
	
	/**
	 * 向MeetingMap中添加一个值。
	 * 
	 * @param key
	 * @param value
	 */
	public static void putMeetingMap(String key, Session value) {
		meetingMap.put(key, value);
	}
	
	/**
	 * 从MeetingMap中删除一个值。
	 * 
	 * @param key
	 */
	public static void removeMeetingMap(String key) {
		meetingMap.remove(key);
	}
	
	/**
	 * 返回MeetingMap中的一个值。
	 * 
	 * @param key
	 * @return
	 */
	public static Session getMeetingMap(String key) {
		return meetingMap.get(key);
	}
	
	/**
	 * 获得数据库连接器。
	 * 
	 * @return 数据库连接器
	 */
	public static DatabaseConnector getDB() {
		return dbConnector;
	}
	
	/**
	 * 此方法将不同的消息分类做不同的处理。
	 * 
	 * @param message 需要处理的消息文本
	 * @param sess 发送消息的会话对象
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * @throws SQLException 
	 */
	private static void doMessage(String message, Session sess) throws JSONException, IOException, NumberFormatException, SQLException {
		
		// 从消息中获得操作类型字符串
		JSONObject mess = new JSONObject(message);
		String operation = mess.getString("operation");
		
		// 判断操作类型
		if (operation.equals("message")) {			// 消息是一个即时消息，转发
			
			new MessageController(mess, sess).process();
			
		} else if (operation.equals("loginToken")) {// 用户登录消息
			
			new LoginTokenController(mess, sess).process();
			
		} else if (operation.equals("loginPassword")) {// 用户登录消息
			
			new LoginPasswordController(mess, sess).process();
			
		} else if (operation.equals("logout")) {	// 用户注销消息
			
			new LogoutController(sess).process();
			
		} else if (operation.equals("meetingShowAdminLogin")) {
			
			new MeetingShowAdminLoginController(mess, sess).process();
			
		} else if (operation.equals("meetingPage")) {
			
			new MeetingShowController(mess, sess).process();
			
		} else if (operation.equals("echo")) {
			
			sess.getBasicRemote().sendText(message);
			
		} else {									// 消息不是即时消息，转给系统各模块
			
			// 若是注册操作特殊处理
			if (mess.getString("operation").equals("register")) {
				mess.put("sessid", sess.getId());
			}
			
			redirector.DoMessage(mess);
		}
	}
	
	/**
	 * 此方法用于供其他类调用发送消息。
	 * 
	 * @param mess 要发送的消息的JSON对象
	 * @param targetUsers 发送的目标用户列表（UserID列表）
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void sendMessage(JSONObject mess, ArrayList<String> targetUsers) throws IOException, SQLException {
		new SendController(mess, targetUsers).process();
	}
	
	/**
	 * 用于发送会议消息。
	 * 
	 * @param mess
	 * @param meetingId
	 * @throws IOException 
	 */
	public static void sendMeetingMessage(JSONObject mess, String meetingId) throws IOException {
		getMeetingMap(meetingId).getBasicRemote().sendText(mess.toString());
	}
	
	/**
	 * 返回当前连接总数。
	 * 
	 * @return 连接数
	 */
	public static long getCount() {
		return clientCount;
	}
	
	/**
	 * 返回当前的有效连接数（已登录的会话）。
	 * 
	 * @return 有效连接数
	 */
	public static long getValidCount() {
		return validClientCount;
	}
	
	/**
	 * 处理新连接的方法。
	 * 
	 * @param sess 新连接的会话对象
	 */
	@OnOpen
	public void onOpen(Session sess) {
		putClientMap(sess.getId(), sess);
		addCount();
	}
	
	/**
	 * 处理Websocket新消息的方法。
	 * 
	 * @param message 消息文本
	 * @param sess 发送消息的会话对象
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws NumberFormatException 
	 * @throws SQLException 
	 */
	@OnMessage
	public void onMessage(String message, Session sess) throws JSONException, IOException, NumberFormatException, SQLException {
		doMessage(message, sess);
	}
	
	/**
	 * 处理连接关闭的方法，若用户处于登录状态则先使其注销。
	 * 
	 * @param sess 关闭的会话
	 * 
	 * @throws IOException 
	 */
	@OnClose
	public void onClose(Session sess) throws IOException {
		new LogoutController(sess, false).process();
		removeClientMap(sess.getId());
		subCount();
	}
	
	/**
	 * 处理错误的方法。
	 * 
	 * @param sess 发生错误的会话
	 * @param e 会话发生的异常
	 * @throws IOException 
	 */
	@OnError
	public void onError(Session sess, Throwable e) {
		
		LogGenerator log = new LogGenerator();
		
		try {
			
			if (e instanceof JSONException) {		// 处理JSON异常，记录日志并发送错误消息
				
				log.log(e);
				sess.getBasicRemote().sendText("Connection Error: JSONException");
			
			} else if (e instanceof IOException) {	// 处理IO异常，记录日志并发送错误消息
				
				log.log(e);
				sess.getBasicRemote().sendText("Connection Error: IOException");
			
			} else if (e instanceof SQLException) {	// 处理SQL异常，记录日志并发送错误消息
				
				log.log(e);
				sess.getBasicRemote().sendText("Connection Error: SQLException");
			
			} else {								// 处理其他类型的异常
			
				log.log(e);
				sess.getBasicRemote().sendText("Connection Error: OtherException");
			}
			
		} catch (IOException e1) {
			
			log.log(e1);
		}
		
		e.printStackTrace();
		try {
			sess.getBasicRemote().sendText(e.getMessage() + e.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
