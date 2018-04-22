package meeting.communicate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

public class MessageSender {
	
	/**
	 * 此方法供外部模块调用发送（同时检查消息格式）。
	 * 
	 * @param mess 要发送消息的Message对象
	 * @param targetUsers 目标用户列表（UserId列表）
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void sendMessage(JSONObject mess, ArrayList<String> targetUsers) throws IOException, SQLException {
		WebsocketEndpoint.sendMessage(mess, targetUsers);
	}
}
