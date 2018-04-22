package priv.wrebuild.MFunction;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;

public class MeetingEnd {
	/**
	 * 会议结束
	 * @param pmess
	 * @return
	 */
	public JSONObject meetingEnd(JSONObject pmess) {
		int u_id=pmess.getInt("u_id");
		int m_id=pmess.getInt("m_id");
		JSONObject MeetingEnd;
		if(new DAO().queryMuByID(m_id,u_id, "admin=1")!=null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String e_time=df.format(new Date());
			new DAO().updateMeetingByID(m_id, "e_time", e_time);
			MeetingEnd=new SendJson().successJson("meetingEnd");
		}else {
			MeetingEnd=new SendJson().errorJson("meetingEnd");
		}
		return MeetingEnd;
	}
}
