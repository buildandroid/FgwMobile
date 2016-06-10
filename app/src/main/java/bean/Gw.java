package bean;

import java.io.Serializable;

/**
 * Created by wangxiao on 16/6/6.
 */
public class Gw implements Serializable {
    String BARCODE;
    String TITLE;
    String UNIT1;
    String DATE1;
    String TYPE;
    String GWSTATUS;
    String SQPEOPLE;
    String SQLEADER;
    String SQTIME;
    String QPSTATUS;

    public String getBARCODE() {
        return BARCODE;
    }

    public void setBARCODE(String BARCODE) {
        this.BARCODE = BARCODE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getUNIT1() {
        return UNIT1;
    }

    public void setUNIT1(String UNIT1) {
        this.UNIT1 = UNIT1;
    }

    public String getDATE1() {
        return DATE1;
    }

    public void setDATE1(String DATE1) {
        this.DATE1 = DATE1;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getGWSTATUS() {
        return GWSTATUS;
    }

    public void setGWSTATUS(String GWSTATUS) {
        this.GWSTATUS = GWSTATUS;
    }

    public String getSQPEOPLE() {
        return SQPEOPLE;
    }

    public void setSQPEOPLE(String SQPEOPLE) {
        this.SQPEOPLE = SQPEOPLE;
    }

    public String getSQLEADER() {
        return SQLEADER;
    }

    public void setSQLEADER(String SQLEADER) {
        this.SQLEADER = SQLEADER;
    }

    public String getSQTIME() {
        return SQTIME;
    }

    public void setSQTIME(String SQTIME) {
        this.SQTIME = SQTIME;
    }

    public String getQPSTATUS() {
        return QPSTATUS;
    }

    public void setQPSTATUS(String QPSTATUS) {
        this.QPSTATUS = QPSTATUS;
    }
}