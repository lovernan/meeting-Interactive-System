package cn.whxlover.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import cn.whxlover.domain.user;
import cn.whxlover.service.registerSer;
import cn.whxlover.service.impl.registerSerImpl;

public class register extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ���ñ���
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset:UTF-8");
		// ����ǰ̨����������
		user user = new user();
		// �ռ����ݵ�����
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// ���������ļ����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// ����������
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> parseRequest = upload.parseRequest(request);
			for (FileItem item : parseRequest) {
				if (item.isFormField()) {
					// ��ͨ����
					String fieldName = item.getFieldName();
					String fieldValue = item.getString("UTF-8");
					map.put(fieldName, fieldValue);
					//����һ������ַ���
					
				} else {
					// �ļ�����
					String fileName = item.getName();
					String path = this.getServletContext().getRealPath("upload");
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(path + "/" + fileName);
					IOUtils.copy(in, out);
					in.close();
					out.close();
					item.delete();

					map.put("image", "upload/" + fileName);
				}
			}
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			map.put("token", uuid);
			
			BeanUtils.populate(user, map);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// �����ݵ�service��
		registerSer registerSer = new registerSerImpl();
		int issuccess = 0;
		try {
			issuccess = registerSer.register(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �ж��Ƿ����ӳɹ�
		if (issuccess > 0) {
			request.setAttribute("isadd", "success");
		} else {
			request.setAttribute("isadd", "fail");
		}
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}