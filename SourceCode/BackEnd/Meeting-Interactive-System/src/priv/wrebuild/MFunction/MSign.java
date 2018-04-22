package priv.wrebuild.MFunction;

import java.util.UUID;

import org.json.JSONObject;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import priv.wrebuild.DAO.DAO;
import priv.wrebuild.Mcontroller.SendJson;

/**
 * 此类为用户的签到操作，生成会议标识符
 * @author Wrebuild
 *
 */
public class MSign {
	//设置para的Json对象保存会议变量
	JSONObject mpara=new JSONObject();
	/**
	 * 获取会议标识符操作
	 * @param m_id
	 */
	public JSONObject smSignID(JSONObject pmess) {
		int m_id=pmess.getInt("m_id");
		int u_id=pmess.getInt("u_id");
		JSONObject smSignID;
		//判断是否为管理员
		if(new DAO().queryMuByID(m_id,u_id, "admin")!=null) {
		String msign_id = new DAO().queryTableByID(m_id, "msign_id", "meeting");
		//System.out.println(msign_id);
		smSignID=new SendJson().successJson("sendMSignId");
		smSignID.put("msign_id", "http://pan.baidu.com/share/qrcode?w=150&h=150&url="+msign_id);
		}else {
		smSignID=new SendJson().errorJson("sendMSignId");
		}
		return smSignID;
	}
	
	/**
	 * 签到操作
	 * @param pmess
	 */
	public JSONObject mSign(JSONObject pmess) {
		JSONObject SmSign;
		int u_id=pmess.getInt("u_id");
		int m_id=pmess.getInt("m_id");
		String rMsign_id=pmess.getString("mSign_id");
		String mSign_id=new DAO().queryTableByID(m_id, "mSign_id","meeting");
		//会议标识符验证
		if(mSign_id.equals(rMsign_id)) {
			//成功发送
			new DAO().updateMuByID(u_id,m_id, "sign", "1");
			SmSign=new SendJson().successJson("mSign");
		}else {
			//失败发送
			SmSign=new SendJson().errorJson("mSign");
		}
		return SmSign;
	}
}
