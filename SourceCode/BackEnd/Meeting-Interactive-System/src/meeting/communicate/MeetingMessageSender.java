package meeting.communicate;

import java.io.IOException;

import org.json.JSONObject;

public class MeetingMessageSender {
	
	/**
	 * 供外部模块调用发送会议消息的方法。
	 * 
	 * @param mess
	 * @param meetingId
	 * @throws IOException 
	 */
	public void sendMeetingMessage(JSONObject mess, String meetingId) throws IOException {
		WebsocketEndpoint.sendMeetingMessage(mess, meetingId);
	}
}
