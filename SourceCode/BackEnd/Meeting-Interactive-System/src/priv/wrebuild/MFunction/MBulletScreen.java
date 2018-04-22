package priv.wrebuild.MFunction;

import org.json.JSONObject;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;


public class MBulletScreen {
	/**
	 * 弹幕发送
	 * @param pmess
	 */
	public JSONObject mBulletScreen(JSONObject pmess) {
		int m_id=pmess.getInt("m_id");
		int u_id=pmess.getInt("u_id");
		JSONObject SmBulletScreen;
		//判断用户是否参加会议
		if(new DAO().queryMuByID(m_id, u_id, "sign")!=null) {
			SmBulletScreen=new SendJson().successJson("mBulletSreen");
			//SmBulletScreen.put("admins",new DAO().outputList(pmess.getInt("m_id"), "admin", "meeting_user"));
			SmBulletScreen.put("content", pmess.getString("content"));
		
		}else {
			SmBulletScreen=new SendJson().errorJson("mBulletSreen");
			SmBulletScreen.put("error", "user does't join meeting");
		}
		return SmBulletScreen;
	}
	
	
	
}
