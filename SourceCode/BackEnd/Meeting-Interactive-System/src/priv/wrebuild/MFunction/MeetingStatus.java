package priv.wrebuild.MFunction;

import java.util.ArrayList;

import org.json.JSONObject;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;

public class MeetingStatus {
	/**
	 * 查询会议状态
	 * @param pmess
	 * @return
	 */
	
	public static int allM() {
		return new DAO().listStatus("meeting");
		
	}
	
	public static int notBeginM() {
		return new DAO().listStatus("meeting where s_time is null");
		
	}
	
	
	public static int beginingM() {
		return new DAO().listStatus("meeting where s_time is not null AND e_time is null");
		
	}
	
	public static int endM() {
		return new DAO().listStatus("meeting where e_time is not null");
		
	}
	/*
	public JSONObject meetingStatus(JSONObject pmess) {
		int u_id=pmess.getInt("u_id");
		JSONObject MeetingStatus;
			int allMeeting=new DAO().listStatus("meeting");
			int notBeginMeeting=new DAO().listStatus("meeting where s_time is null");
			int beginingMeeting=new DAO().listStatus("meeting where s_time is not null AND e_time is null");
			int endMeeting=new DAO().listStatus("meeting where e_time is not null");
			MeetingStatus=new SendJson().successJson("meetingStatus");
			JSONObject para=new JSONObject();
			para.put("allMeeting", allMeeting);
			para.put("beginMeeting", beginingMeeting);
			para.put("endMeeting", endMeeting);
			MeetingStatus.put("parameter", para);
			return MeetingStatus;
	}
	*/
	/**
	 * 查看会议信息
	 * @param pmess
	 * @return
	 */
	public JSONObject mInformation(JSONObject pmess) {
			int m_id=pmess.getInt("m_id");
			JSONObject MInformation;
			MInformation=new SendJson().successJson("mInformation");
			JSONObject para=this.mIs(m_id);
			MInformation.put("parameter", para);
			System.out.println(MInformation.toString());
			return MInformation;
	
	}
	
	/**
	 * 获取每个会议信息
	 * @param m_id
	 * @return
	 */
	
	public JSONObject mIs(int m_id) {
		JSONObject paras=new JSONObject();
		String m_status;
		String s_time=new DAO().queryTableByID(m_id,"s_time" , "meeting");
		String e_time=new DAO().queryTableByID(m_id, "e_time", "meeting");
		if(s_time==null) {
			m_status="meeting not begin";
		}else if(e_time==null) {
			m_status="meeting begin";
		}else {
			m_status="meeting end";
		}
		paras.put("m_id", m_id);
		paras.put("m_title",new DAO().queryTableByID(m_id, "m_title", "meeting")); 
		paras.put("m_place",new DAO().queryTableByID(m_id, "m_place", "meeting"));
		paras.put("b_time",new DAO().queryTableByID(m_id, "b_time", "meeting"));
		paras.put("m_content",new DAO().queryTableByID(m_id, "m_content", "meeting"));
		paras.put("m_status", m_status);
		return paras;
	}
	
	/**
	 * 获取会议标题
	 * @param pmess
	 * @return
	 */
	public static JSONObject getMtitle(int u_id) {
		String m_title;
		ArrayList<String> m_titles=new ArrayList<String>();
		ArrayList<String> m_ideas=new ArrayList<String>();
		int[] m_ids=new DAO().outputList("admin="+u_id,"m_id","meeting_user");
		for(int i=0;i<m_ids.length;i++) {
			m_title=new DAO().queryTableByID(m_ids[i], "m_title", "meeting");
			m_titles.add(m_title);
			}
		for (int item : m_ids) {
			m_ideas.add(String.valueOf(item));
		}
		JSONObject getMtitle=new SendJson().successJson("getMtitle");
		getMtitle.put("m_ids", m_ideas);
		getMtitle.put("m_titles", m_titles);
		return getMtitle;
	}
	
	/**
	 * 获取所有会议信息
	 * @return
	 */
	public JSONObject getAllMeeting( ) {
		int[] m_ids=new DAO().outputList("","m_id","meeting");
		JSONObject j=new SendJson().successJson("getMeetingsInformation"); 
		ArrayList<JSONObject> m_alls=new ArrayList<JSONObject>();
		for(int i=0;i<m_ids.length;i++) {
			m_alls.add(this.mIs(m_ids[i]));
		}
		j.put("parameter", m_alls);
		return j;
	}

}
