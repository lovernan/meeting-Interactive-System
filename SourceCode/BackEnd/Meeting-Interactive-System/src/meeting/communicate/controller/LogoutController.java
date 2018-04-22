package meeting.communicate.controller;

import java.io.IOException;

import javax.websocket.Session;

import org.json.JSONObject;

import meeting.communicate.WebsocketEndpoint;

/**
 * 用于控制用户注销流程的类。
 * 
 * @author Shuolin Yang
 *
 */
public class LogoutController {
	
	/**
	 * 用于保存需要注销的用户ID
	 */
	private String userId = null;
	
	/**
	 * 用于保存用户的会话对象
	 */
	private Session sess = null;
	
	/**
	 * 用于保存发送注销消息的会话对象ID
	 */
	private String sessId = null;
	
	/**
	 * 用于指示是否发送应答消息
	 */
	private boolean sendResponse = true;
	
	/**
	 * 用于保存应答消息
	 */
	private JSONObject response = null;
	
	/**
	 * 构造函数，初始化数据。
	 * 
	 * @param sess
	 */
	public LogoutController(Session sess, boolean sendResponse) {
		this.sess = sess;
		this.sessId = sess.getId();
		this.sendResponse = sendResponse;
		this.response = new JSONObject();
	}
	public LogoutController(Session sess) {
		this.sess = sess;
		this.sessId = sess.getId();
		this.response = new JSONObject();
	}
	
	/**
	 * 用于设置未登录错误信息。
	 */
	private void setResponseNotLoginError() {
		response.put("result", "error");
		response.put("error", "notLogin");
	}
	
	/**
	 * 用于设置操作成功信息。
	 */
	private void setResponseSuccess() {
		response.put("result", "success");
	}
	
	/**
	 * 发送应答消息。
	 * 
	 * @throws IOException
	 */
	private void sendResponse() throws IOException {
		sess.getBasicRemote().sendText(response.toString());
	}
	
	/**
	 * 处理注销流程的方法。
	 * 
	 * @throws IOException 
	 */
	public void process() throws IOException {
		
		// 判断用户是否已登陆
		if (isSourceOnline()) {				// 已登录
			
			// 将会话信息从服务器移除
			WebsocketEndpoint.removeSessUserMap(sessId);
			WebsocketEndpoint.subValidCount();
			WebsocketEndpoint.putClientMap(sessId, sess);
			WebsocketEndpoint.removeClientMap(userId);
			
			// 设置操作成功信息
			setResponseSuccess();
		} else {							// 未登录
			
			// 设置未登录错误信息
			setResponseNotLoginError();
		}
		
		// 发送应答
		if (sendResponse)
			sendResponse();
	}
	
	/**
	 * 用于测试要注销用户是否已登录。
	 * 
	 * @return 已登录返回true，未登录返回false
	 */
	private boolean isSourceOnline() {
		
		// 尝试获取会话的UserID
		userId = WebsocketEndpoint.getSessUserMap(sessId);
		
		// 判断是否已登录
		if (userId != null)				// 已登录
			return true;
		else							// 未登录
			return false;
	}
}
