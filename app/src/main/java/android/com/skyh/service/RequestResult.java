package android.com.skyh.service;


import android.com.skyh.tool.ProtocolType;

/**
 * Created by Administrator on 2015/11/17.
 */
public abstract class RequestResult {
    public ProtocolType getType(){return null;};
    public void onSuccess(Object o){};
    public void onFailure(CharSequence failure){};
    public void OnErrorResult(CharSequence error){};

}