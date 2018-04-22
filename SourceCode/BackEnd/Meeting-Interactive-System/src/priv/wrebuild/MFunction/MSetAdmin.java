package priv.wrebuild.MFunction;

import java.util.ArrayList;

import org.json.JSONObject;

import com.sun.media.jfxmedia.control.VideoDataBuffer;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;
/**
 * 该类为设置管理员操作
 * @author Wrebuild
 *
 */
public class MSetAdmin {
	/**
	 * 设置管理员操作
	 * @param pmess
	 */
	public JSONObject mSetAdmin(JSONObject pmess) {
		
		String u_id=String.valueOf(pmess.getInt("u_id"));
		int m_id=pmess.getInt("m_id");
		int au_id=pmess.getInt("au_id");
		JSONObject SetAdmin;
		//判断是否为会议创建者
		if(new DAO().queryTableByID(m_id, "mCreator_id="+u_id, "meeting")!=null) {
			//判断是否用户参加了会议
			if(new DAO().queryTableByID(m_id, "cu_id="+au_id, "meeting_user")!=null) {
				new DAO().updateMuByID(au_id, m_id, "admin",String.valueOf(au_id));
				SetAdmin=new SendJson().successJson("SetAdmin");
			}else {
				SetAdmin=new SendJson().errorJson("SetAdmin");
				SetAdmin.put("error", "user does't join meeting");
			}
		}else {
			SetAdmin=new SendJson().errorJson("SetAdmin");
			SetAdmin.put("error", "user isn't creator");
		}
		return SetAdmin;
	}
	
	/**
	 * 获取管理员id
	 * @param pmess
	 * @return
	 */
	public ArrayList<String> adminsId(JSONObject pmess){
		int m_id=pmess.getInt("m_id");
		int[] admin=new DAO().outputList(" where m_id="+m_id, "admin", "meeting_user");
		ArrayList<String> admins=new ArrayList<String>();
		for(int i=0;i<admin.length;i++) {
			admins.add(admin[i]+"");
		}
		return admins;
	}
	
	/**
	 * 获取参加用户id
	 * @param pmess
	 * @return
	 */
	public ArrayList<String> usersId(JSONObject pmess){
		int m_id=pmess.getInt("m_id");
		int[] admin=new DAO().outputList(" where m_id="+m_id, "usersId", "meeting_user");
		ArrayList<String> admins=new ArrayList<String>();
		for(int i=0;i<admin.length;i++) {
			admins.add(admin[i]+"");
		}
		return admins;
	}
}
