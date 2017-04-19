package android.com.skyh.entity;

import java.util.List;

/**
 * Created by Administrator on 2017-03-29.
 */

public class UserInfo {
    private String type;
    private String zzdm;
    private String zzmc;
    private List<UserInfo> children;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZzdm() {
        return zzdm;
    }

    public void setZzdm(String zzdm) {
        this.zzdm = zzdm;
    }

    public String getZzmc() {
        return zzmc;
    }

    public void setZzmc(String zzmc) {
        this.zzmc = zzmc;
    }

    public List<UserInfo> getChildren() {
        return children;
    }

    public void setChildren(List<UserInfo> children) {
        this.children = children;
    }
}
