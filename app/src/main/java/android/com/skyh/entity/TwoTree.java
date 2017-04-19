package android.com.skyh.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Administrator on 2017-03-29.
 */

public class TwoTree  {
    @JsonProperty(value = "TUserlinkList")
    private List<TwoTreeData> tUserlinkList;

    public List<TwoTreeData> gettUserlinkList() {
        return tUserlinkList;
    }

    public void settUserlinkList(List<TwoTreeData> tUserlinkList) {
        this.tUserlinkList = tUserlinkList;
    }
}
