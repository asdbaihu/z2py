package com.z2py.util;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

public class HttpDownloadUtil {

	public static final int cache = 10 * 1024;
	public static final boolean isWindows;
	public static final String splash;
	public static final String root;
	static {
		if (System.getProperty("os.name") != null && System.getProperty("os.name").toLowerCase().contains("windows")) {
			isWindows = true;
			splash = "\\";
			root = "D:";
		} else {
			isWindows = false;
			splash = "/";
			root = "/search";
		}
	}

	/**
	 * 根据url下载文件，文件名从response header头中获取
	 * 
	 * @param url
	 * @return
	 */
	public static boolean download(String url) {
		return download(url, null);
	}
	
	public static void downloadImg(String url, String filepath) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			httpget.addHeader("Cookie", "Hm_lvt_0ef4d2ab3569e6ba0768bca5c4a7b7bf=1518864924,1519040437,1519221210,1519824240; py_loginauth=WyJxNTIyODQ5MTAwIiwxNTE5ODI0MjExLCJlNDhmN2UyYzBjOTkzMGQyIl0%3D; Hm_lvt_6d5e86213b07ede18ec639f1da3bc86b=1518864927,1519040439,1519221211,1519824241; yunsuo_session_verify=2bb686d020073d1a33ce041dfdc38621; PHPSESSID=ol6rlv7o6vpsd79td00h8kggr3; Hm_lpvt_0ef4d2ab3569e6ba0768bca5c4a7b7bf=1519824240; UBGLAI63GV=pbcfm.1519824241; Hm_lpvt_6d5e86213b07ede18ec639f1da3bc86b=1519824241; cpmpop_cm_t4=true");
			HttpResponse response = client.execute(httpget);

			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			File file = new File(filepath);
			file.getParentFile().mkdirs();
			FileOutputStream fileout = new FileOutputStream(file);
			/**
			 * 根据实际运行效果 设置缓冲区大小
			 */
			byte[] buffer = new byte[cache];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			is.close();
			fileout.flush();
			fileout.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String downloadFile(String url, String filepath) {
		String filename = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
			HttpResponse response = client.execute(httpget);

			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			filename = getFileName(response);
			if (filename == null)
				return "";
			filepath += filename;
			File file = new File(filepath);
			file.getParentFile().mkdirs();
			FileOutputStream fileout = new FileOutputStream(file);
			/**
			 * 根据实际运行效果 设置缓冲区大小
			 */
			byte[] buffer = new byte[cache];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			is.close();
			fileout.flush();
			fileout.close();

		} catch (Exception e) {
			e.printStackTrace();
			return filename;
		}
		return filename;
	}

	/**
	 * 根据url下载文件，保存到filepath中
	 * 
	 * @param url
	 * @param filepath
	 * @return
	 */
	public static boolean download(String url, String filepath) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			httpget.addHeader("Cookie", "Hm_lvt_0ef4d2ab3569e6ba0768bca5c4a7b7bf=1518864924,1519040437,1519221210,1519824240; py_loginauth=WyJxNTIyODQ5MTAwIiwxNTE5ODI0MjExLCJlNDhmN2UyYzBjOTkzMGQyIl0%3D; Hm_lvt_6d5e86213b07ede18ec639f1da3bc86b=1518864927,1519040439,1519221211,1519824241; yunsuo_session_verify=2bb686d020073d1a33ce041dfdc38621; PHPSESSID=ol6rlv7o6vpsd79td00h8kggr3; Hm_lpvt_0ef4d2ab3569e6ba0768bca5c4a7b7bf=1519824240; UBGLAI63GV=pbcfm.1519824241; Hm_lpvt_6d5e86213b07ede18ec639f1da3bc86b=1519824241; cpmpop_cm_t4=true");
			httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/57.0");
			HttpResponse response = client.execute(httpget);

			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			if (filepath == null)
				filepath = getFilePath(response);
			File file = new File(filepath);
			file.getParentFile().mkdirs();
			FileOutputStream fileout = new FileOutputStream(file);
			/**
			 * 根据实际运行效果 设置缓冲区大小
			 */
			byte[] buffer = new byte[cache];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			is.close();
			fileout.flush();
			fileout.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获取response要下载的文件的默认路径
	 * 
	 * @param response
	 * @return
	 */
	public static String getFilePath(HttpResponse response) {
		String filepath = root + splash;
		String filename = getFileName(response);

		if (filename != null) {
			filepath += filename;
		} else {
			filepath += getRandomFileName();
		}
		return filepath;
	}

	/**
	 * 获取response header中Content-Disposition中的filename值
	 * 
	 * @param response
	 * @return
	 */
	public static String getFileName(HttpResponse response) {
		Header contentHeader = response.getFirstHeader("Content-Disposition");
		String filename = null;
		if (contentHeader != null) {
			HeaderElement[] values = contentHeader.getElements();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					try {
						// filename = new
						// String(param.getValue().toString().getBytes(),
						// "utf-8");
						filename=URLDecoder.decode(param.getValue(),"utf-8");
						//filename = param.getValue();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return filename;
	}

	/**
	 * 获取随机文件名
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		return String.valueOf(System.currentTimeMillis());
	}

	public static void outHeaders(HttpResponse response) {
		Header[] headers = response.getAllHeaders();
		for (int i = 0; i < headers.length; i++) {
			System.out.println(headers[i]);
		}
	}
}
