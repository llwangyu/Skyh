package android.com.skyh.tool;

import android.com.skyh.entity.DwhytResult;
import android.com.skyh.entity.FtpResult;
import android.com.skyh.entity.HyList;
import android.com.skyh.entity.LoginResult;
import android.com.skyh.entity.RsEntityResult;
import android.com.skyh.entity.TwoTree;
import android.com.skyh.entity.TwoTreeData;
import android.com.skyh.entity.UserTree;

public enum ProtocolType {
 //  LOGIN(LoginResult.class),

    BASE(Object.class),
    FTPRESULT(FtpResult.class),
    LOGIN(LoginResult.class),
    DWBMUSER(TwoTree.class),
    USERTREE(UserTree.class),
    HYXQ(RsEntityResult.class),
    DWHYT(DwhytResult.class),
    HYJL(HyList.class);


    private Class cls;

    ProtocolType(Class cls) {
        this.cls = cls;

    }

    public Class getCls() {
        return cls;
    }

    public static ProtocolType getFrom(String cls) {
        for (ProtocolType type : ProtocolType.values()) {
            if (type.name() == cls)
                return type;
        }
        return null;
    }
}
