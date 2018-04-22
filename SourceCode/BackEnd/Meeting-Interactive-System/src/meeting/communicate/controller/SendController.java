package meeting.communicate.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.websocket.Session;

import org.json.JSONObject;

import meeting.communicate.WebsocketEndpoint;

/**
 * 用于控制向客户端发送消息流程的类。
 * 
 * @author Shuolin Yang
 *
 */
public class SendController {

	/**
	 * 用于保存目标用户列表。
	 */
	private ArrayList<String> targetUsers = null;
	
	/**
	 * 用于保存待发送消息。
	 */
	private JSONObject mess = null;
	
	/**
	 * 同于保存目标用户会话。
	 */
	private ArrayList<Session> targetSessions = null;
	

	/**
	 * 构造函数，用于初始化数据。
	 * 
	 * @param mess 要发送的消息对象
	 * @param targetUsers 目标用户列表
	 */
	public SendController(JSONObject mess, ArrayList<String> targetUsers) {
		this.targetUsers = targetUsers;
		this.mess = mess;
		this.targetSessions = new ArrayList<>();
	}
	
	/**
	 * 用于向特定用户发送消息的方法。
	 * 
	 * @param mess 要发送的消息
	 * @param sess 目标会话
	 * @throws IOException 
	 */
	private void sendMessage(JSONObject mess, Session sess) throws IOException {
		sess.getBasicRemote().sendText(mess.toString());
	}
	
	/**
	 * 用于处理发送流程的方法。
	 * 
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void process() throws IOException, SQLException {
		
		// 尝试获取目标会话
		getTargetSessions();
		
		int index = 0;
		
		// 判断每个用户是否在线
		for (Session sess : targetSessions) {
			if (sess != null) {					// 用户在线
				
				// 发送消息
				sendMessage(mess, sess);
			} else {							// 用户不在线
				
				// 缓存消息
				cacheMessage(targetUsers.get(index));
			}
			index++;
		}
	}

	/**
	 * 当目标用户离线时用于缓存消息的方法。
	 * 
	 * @param userId 缓存消息的目标用户ID
	 * @throws SQLException
	 */
	private void cacheMessage(String userId) throws SQLException {
		WebsocketEndpoint.getDB().cacheMessage(userId, mess.toString());
	}
	
	/**
	 * 获取目标用户的会话对象。
	 */
	private void getTargetSessions() {
		
		// 尝试获取所有目标用户的会话对象
		for (String userId : targetUsers) {
			targetSessions.add(WebsocketEndpoint.getClientMap(userId));
		}
	}
}
