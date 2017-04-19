package android.com.skyh.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.com.skyh.service.ProtocolService;
import android.com.skyh.service.RequestResult;
import android.com.skyh.tool.ProtocolType;
import android.com.skyh.until.Util;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;


public class BaseActivity extends Activity {
    public static EnumMap<ProtocolType, RequestResult> getRequestResult = new EnumMap<ProtocolType, RequestResult>(ProtocolType.class);

    private ProgressDialog progressDialog;

    /**
     * activity跳转工具类，负责Activity的基本跳转时间
     * @param
     * @param cls
     */
    public void JumpToActivity(Class<?> cls) {
        Intent intent = null;
        intent = new Intent(this, cls);// 实例化Intent信使
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);// 设置跳转标志为如此Activity存在则把其从任务堆栈中取出放到最上方
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 在跳转之前情况当前任务堆栈中此Activity的顶部任务
        this.startActivity(intent);// 开始跳转
    }

    public void JumpToActivity(Class<?> cls, Bundle bundle) {
        Intent intent = null;
        intent = new Intent(this, cls);// 实例化Intent信使
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);// 设置跳转标志为如此Activity存在则把其从任务堆栈中取出放到最上方
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 在跳转之前情况当前任务堆栈中此Activity的顶部任务
        if (bundle != null)
            intent.putExtras(bundle);
        this.startActivity(intent);// 开始跳转
    }

    public static void setRequestResult(RequestResult requestResult, ProtocolType type) {
        BaseActivity.getRequestResult.put(type, requestResult);
    }

    public static RequestResult getResult(ProtocolType type) {
        return BaseActivity.getRequestResult.get(type);
    }
    public void sendRequest(String protocol, Object obj, String method, ProtocolType type, RequestResult requestResult) {
        try {
            ProtocolService.sendProtocol(protocol, obj, method, type);
            setRequestResult(requestResult, type);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public void showProgBar(String str) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(str);
        progressDialog.show();
    }
    public void hideProgBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }
    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否放弃未完成编辑？");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                       finish();
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        builder.create().show();
    }
    protected void dialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("完成资料上传");
        builder.setPositiveButton("点击返回主页",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

    }
}

