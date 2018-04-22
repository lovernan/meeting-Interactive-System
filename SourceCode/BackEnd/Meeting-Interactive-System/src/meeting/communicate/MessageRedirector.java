package meeting.communicate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.json.*;

import cn.whxlover.joint.handle;
import priv.wrebuild.Mcontroller.*;

class MessageRedirector {
	
	/**
	 * 用于保存操作代码和系统模块关系的表。
	 */
	private HashMap<String, String> redirectMap = new HashMap<>();

	/**
	 * 构造函数，用于向redirectMap赋值。
	 */
	public MessageRedirector() {
		redirectMap.put("register", 			"user");
		redirectMap.put("getFriendsInfo", 		"user");
		redirectMap.put("mRegister", 			"meeting");
		redirectMap.put("mSignID", 				"meeting");
		redirectMap.put("mSign", 				"meeting");
		redirectMap.put("mInvite", 				"meeting");
		redirectMap.put("mJoin", 				"meeting");
		redirectMap.put("mAdminReview", 		"meeting");
		redirectMap.put("mSetAdmin", 			"meeting");
		redirectMap.put("adminVote", 			"meeting");
		redirectMap.put("userVote", 			"meeting");
		redirectMap.put("voteResult", 			"meeting");
		redirectMap.put("mBulletScreen", 		"meeting");
		redirectMap.put("mSweepstake", 			"meeting");
		redirectMap.put("adminGrade", 			"meeting");
		redirectMap.put("userGrade", 			"meeting");
		redirectMap.put("gradeResult", 			"meeting");
		redirectMap.put("meetingEnd", 			"meeting");
		redirectMap.put("meetingStatus", 		"meeting");
		redirectMap.put("meetingBegin", 		"meeting");
		redirectMap.put("mRecommend", 			"meeting");
		redirectMap.put("mFriendRecommend", 	"meeting");
		redirectMap.put("mLabel", 				"meeting");
		redirectMap.put("mInformation", 		"meeting");
		redirectMap.put("getMeetingsInformation", "meeting");
	}
	
	/**
	 * 此方法用于将Message对象发送给用户模块。
	 * 
	 * @param mess 要传送的Message对象
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void SendToUserM(JSONObject mess) throws IOException, SQLException {
		handle.handlemess(mess);
	}
	
	/**
	 * 此方法用于将Message对象发送给会议模块。
	 * 
	 * @param mess 要传送的Message对象
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void SendToMeetingM(JSONObject mess) throws IOException, SQLException {
		System.out.println("aaaaa");
		MController.cJsonMess(mess);
	}
	
	/**
	 * 将消息组装成Message对象后将消息传递给相应的模块。
	 * 
	 * @param message 要处理的消息字符串
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public void DoMessage(JSONObject mess) throws IOException, SQLException {
		
		String target = redirectMap.get(mess.getString("operation"));
		
		// 判断消息的目的模块
		if (target.equals("user"))
			
			SendToUserM(mess);
			
		else if (target.equals("meeting"))
			
			SendToMeetingM(mess);
		
	}
}
