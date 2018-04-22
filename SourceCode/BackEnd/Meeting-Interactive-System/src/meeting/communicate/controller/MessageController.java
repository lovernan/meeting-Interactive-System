package meeting.communicate.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.websocket.Session;

import org.json.JSONObject;

import meeting.communicate.WebsocketEndpoint;

/**
 * 用于控制消息转发流程的控制器类。
 * 
 * @author Shuolin Yang
 *
 */
public class MessageController {

	/**
	 * 用于保存会话对象
	 */
	private Session sess = null;
	
	/**
	 * 用于保存会话ID
	 */
	private String sessId = null;
	
	/**
	 * 用于保存原始消息对象
	 */
	private JSONObject mess = null;
	
	/**
	 * 用于保存消息目标用户ID
	 */
	private String targetUser = null;
	
	/**
	 * 用于保存应答消息
	 */
	private JSONObject response = null;
	
	/**
	 * 若目标用户在线，保存目标用户的会话对象
	 */
	private Session targetSess = null;
	
	/**
	 * 构造函数，初始化数据。
	 * 
	 * @param mess 消息对象
	 * @param sess 发送消息的会话对象
	 */
	public MessageController(JSONObject mess, Session sess) {
		this.sess = sess;
		this.sessId = sess.getId();
		this.mess = mess;
		this.targetUser = mess.getJSONObject("parameter").getString("targetUser");
		this.response = new JSONObject();
	}
	
	/**
	 * 用于向应答设置notLogin未登录错误信息。
	 */
	private void setResponseNotLoginError() {
		response.put("result", "error");
		response.put("error", "notLogin");
	}
	
	/**
	 * 用于向应答设置notFriend不是好友关系错误信息。
	 */
	private void setResponseNotFriendError() {
		response.put("result", "error");
		response.put("error", "notFriend");
	}
	
	/**
	 * 用于向应答设置操作成功的信息。
	 */
	private void setResponseSuccess() {
		response.put("result", "success");
	}
	
	/**
	 * 用于发送应答消息。
	 * 
	 * @throws IOException 
	 */
	private void sendResponse() throws IOException {
		sess.getBasicRemote().sendText(response.toString());
	}
	
	/**
	 * 用于向转发消息设置发送用户的ID的方法。
	 */
	private void setMessSourceUser() {
		String sourceUser = WebsocketEndpoint.getSessUserMap(sessId);
		mess.getJSONObject("parameter").put("sourceUser", sourceUser);
	}
	
	/**
	 * 用于向目标用户转发原始消息。
	 * 
	 * @throws IOException 
	 */
	private void sendMessage() throws IOException {
		targetSess.getBasicRemote().sendText(mess.toString());
	}
	
	/**
	 * 用于处理消息的方法。
	 * 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public void process() throws SQLException, IOException {
		
		// 判断发送方是否已登录
		if (isSourceOnline()) {			// 已登录
			
			// 判断发送和接受双方是否为好友关系
			if (isSourceTargetFriends()) {		// 是好友关系
				
				// 向转发消息添加发送方UserId
				setMessSourceUser();
				
				// 判断对方是否在线
				if (isTargetOnline())					// 对方在线
					// 转发消息
					sendMessage();
				else									// 对方离线
					// 缓存消息
					cacheMessage();
				
				// 向应答设置成功操作信息
				setResponseSuccess();
			} else {							// 不是好友关系
				
				// 设置不为好友关系错误
				setResponseNotFriendError();
			}
		} else {						// 未登录
			
			// 设置未登录错误
			setResponseNotLoginError();
		}
		
		// 向发送方返回应答
		sendResponse();
	}
	
	/**
	 * 当目标用户离线时用于缓存消息的方法。
	 * 
	 * @throws SQLException 
	 */
	private void cacheMessage() throws SQLException {
		WebsocketEndpoint.getDB().cacheMessage(targetUser, mess.toString());
	}
	
	/**
	 * 测试发送和接受双发是否为好友关系的方法。
	 * 
	 * @return 是好友返回true，不是好友返回false
	 */
	private boolean isSourceTargetFriends() {
		// TODO 完成与用户系统的对接
		return true;
	}	
	
	/**
	 * 测试发送消息的用户是否已登录。
	 * 
	 * @return 已登录返回true，未登录返回false
	 */
	private boolean isSourceOnline() {
		
		// 尝试获取会话的用户ID并判断是否已登录
		if (WebsocketEndpoint.getSessUserMap(sessId) != null)	// 已登录
			return true;
		else													// 未登录
			return false;
	}
	
	/**
	 * 测试目标用户是否在线。
	 * 
	 * @return 在线返回true，离线返回false
	 */
	private boolean isTargetOnline() {
		
		// 尝试获取目标用户会话
		targetSess = WebsocketEndpoint.getClientMap(targetUser);
		
		// 判断用户是否在线
		if (targetSess != null)		// 在线
			return true;
		else						// 离线
			return false;
	}
}
