package priv.wrebuild.Mcontroller;

import org.json.JSONObject;

public class SendJson {
	
	/**
	 * 操作失败
	 * @return
	 */
	public JSONObject errorJson(String operation) {
		JSONObject errormess=new JSONObject();
		errormess.put("result", operation+" error");
		return errormess;
	}
	
	/**
	 * 操作成功
	 * @return
	 */
	public JSONObject successJson(String operatoion) {
		JSONObject success=new JSONObject();
		success.put("result",operatoion+" success");
		return success;
	}
}
