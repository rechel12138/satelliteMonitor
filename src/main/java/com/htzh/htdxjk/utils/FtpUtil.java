package com.htzh.htdxjk.utils;


import com.alibaba.fastjson.JSON;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class FtpUtil {

	public FTPClient ftpClient = null;






	//连接ftp服务器
	public void connectFtp(String ip,int port,String userName,String password) {

		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("utf-8");
		try {
			System.out.println("connecting...ftp服务器:" + ip + ":" + port);
			ftpClient.connect(ip, port);
			ftpClient.login(userName, password);
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {

				System.out.println("连接ftp服务器失败:" + ip + ":" + port);
			}

			System.out.println("连接ftp服务器成功:" + ip + ":" + port);

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	//跳转至指定目录
	public void  changeDirectory(String directory) {

		boolean flag = true;
		try {
			flag = ftpClient.changeWorkingDirectory(directory);
			if (flag) {
				System.out.println("进入文件夹" + directory + " 成功！");

			} else {
				System.out.println("进入文件夹" + directory + " 失败！");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	//获取指定目录的文件列表
	public List<Map<String, Object>>  getFileList(String directory) {

		List<Map<String, Object>> fileList = new ArrayList<>();
		try {
			ftpClient.enterLocalPassiveMode();
			changeDirectory(directory);
			FTPFile[] ftpFiles = ftpClient.listFiles(directory);
			for(FTPFile file : ftpFiles){

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fileName", file.getName());
				map.put("fileSize", file.getSize()+"kb");
				if(file.getType()==0){
					map.put("fileType", "文件");
				}else {
					map.put("fileType", "文件夹");
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				map.put("fileModifyTime",sdf.format(file.getTimestamp().getTime()));

				fileList.add(map);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileList;
	}

	//下载指定文件到本地服务器

	/**
	 *
	 * @param remoteFileName
	 * @param remotePath
	 * @throws IOException
	 */
	public void download(String localPath,String remoteFileName,String remotePath) throws IOException {

		File localFile = new File(localPath + File.separatorChar+remoteFileName);
		OutputStream os = new FileOutputStream(localFile);
		ftpClient.retrieveFile(remoteFileName, os);
		os.close();

	}

	public static void main(String[] args) throws IOException {

		String ip="121.42.33.140";
		int port=21;
		String userName="qyu2866040001";
		String password="4r5t6y7u";
		String directory = "/ftplogs";

		String fileName="vsftpd_qxu1192770014_20170903.log.gz";
		String localPath="/Users/Rechel/Downloads/ftpdir";

		FtpUtil ftpUitl = new FtpUtil();
		//连接服务器
		ftpUitl.connectFtp(ip,port,userName,password);
		//跳转目录至/htdocs目录下，并列 出目录下所有文件
		List<Map<String, Object>> fileList = ftpUitl.getFileList(directory);
		for(Map<String,Object> file :fileList){


			System.out.println("正在列出文件夹："+directory+"下的文件....");
			System.out.println(JSON.toJSON(file));
		}

		//下载文件
		ftpUitl.download("D://upload/",fileName,"");

		ftpUitl.ftpClient.logout();
		ftpUitl.ftpClient.disconnect();
	}
}


