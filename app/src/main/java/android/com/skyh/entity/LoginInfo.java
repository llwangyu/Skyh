package android.com.skyh.entity;

import java.io.Serializable;

/**
 * 用户注册信息
 */
public class LoginInfo implements Serializable {
    private String yhdm;
    private String yhmm;
    private String yhlb;

    public String getYhdm() {
        return yhdm;
    }

    public void setYhdm(String yhdm) {
        this.yhdm = yhdm;
    }

    public String getYhmm() {
        return yhmm;
    }

    public void setYhmm(String yhmm) {
        this.yhmm = yhmm;
    }

    public String getYhlb() {
        return yhlb;
    }

    public void setYhlb(String yhlb) {
        this.yhlb = yhlb;
    }
}

