package meeting.management.entity;

/**
 * 实体类，用于保存管理员用户表的信息。
 * 
 * @author Shuolin Yang
 *
 */
public class AdminUser {

	// 管理员的id
	private String userId = null;
	
	// 管理员的用户名
	private String username = null;
	
	// 管理员的密码
	private String password = null;
	
	// 管理员密码的盐
	private String salt = null;
	
	// 管理员的注册邮箱地址
	private String mail = null;
	
	/**
	 * 构造函数，用于初始化数据。
	 * 
	 * @param userId 用户id
	 * @param username 用户名
	 * @param password 用户密码
	 * @param salt 密码的盐值
	 * @param mail 用户的邮箱地址
	 */
	public AdminUser(String userId, String username, String password, String salt, String mail) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.mail = mail;
	}
	
	/**
	 * userId的访问器。
	 * 
	 * @return userId
	 */
	public String getUserId() {
		return this.userId;
	}
	
	/**
	 * username的访问器。
	 * 
	 * @return username
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * password的访问器。
	 * 
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * salt的访问器。
	 * 
	 * @return salt
	 */
	public String getsalt() {
		return this.salt;
	}
	
	/**
	 * mail的访问器。
	 * 
	 * @return mail
	 */
	public String getMail() {
		return this.mail;
	}
}
