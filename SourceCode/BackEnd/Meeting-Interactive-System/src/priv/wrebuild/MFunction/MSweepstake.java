package priv.wrebuild.MFunction;

import java.util.Random;

import org.json.JSONObject;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;

public class MSweepstake {
	/**
	 * 抽奖操作
	 * @param pmess
	 */
	public JSONObject mSweepstake(JSONObject pmess) {
		JSONObject MSweepstake;
		int m_id=pmess.getInt("m_id");
		int u_id=pmess.getInt("u_id");
		//判断操作者是否为管理员
		if(new DAO().queryMuByID(m_id,u_id, "admin="+u_id)!=null) {
			int[] n=new DAO().outputList(" where m_id="+m_id, "cu_id", "meeting_user");
			Random random=new Random();
			int su_id=n[random.nextInt(n.length)];
			System.out.println(su_id);
			MSweepstake=new SendJson().successJson("mSweepstake");
			MSweepstake.put("su_id", su_id);
			//System.out.println(para.toString());
		}else {
			MSweepstake=new SendJson().errorJson("mSweepstake");
			MSweepstake.put("errorr", "u_id is't admin");
		}
		return MSweepstake;
	}
	
}
