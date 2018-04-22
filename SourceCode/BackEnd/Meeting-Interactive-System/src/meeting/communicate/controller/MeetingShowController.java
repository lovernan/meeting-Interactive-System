package meeting.communicate.controller;

import javax.websocket.Session;

import org.json.JSONObject;

import meeting.communicate.WebsocketEndpoint;

/**
 * 用于处理会议展示页面操作的处理器。
 * 
 * @author Shuolin Yang
 *
 */
public class MeetingShowController {

	/**
	 * 用于保存会话对象。
	 */
	private Session sess = null;
	
	/**
	 * 用于保存所属会议id。
	 */
	private String meetingId = null;
	
	/**
	 * 构造函数，初始化数据。
	 * 
	 * @param mess
	 * @param sess
	 */
	public MeetingShowController(JSONObject mess, Session sess) {
		this.sess = sess;
		this.meetingId = mess.getString("meetingId");
	}
	
	/**
	 * 主流程。
	 */
	public void process() {
		WebsocketEndpoint.putMeetingMap(meetingId, sess);
	}
}
