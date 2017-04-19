package android.com.skyh.entity;

import java.util.HashMap;

public class EventSend {
    private HashMap<String, String> stringParamMap;




    public EventSend() {
    }

    public EventSend(HashMap<String, String> paramMap) {
        stringParamMap = paramMap;
    }

    public HashMap<String, String> getStringParamMap() {
        return stringParamMap;
    }

    public void setStringParamMap(HashMap<String, String> stringParamMap) {
        this.stringParamMap = stringParamMap;
    }


}
