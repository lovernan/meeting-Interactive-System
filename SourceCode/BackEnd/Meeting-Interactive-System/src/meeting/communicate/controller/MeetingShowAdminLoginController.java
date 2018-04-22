package meeting.communicate.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.websocket.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.whxlover.joint.joint_login;
import priv.wrebuild.MFunction.MeetingStatus;

/**
 * 会议展示页面中会议管理员登录操作的处理器。
 * 
 * @author Shuolin Yang
 *
 */
public class MeetingShowAdminLoginController {

	/*
	 * 用于保存会话对象。
	 */
	private Session sess = null;
	
	/**
	 * 用于保存用户id。
	 */
	private String userId = null;
	
	/**
	 * 用于保存用户的密码。
	 */
	private String password = null;
	
	/**
	 * 用于保存应答对象。
	 */
	private JSONObject response = null;
	
	/**
	 * 构造函数，初始化数据。
	 * 
	 * @param mess 消息对象
	 * @param sess 会话对象
	 */
	public MeetingShowAdminLoginController(JSONObject mess, Session sess) {
		this.sess = sess;
		this.userId = getUserid(mess.getJSONObject("parameter").getString("username"));
		this.password = mess.getJSONObject("parameter").getString("password");
		this.response = new JSONObject();
	}
	
	/**
	 * 设置用户不存在错误。
	 */
	private void setResponseUserNotExistError() {
		response.put("result", "error");
		response.put("error", "usernameNotExist");
	}
	
	/**
	 * 设置成功消息。
	 */
	private void setResponseSuccess() {
		response.put("result", "success");
	}
	
	/**
	 * 设置为管理员的会议标题列表。
	 */
	private void setMeetingIdList() {
		JSONObject meetings = MeetingStatus.getMtitle(Integer.parseInt(userId));

		JSONArray mid = (JSONArray) meetings.get("m_ids");
		JSONArray mtitle = (JSONArray) meetings.get("m_titles");
		response.put("idLength", mid.length());
		response.put("titleLength", mtitle.length());
		
		for (Object item : mid) {
			response.append("meetingsId", (String)item);
		}
		for (Object item : mtitle) {
			response.append("meetingsTitle", (String)item);
		}
	}

	/**
	 * 用于发送应答对象的方法。
	 * @throws IOException 
	 */
	private void sendResponse() throws IOException {
		sess.getBasicRemote().sendText(response.toString());
	}
	
	/**
	 * 执行主流程。
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 * @throws IOException 
	 */
	public void process() throws NumberFormatException, SQLException, IOException {
		
		// 判断密码是否正确
		if (verifyPassword()) {		// 若用户密码正确
			
			// 设置成功消息
			setResponseSuccess();
			
			// 设置会议列表
			setMeetingIdList();
			
		} else {					// 密码不正确
			
			// 设置错误消息
			setResponseUserNotExistError();
		}
		
		// 发送消息
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
