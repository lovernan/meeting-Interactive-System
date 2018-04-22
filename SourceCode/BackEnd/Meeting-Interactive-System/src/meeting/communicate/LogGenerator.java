package meeting.communicate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * 用于控制记录日志的类。
 * 
 * @author Shuolin Yang
 *
 */
class LogGenerator {
	
	/**
	 * 记录日志文件的路径。
	 */
	private static String path = "/opt/apache-tomcat-9.0.6/logs/meeting.log";
	
	/**
	 * 记录异常信息的方法。
	 * 
	 * @param e 发生的异常
	 */
	public void log(Throwable e) {
		try {
			FileWriter writer = new FileWriter(path, true);
			String log = "";
			log += new Date().toString() + "\r\n";
			log += "Exception: \r\n";
			log += e.toString() + "\r\n";
			log += "------------------------------------------------\r\n";
			writer.write(log);
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
