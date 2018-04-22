package priv.wrebuild.MFunction;

import org.json.JSONObject;

import com.sun.org.apache.bcel.internal.generic.NEW;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;

public class Mjoin {
	/**
	 * 用户验证，发至管理员审核
	 * @param pmess
	 */
	public JSONObject mJoin(JSONObject pmess) {
		//获取mpess中的各个变量
		int u_id=pmess.getInt("u_id");
		int m_id=pmess.getInt("m_id");
		//String mSign_id=new DAO().queryTableByID(m_id, "mSign_id","meeting");
		
		JSONObject SMJoin;
			//判断用户是否已经加入会议
			if(new DAO().queryMuByID(m_id,u_id, "admin="+u_id)==null) {
			//审核中的Json
			SMJoin=new SendJson().successJson("");
			String m_title=new DAO().queryTableByID(m_id, "m_title", "meeting");
			//发送给用户的Json
			JSONObject SendUser=new SendJson().successJson("mJion");
			//发送给管理员的Json	
			JSONObject SendAdminJoin=new JSONObject();
			SendAdminJoin.put("operation", "sendadminJoin");
			pmess.put("m_title",m_title);
			SendAdminJoin.put("parameter", pmess);
			//System.out.println(SMJoin.toString());
			SMJoin.put("SendUser", SendUser);
			SMJoin.put("SendAdminJoin", SendAdminJoin);
			return SMJoin;
			
			}else {
				SMJoin=new SendJson().errorJson("mJoin");
				SMJoin.put("error", "user already existed");
				
				return SMJoin;
			}

	}
	
	/**
	 * 根据管理员审核结果的加入操作
	 */
	public JSONObject mJionResult(JSONObject pmess) {
		JSONObject SMJionResult;
		
		if(pmess.getBoolean("result")==true) {
			//管理员同意
			new DAO().addMeeting_User(pmess.getInt("m_id"), pmess.getInt("ju_id"));
			SMJionResult=new SendJson().successJson("mJionResult");
		
		}else {
			//管理员拒绝
			SMJionResult=new SendJson().errorJson("mJionResult");
		}
		return SMJionResult;
	}
}
