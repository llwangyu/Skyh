package android.com.skyh.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResult {
	private LoginData data;
	private String message;
	private boolean success;

	public LoginData getData() {
		return data;
	}

	public void setData(LoginData data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
