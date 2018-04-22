package priv.wrebuild.MFunction;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;

public class MeetingBegin {
	/**
	 * 会议开始
	 * @param pmess
	 * @return
	 */
	public JSONObject meetingBegin(JSONObject pmess) {
		int u_id=pmess.getInt("u_id");
		int m_id=pmess.getInt("m_id");
		JSONObject MeetingBegin;
		if(new DAO().queryMuByID(m_id,u_id, "admin=1")!=null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s_time=df.format(new Date());
			new DAO().updateMeetingByID(m_id, "s_time", s_time);
			MeetingBegin=new SendJson().successJson("meetingBegin");
		}else {
			MeetingBegin=new SendJson().errorJson("meetingBegin");
		}
		return MeetingBegin;
	}
}
