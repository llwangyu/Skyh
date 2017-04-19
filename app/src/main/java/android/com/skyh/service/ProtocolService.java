package android.com.skyh.service;


import android.com.skyh.base.BaseActivity;
import android.com.skyh.tool.PrefName;
import android.com.skyh.tool.ProtocolType;
import android.com.skyh.until.SendProtocolImpl;
import android.com.skyh.until.Util;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA. User: weiguo.ren Date: 13-9-13 Time: 上午10:27 To
 * change this template use File | Settings | File Templates.
 */
public class ProtocolService {
    private static ExecutorService mExecutorService = Executors
            .newFixedThreadPool(5);

    public static void sendProtocol(String protocol, Object pram,
                                    String method, ProtocolType type) throws InvocationTargetException,
            NoSuchMethodException, InstantiationException,
            IllegalAccessException {


        sendProtocolImpl.sendProtocol(protocol, pram, method, type);
    }

    private static final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            switch (msg.what) {
                // 处理结果
                case 10:
                    RequestResult requestResult = BaseActivity
                            .getResult((ProtocolType) data.getSerializable("type"));
                    requestResult.onSuccess(msg.obj);
                    break;
                case 20:
                    BaseActivity.getResult(
                            (ProtocolType) data.getSerializable("type")).onFailure(
                            (CharSequence) msg.obj);
                    break;
                case 30:
                    BaseActivity.getResult(
                            (ProtocolType) data.getSerializable("type")).onFailure(
                            (CharSequence) msg.obj);
                    break;
                case 40:
                    BaseActivity.getResult(
                            (ProtocolType) data.getSerializable("type"))
                            .OnErrorResult((CharSequence) msg.obj);
                    break;
            }

        }

        ;
    };
    public static SendProtocolImpl sendProtocolImpl = new SendProtocolImpl() {
        private boolean isInterrupt = false;
        private ObjectMapper oMapper = new ObjectMapper();
        @Override
        public void sendProtocol(final String protocol, final Object pram,
                                 final String method, final ProtocolType type) {
            mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    InputStream in = null;
                    Bundle data = new Bundle();
                    try {
                        if (!isInterrupt) {
                            Util.print("isInterrupt 正常运行！");
                            data.putSerializable("type", type);
                            if (method.equals(PrefName.GET)) {
                                in = HttpRequest.GET(protocol, pram);
                            } else if (method.equals(PrefName.POST)) {
                                in = HttpRequest.POST(protocol, pram);
                            } else if (method.equals(PrefName.POST_LONG_TIMEOUT)) {
                                in = HttpRequest.postLongTimeOut(protocol, pram);
                            } else if (method.equals(PrefName.POST_FILE)) {
                                in = HttpRequest.postImageNew(protocol, pram);
                            }  else if (method.equals(PrefName.POST_FILE1)) {
                            in = HttpRequest.postImage1(protocol, pram);
                        }else if (method.equals(PrefName.POST_FILE2)) {
                                in = HttpRequest.postImage2(protocol, pram);
                            }
                        else if (method.equals(PrefName.POST_PHOTO)) {
                                in = HttpRequest.uploadFile(protocol, pram);
                            } else if (method.equals(PrefName.POST_MORE_FILE)) {
                                in = HttpRequest.postMoreFile(protocol, pram);
                            } else if (method.equals(PrefName.POST_SIGN)) {
                                in = HttpRequest.postSign(protocol, pram);
                            }
                            if (in == null) {
                                Util.print("server error");
                                sendProtocolImpl.isInterrupt(false);
                                Message msg = new Message();
                                msg.what = 40;
                                msg.setData(data);
                                msg.obj = "server error";
                                handler.sendMessage(msg);
                                return;
                            }
                            String json = HttpRequest.InputStreamTOString(in);
                            Util.print("json----------------" + json);
                        //    System.out.println("执行到这里"+json);

                            Message msg = new Message();
                            @SuppressWarnings("unchecked")
                            Object object = oMapper.readValue(json,
                                    type.getCls());
                            msg.what = 10;
                            msg.obj = object;
                            //       }
                            msg.setData(data);
                            handler.sendMessage(msg);
                        } else {
                            Util.print("isInterrupt 中断运行！");
                            sendProtocolImpl.isInterrupt(false);
                            Message msg = new Message();
                            msg.what = 20;
                            msg.obj = "获取数据异常";
                            msg.setData(data);
                            handler.sendMessage(msg);
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        Util.print("IllegalArgumentException");
                        sendProtocolImpl.isInterrupt(false);
                        Message msg = new Message();
                        msg.what = 40;
                        msg.setData(data);
                        msg.obj = "获取数据异常";
                        handler.sendMessage(msg);
                    } catch (ConnectTimeoutException timeout) {
                        timeout.printStackTrace();
                        Util.print("timeout");
                        sendProtocolImpl.isInterrupt(false);
                        Message msg = new Message();
                        msg.what = 40;
                        msg.setData(data);
                        msg.obj = "请求超时";
                        handler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Util.print("IOException");
                        sendProtocolImpl.isInterrupt(false);
                        Message msg = new Message();
                        msg.what = 40;
                        msg.setData(data);
                        msg.obj = "请求超时";
                        handler.sendMessage(msg);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                        Util.print("InvocationTargetException");
                        sendProtocolImpl.isInterrupt(false);
                        Message msg = new Message();
                        msg.what = 40;
                        msg.setData(data);
                        msg.obj = "获取数据异常";
                        handler.sendMessage(msg);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        Util.print("NoSuchMethodException");
                        sendProtocolImpl.isInterrupt(false);
                        Message msg = new Message();
                        msg.what = 40;
                        msg.setData(data);
                        msg.obj = "获取数据异常";
                        handler.sendMessage(msg);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                        Util.print("InstantiationException");
                        sendProtocolImpl.isInterrupt(false);
                        Message msg = new Message();
                        msg.what = 40;
                        msg.setData(data);
                        msg.obj = "获取数据异常";
                        handler.sendMessage(msg);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        Util.print("IllegalAccessException");
                        sendProtocolImpl.isInterrupt(false);
                        Message msg = new Message();
                        msg.what = 40;
                        msg.setData(data);
                        msg.obj = "获取数据异常";
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Util.print("json Exception");
                        sendProtocolImpl.isInterrupt(false);
                        Message msg = new Message();
                        msg.what = 40;
                        msg.setData(data);
                        msg.obj = "获取数据异常";
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void isInterrupt(boolean isInterrupt) {
            this.isInterrupt = isInterrupt;
        }
    };


}
