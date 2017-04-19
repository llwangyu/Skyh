package android.com.skyh.service;

import android.com.skyh.until.Util;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FileTool {

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录，是linux下的目录形式,如/photo/
	 * @param filename
	 *            上传到FTP服务器上的文件名,是自己定义的名字，
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String url, int port, String username,
			String password, String path, String filename, InputStream input) {
		boolean success = false;


		FTPClient ftp = new FTPClient();
		
		
		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.setBufferSize(1024);
			ftp.setDataTimeout(12000);
			boolean bb = ftp.changeWorkingDirectory(path);
			if(bb){

			}else{
				ftp.makeDirectory(path);
				ftp.changeWorkingDirectory(path);
			}
			ftp.enterLocalPassiveMode();//Switch to passive mode

//			FileOutputStream fosto = new FileOutputStream(Environment.getExternalStorageDirectory()+"/DCIM/"+"hhhhh.jpg");
//
//			byte[] bt = new byte[1024];
//			int c;
//			while((c=input.read(bt)) > 0){
//				fosto.write(bt,0,c);
//			}
//			//关闭输入、输出流
//
//			fosto.close();
			ftp.storeFile(filename, input);
		//	success = ftp.storeFile(filename, input);

			input.close();
			ftp.logout();
			success = true;

		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;

	}

	/**
	 * 下载文件
	 * @param FilePath  要存放的文件的路径
	 * @param FileName   远程FTP服务器上的那个文件的名字
	 * @return   true为成功，false为失败
	 */
	public static boolean downLoadFile(String url, int port, String username,
								String password,String FilePath, String FileName,String filePaths) {
		FTPClient ftp = new FTPClient();


		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return false;
			}
			ftp.changeWorkingDirectory(filePaths);
			//			// 列出该目录下所有文件
			FTPFile[] files = ftp.listFiles();

			// 遍历所有文件，找到指定的文件
			for (FTPFile file : files) {
				if (file.getName().equals(FileName)) {
					//根据绝对路径初始化文件
					File localFile = new File(FilePath);

					// 输出流
					OutputStream outputStream = new FileOutputStream(localFile);

					// 下载文件
					ftp.retrieveFile(file.getName(), outputStream);

					//关闭流
					outputStream.close();
				}
			}

			//退出登陆FTP，关闭ftpCLient的连接
			ftp.logout();
			ftp.disconnect();

		}catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//		if (!ftpClient.isConnected())
//		{
//			if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword))
//			{
//				return false;
//			}
//		}
//
//		try {
//			// 转到指定下载目录
//			ftpClient.changeWorkingDirectory("/data");
//
//			// 列出该目录下所有文件
//			FTPFile[] files = ftpClient.listFiles();
//
//			// 遍历所有文件，找到指定的文件
//			for (FTPFile file : files) {
//				if (file.getName().equals(FileName)) {
//					//根据绝对路径初始化文件
//					File localFile = new File(FilePath);
//
//					// 输出流
//					OutputStream outputStream = new FileOutputStream(localFile);
//
//					// 下载文件
//					ftpClient.retrieveFile(file.getName(), outputStream);
//
//					//关闭流
//					outputStream.close();
//				}
//			}
//
//			//退出登陆FTP，关闭ftpCLient的连接
//			ftpClient.logout();
//			ftpClient.disconnect();
//
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//			return true;
	//	}
		return true;
}
}
