package android.com.skyh.until;


import android.com.skyh.tool.ProtocolType;

public interface SendProtocolImpl {
public void sendProtocol(String protocol, Object map, String method, ProtocolType type);
public void isInterrupt(boolean isInterrupt);
}
