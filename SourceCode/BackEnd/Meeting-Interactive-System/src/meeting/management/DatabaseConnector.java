package meeting.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import meeting.management.entity.AdminUser;
import meeting.management.entity.Blacklist;
import meeting.management.entity.SystemConfig;

public class DatabaseConnector {

	/**
	 * 用于保存数据库连接的各变量。
	 */
	private String url = "jdbc:mysql://localhost:3306/meeting?useUnicode=true&characterEncoding=utf-8";
	
	private String user = "root";
	
	private String password = "Iloveyou151";
	
	private Connection connection = null;
	
	private Statement statement = null;
	
	private ResultSet result = null;
	
	/**
	 * 构造函数，用于连接数据库。
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DatabaseConnector() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(url, user, password);
	}
	
	/**
	 * 用于检测数据库中是否有相同用户名的用户存在。
	 * 
	 * @param username 用户名
	 * @return 有相同存在返回true，没有返回false
	 * @throws SQLException 
	 */
	private boolean ifUserExist(String username) throws SQLException {
		
		boolean flag = false;
		
		// 判断数据库中是否有相同的用户名存在
		statement = connection.createStatement();
		String sql = "SELECT * FROM ma_admin WHERE ad_username='" + username + "';";
		result = statement.executeQuery(sql);
		
		if (result.next()) {		// 有相同用户存在
			flag = true;
		}
		
		result.close();
		statement.close();
		
		return flag;
	}
	
	/**
	 * 用于检测黑名单中是否有相同的UserId存在。
	 * 
	 * @param userId UserId
	 * @return 有相同存在返回true，没有返回false
	 * @throws SQLException 
	 */
	private boolean ifBlackListUserExist(String userId) throws SQLException {
		
		boolean flag = false;
		
		// 判断数据库中是否有相同userId存在
		statement = connection.createStatement();
		String sql = "SELECT * FROM ma_blacklist WHERE bl_userid='" + userId + "'";
		result = statement.executeQuery(sql);
		
		if (result.next()) {		// 有相同用户id存在
			flag = true;
		}
		
		result.close();
		statement.close();
		
		return flag;
	}
	
	/**
	 * 获取管理员信息的方法。
	 * 
	 * @param username 管理员的用户名
	 * @return 包含有管理员信息的实体对象，若id为-1则用户不存在
	 * @throws SQLException
	 */
	public AdminUser getUserInfo(String username) throws SQLException {
		
		// 查询用户名对应的密码
		Statement statement = connection.createStatement();
		String sql = "SELECT * FROM ma_admin WHERE ad_username='" + username + "';";
		result = statement.executeQuery(sql);
		
		// 构造返回数组列表
		AdminUser user = null;
		if (result.next()) {		// 用户存在
			user = new AdminUser(String.valueOf(result.getInt("ad_userid")), result.getString("ad_username"),
					result.getString("ad_password"),result.getString("ad_salt"), result.getString("ad_mail"));
		} else {					// 用户不存在
			user = new AdminUser("-1", null, null, null, null);
		}

		result.close();
		statement.close();
		
		// 返回数据
		return user;
	}
	
	/**
	 * 用于向管理员表中添加一个新的管理员。
	 * 
	 * @param user 保存有username，password和mail的AdminUser对象
	 * @return 用户名重复返回false，插入数据成功返回true
	 * @throws SQLException
	 */
	public boolean setUserInfo(AdminUser user) throws SQLException {
		
		// 检测是否有相同用户名的用户存在
		if (ifUserExist(user.getUsername())) {		// 相同用户名存在
			return false;
		}
		
		// 向数据库插入数据
		statement = connection.createStatement();
		String sql = "INSERT INTO ma_admin VALUES(null, '"
				+ user.getUsername() + "', '" + user.getPassword() + "', '" + user.getsalt() + "', '" + user.getMail() + "');";
		statement.executeUpdate(sql);
		statement.close();
		
		return true;
	}
	
	/**
	 * 用于获取系统当前设置的方法。
	 * 
	 * @return 保存有系统当前设置的对象
	 * @throws SQLException 
	 */
	public SystemConfig getSystemConfig() throws SQLException {
		
		// 查询系统当前设置
		statement = connection.createStatement();
		String sql = "SELECT * FROM ma_config;";
		result = statement.executeQuery(sql);
		
		// 构造返回对象
		SystemConfig config = new SystemConfig();
		while (result.next()) {
			if (result.getString("cg_key").equals("validFunctions"))
				config.setValidFunctions(result.getString("cg_value"));
			if (result.getString("cg_key").equals("ifUserLogin"))
				config.setIfUserLogin(result.getString("cg_value"));
			if (result.getString("cg_key").equals("ifUserResister"))
				config.setIfUserResister(result.getString("cg_value"));
		}
		
		result.close();
		statement.close();
		
		return config;
	}
	
	/**
	 * 用于更新数据库中的系统设置。
	 * 
	 * @param config 保存有系统设置信息的SystemConfig对象
	 * @throws SQLException 
	 */
	public void setSystemConfig(SystemConfig config) throws SQLException {
		
		// 插入数据
		statement = connection.createStatement();
		String sql =  null;
		sql = "UPDATE ma_config SET cg_value='" + config.getValidFunctions() + "' WHERE cg_key='validFunctions';";
		statement.executeUpdate(sql);
		sql = "UPDATE ma_config SET cg_value='" + config.getIfUserLogin() + "' WHERE cg_key='ifUserLogin';";
		statement.executeUpdate(sql);
		sql = "UPDATE ma_config SET cg_value='" + config.getIfUserResister() + "' WHERE cg_key='ifUserResister';";
		statement.executeUpdate(sql);
		
		statement.close();
	}
	
	/**
	 * 用于获取黑名单用户。
	 * 
	 * @return 含有黑名单用户id的数组列表
	 * @throws SQLException 
	 */
	public Blacklist getBlackList() throws SQLException {
		
		// 查询数据
		statement = connection.createStatement();
		String sql = "SELECT * FROM ma_blacklist;";
		result = statement.executeQuery(sql);
		
		// 构造数组列表
		Blacklist blacklist = new Blacklist();
		while (result.next()) {
			blacklist.setUser(result.getString("bl_userid"), result.getString("bl_starttime"));
		}
		
		result.close();
		statement.close();
		
		return blacklist;
	}
	
	/**
	 * 用于向黑名单中添加一个新用户id。
	 * 
	 * @param userId 用户id
	 * @return 若添加成功返回true，若有重复的id返回false
	 * @throws SQLException 
	 */
	public boolean setBlackList(String userId) throws SQLException {
		
		// 检测是否有重复的userid
		if (ifBlackListUserExist(userId)) {			// 有重复的userid存在
			return false;
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		
		// 向数据库中插入数据
		statement = connection.createStatement();
		String sql = "INSERT INTO ma_blacklist VALUES('" + userId + "', '" + time + "');";
		statement.executeUpdate(sql);
		
		statement.close();
		
		return true;
	}
	
	/**
	 * 释放数据库连接。
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		connection.close();
	}
}
