package priv.wrebuild.MFunction;

import org.json.JSONObject;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;

/**
 * 该类为邀请用户加入会议
 * @author Wrebuild
 *
 */
public class MInvite {
	/**
	 * 邀请操作
	 * @param mess
	 */
	public JSONObject mInvite(JSONObject pmess) {
		//获取pmess的各个变量
		int u_id=pmess.getInt("u_id");
		int m_id=pmess.getInt("m_id");
		
		JSONObject SmInvite;
		//判断邀请人是否已经加入会议
		if(new DAO().queryMuByID(m_id, u_id, "*")!=null) {
			
			//String mSign_id=new DAO().queryTableByID(m_id, "msign_id","meeting");
			//返回的Jsonject信息
			//System.out.println(mSign_id);
			//System.out.println(mess.getInt("iu_id"));
		
			SmInvite=new SendJson().successJson("mInvite");
			//pmess.remove("u_id");
			//pmess.remove("iu_id");
			//pmess.remove("m_id");
			//pmess.put("mSign_id", mSign_id);
		
			SmInvite.put("operation","mJion" );
			SmInvite.put("m_id", pmess.getInt("m_id"));
			//System.out.println(SmInvite.toString());
		}else {
			SmInvite=new SendJson().errorJson("mInvite");
		}
		
		return SmInvite;
	}
	

	
	
}
