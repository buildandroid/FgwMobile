package bean;

import java.io.Serializable;

/**
 * Created by wangxiao on 16/6/6.
 */
public class GwAttachs implements Serializable {
    String TITLENAME;
    String ROWGUID;

    public String getTITLENAME() {
        return TITLENAME;
    }

    public void setTITLENAME(String TITLENAME) {
        this.TITLENAME = TITLENAME;
    }

    public String getROWGUID() {
        return ROWGUID;
    }

    public void setROWGUID(String ROWGUID) {
        this.ROWGUID = ROWGUID;
    }
}
