package meeting.communicate.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import javax.websocket.Session;

import org.json.JSONObject;

import cn.whxlover.joint.joint_login;
import meeting.communicate.WebsocketEndpoint;

/**
 * 用于处理密码登录消息的处理器。
 * 
 * @author Shuolin Yang
 *
 */
public class LoginPasswordController {

	/**
	 * 用于保存会话对象。
	 */
	private Session sess = null;
	
	/**
	 * 用于保存会话id。
	 */
	private String sessId = null;
	
	/**
	 * 用于保存密码。
	 */
	private String password = null;
	
	/**
	 * 用于保存用户id。
	 */
	private String userId = null;
	
	/**
	 * 用于保存应答消息对象。
	 */
	private JSONObject response = null;
	
	/**
	 * 构造函数，初始化数据.
	 * 
	 * @param mess 消息对象
	 * @param sess 会话对象
	 */
	public LoginPasswordController(JSONObject mess, Session sess) {
		this.sess = sess;
		this.sessId = sess.getId();
		this.password = mess.getJSONObject("parameter").getString("password");
		this.userId = getUserid(mess.getJSONObject("parameter").getString("username"));
		this.response = new JSONObject();
	}
	
	/**
	 * 向应答对象设置密码错误消息。
	 */
	private void setResponsePasswordError() {
		response.put("result", "error");
		response.put("error", "passwordError");
	}
	
	/**
	 * 设置用户不存在错误消息。
	 */
	private void serResponseUserNotExistError() {
		response.put("result", "error");
		response.put("error", "usernameNotExist");
	}
	
	/**
	 * 用于向应答对象设置登录成功消息。
	 */
	private void setResponseSuccess() {
		response.put("result", "success");
	}

	/**
	 * 用于向应答消息设置下次登录Token的方法。
	 */
	private void setResponseToken() {
		
		// 获取新的token
		String token = joint_login.setLoginToken(Integer.parseInt(userId));
		
		response.put("token", token);
	}
	
	/**
	 * 获取此登录用户已缓存的消息。
	 * 
	 * @return 消息字符串数组列表
	 * @throws SQLException 
	 */
	private void setResponseCachedMessages() throws SQLException {
		ArrayList<String> messages = WebsocketEndpoint.getDB().getCachedMessage(userId);
		
		// 构造应答对象
		JSONObject cachedMessage = new JSONObject();
		for (String message : messages) {
			JSONObject item = new JSONObject(message);
			cachedMessage.append("messages", item);
		}
		
		// Base64编码
		byte[] src = cachedMessage.toString().getBytes();
		String base64Str = Base64.getEncoder().encodeToString(src);
		
		// 设置应答对象
		response.put("cachedMessage", base64Str);
	}
	
	/**
	 * 用于向应答消息添加用户id。
	 */
	private void setResponseUserId() {
		response.put("userId", userId);
	}
	
	/**
	 * 用于发送应答对象的方法。
	 * @throws IOException 
	 */
	private void sendResponse() throws IOException {
		sess.getBasicRemote().sendText(response.toString());
	}
	
	/**
	 * 用于处理消息的方法。
	 * 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * @throws SQLException 
	 */
	public void process() throws IOException, NumberFormatException, SQLException {
	
		// 检查用户是否存在
		if (userId.equals("error")) {
			serResponseUserNotExistError();
			sendResponse();
			return;
		}
		
		// 验证用户Token
		if (verifyPassword()) {   				// 密码正确
		
			// 添加会话信息
			WebsocketEndpoint.putClientMap(userId, sess);
			WebsocketEndpoint.removeClientMap(sessId);
			WebsocketEndpoint.putSessUserMap(sessId, userId);
			WebsocketEndpoint.addValidCount();
			
			// 设置成功操作消息
			setResponseSuccess();
			
			// 设置下次登录的Token
			setResponseToken();
			
			// 设置用户离线时的缓存消息
			setResponseCachedMessages();
			
			// 设置用户id
			setResponseUserId();
		} else {							// 验证不正确
						
			// 设置密码错误消息
			setResponsePasswordError();
		}
		
		// 发送应答
		sendResponse();
	}
	
	/**
	 * 用于验证用户名和密码是否匹配。
	 * 
	 * @return 密码正确返回true，错误返回false
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	private Boolean verifyPassword() throws NumberFormatException, SQLException {
		return joint_login.loginin(Integer.parseInt(userId), password);
	}
	
	/**
	 * 返回用户名对应的用户ID。
	 * 
	 * @param username 用户名字符串
	 * @return 用户ID
	 */
	private String getUserid(String username) {
		return joint_login.ReturnUid(username);
	}
}
