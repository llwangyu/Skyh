package android.com.skyh.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FtpResult {
	@JsonProperty(value = "IMGPATH")
	private String imgPath;
@JsonProperty(value = "NULLIMGPATH")
	private String nullImgpath;
@JsonProperty(value = "FTP_USER")
	private String ftp_user;
@JsonProperty(value = "FTP_URL")
	private String ftp_url;
@JsonProperty(value = "FTP_PORT")
	private String ftp_port;
@JsonProperty(value = "FTP_PWD")
	private String ftp_pwd;

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getNullImgpath() {
		return nullImgpath;
	}

	public void setNullImgpath(String nullImgpath) {
		this.nullImgpath = nullImgpath;
	}

	public String getFtp_user() {
		return ftp_user;
	}

	public void setFtp_user(String ftp_user) {
		this.ftp_user = ftp_user;
	}

	public String getFtp_url() {
		return ftp_url;
	}

	public void setFtp_url(String ftp_url) {
		this.ftp_url = ftp_url;
	}

	public String getFtp_port() {
		return ftp_port;
	}

	public void setFtp_port(String ftp_port) {
		this.ftp_port = ftp_port;
	}

	public String getFtp_pwd() {
		return ftp_pwd;
	}

	public void setFtp_pwd(String ftp_pwd) {
		this.ftp_pwd = ftp_pwd;
	}
}
