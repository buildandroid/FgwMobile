package bean;

import java.io.Serializable;

/**
 * Created by wangxiao on 16/6/6.
 */
public class GwSignInfo implements Serializable{
    String SQTIME;
    String SQLEADER;
    String SQPEOPLE;
    String URL;
    String QPTIME;

    public String getSQTIME() {
        return SQTIME;
    }

    public void setSQTIME(String SQTIME) {
        this.SQTIME = SQTIME;
    }

    public String getSQLEADER() {
        return SQLEADER;
    }

    public void setSQLEADER(String SQLEADER) {
        this.SQLEADER = SQLEADER;
    }

    public String getSQPEOPLE() {
        return SQPEOPLE;
    }

    public void setSQPEOPLE(String SQPEOPLE) {
        this.SQPEOPLE = SQPEOPLE;
    }

    public String getQPTIME() {
        return QPTIME;
    }

    public void setQPTIME(String QPTIME) {
        this.QPTIME = QPTIME;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
