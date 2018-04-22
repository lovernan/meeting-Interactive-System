package priv.wrebuild.MFunction;

import org.json.JSONObject;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;

public class MGrade {
	
	/**
	 * 管理员判断操作
	 * @param pmess
	 */
	public JSONObject adminGrade(JSONObject pmess) {
		
		JSONObject AdminGrade;
		int m_id=pmess.getInt("m_id");
		int u_id=pmess.getInt("u_id");
		//管理员判断
		if(new DAO().queryMuByID(m_id,u_id, "admin=1")!=null) {
			new DAO().updateMeetingByID(m_id,"gContent", pmess.getString("gContent"));
			AdminGrade=new SendJson().successJson("adminGrade");
		}else {
			AdminGrade=new SendJson().successJson("adminGrade");
			AdminGrade.put("error", "user isn't admin");
		}
		return AdminGrade;
	}
	
	/**
	 * 用户评分操作
	 * @param pmess
	 */
	public JSONObject userGrade(JSONObject pmess) {
		int m_id=pmess.getInt("m_id");
		int u_id=pmess.getInt("u_id");
		String gResult=pmess.getString("gResult");
		JSONObject UserGrade;
		//判断用户是否已经签到
		if(new DAO().queryMuByID(m_id, u_id, "sign")!=null) {
			new DAO().updateMuByID(u_id, m_id, "grade",gResult);
			UserGrade=new SendJson().successJson("userGrade");
			//System.out.println(UserGrade.toString());
			
		}else {
			UserGrade=new SendJson().errorJson("userGrade");
			UserGrade.put("error", "user dosen't sign or dosen't join meeting");
		}
		return UserGrade;
	}	
	
	/**
	 * 查看评分结果
	 * @param pmess
	 */
	public JSONObject gradeResult(JSONObject pmess) {
		JSONObject GradeResult;
		int m_id=pmess.getInt("m_id");
		
			//String[] all=new DAO().outputSList(m_id, "grade", "meeting_user");
			GradeResult=new SendJson().successJson("gradeResult");
			int twoStar=new DAO().listStatus("meeting_user where m_id="+m_id+" and grade='2*'");
			int fourStar=new DAO().listStatus("meeting_user where m_id="+m_id+" and grade='4*'");
			int sixStar=new DAO().listStatus("meeting_user where m_id="+m_id+" and grade='6*'");
			int eightStar=new DAO().listStatus("meeting_user where m_id="+m_id+" and grade='8*'");
			int tenStar=new DAO().listStatus("meeting_user where m_id="+m_id+" and grade='10*'");
			JSONObject para=new JSONObject();
			para.put("2*", twoStar);
			para.put("4*", fourStar);
			para.put("6*", sixStar);
			para.put("8*", eightStar);
			para.put("10*", tenStar);
			GradeResult.put("parameter", para);
	
		return GradeResult;
	}	
	
}
