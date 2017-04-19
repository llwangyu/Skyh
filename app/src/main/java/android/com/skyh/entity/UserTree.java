package android.com.skyh.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Administrator on 2017-03-29.
 */

public class UserTree {
    private String message;
    private boolean success;
    private List<UserInfo> data;

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

    public List<UserInfo> getData() {
        return data;
    }

    public void setData(List<UserInfo> data) {
        this.data = data;
    }
}
