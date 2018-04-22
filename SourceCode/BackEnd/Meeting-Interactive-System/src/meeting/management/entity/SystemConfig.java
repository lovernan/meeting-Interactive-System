package meeting.management.entity;

/**
 * 用于保存系统设置表中数据的实体类。
 * 
 * @author Shuolin Yang
 *
 */
public class SystemConfig {

	// 会议可使用的功能
	private String validFunctions = null;
	
	// 用户能否登录
	private String ifUserLogin = null;;
	
	// 用户能否注册
	private String ifUserResister = null;
	
	/**
	 * 构造函数，用于初始化数据。
	 * 
	 * @param validFunctions 会议可使用的功能
	 * @param ifUserLogin 是否允许用户登录（yes/no）
	 * @param ifUserResister 是否允许用户注册（yes/no）
	 */
	public SystemConfig(String validFunctions, String ifUserLogin, String ifUserResister) {
		this.validFunctions = validFunctions;
		this.ifUserLogin = ifUserLogin;
		this.ifUserResister = ifUserResister;
	}
	public SystemConfig() {
		;
	}
	
	/**
	 * validFunctions的访问器。
	 * 
	 * @return validFunctions validFunctions值
	 */
	public String getValidFunctions() {
		return this.validFunctions;
	}
	
	/**
	 * validFunctions的设置器。
	 * 
	 * @param validFunctions validFunctions值
	 */
	public void setValidFunctions(String validFunctions) {
		this.validFunctions = validFunctions;
	}
	
	/**
	 * ifUserLogin的访问器。
	 * 
	 * @return ifUserLogin ifUserLogin值
	 */
	public String getIfUserLogin() {
		return this.ifUserLogin;
	}
	
	/**
	 * ifUserLogin的设置器。
	 * 
	 * @param ifUserLogin ifUserLogin值
	 */
	public void setIfUserLogin(String ifUserLogin) {
		this.ifUserLogin = ifUserLogin;
	}
	
	/**
	 * ifUserResister的访问器。
	 * 
	 * @return ifUserResister ifUserResister值
	 */
	public String getIfUserResister() {
		return this.ifUserResister;
	}
	
	/**
	 * ifUserResister的设置器。
	 * 
	 * @param ifUserResister ifUserResister值
	 */
	public void setIfUserResister(String ifUserResister) {
		this.ifUserResister = ifUserResister;
	}
}
