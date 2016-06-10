package bean;

import java.io.Serializable;

/**
 * Created by wangxiao on 16/6/6.
 */
public class GwForm implements Serializable{
    public String ZHENGWEN;
    public String NUMBERCOUNT;//发文单号
    public String UPDATEDATE;//更新时间
    public String HANLDEDATE;//拟稿日期
    public String HUIQIANIDEAR;//会签处室意见
    public String REMARK;//附注（联合发文）
    public String INFOPUBLICMODE;//公开属性
    public String DOCTITLE;//文件标题
    public String COPYNUMBER;//份数（联合发文）
    public String ZHUBANIDEAR;//主办处室审核意见
    public String BANGONGSHIIDEAR;//办公室审核意见
    public String DRAFTTEL;//主办处室承办人联系电话
    public String PRINCIPALDEPT;//主送单位（联合发文）
    public String WORKFLOWINSTANCE_GUID;
    public String BARCODE;//条码号
    public String DOCNUMBER;//文号
    public String INSTANCESTATUS;//发文状态[字典]
    public String LEADERDIRECTION;//领导批示
    public String SIGNCONTENT;//会签栏（联合发文）
    public String NIGAOCHUSHI;//拟稿处室
    public String URGENTDEGREE;//紧急程度
    public String LIANHEFAWEN;//联合发文标记，1：联合发文


    //收文
    //String BARCODE;
    public String JINJICHENGDU;//紧急程度
    public String WENHAO;//收文单号
    public String SHOUWENRIQI;//收文日期
    public String LAIWENDANWEI;//来文单位
    public String WENJIANBIAOTI;//文件标题
    public String BANWENLEIXING;//办文类型
    public String BANLISHIXIAN;//办理期限
//    String INSTANCESTATUS;//收文状态[字典]
    public String PISHI;//领导批示
    public String NIBANYIJIAN;//拟办意见
    public String CHENGBAN;//承办情况
    public String BEIZHU;//附注
    public String CHENBANREN;//承办人
    public String TELEPHONE;//承办人电话
    public String FUHEREN;//复核人
//    String UPDATEDATE;//更新时间

    //签报
//    String BARCODE;//条码号
    public String BANWENHAO;//签报单号
    public String QIANBAORIQI;//拟稿日期
//    String NIGAOCHUSHI;//拟稿处室
//    String DOCTITLE;//文件标题
//    String INSTANCESTATUS;//签报状态[字典]
    public String LEADER;//领导批示
    public String HUIQIAN;//会签栏
    public String ZHUBAN;//主办处室审核意见
    public String DIANHUA;//主办处室承办人联系电话
//    String UPDATEDATE;//更新时间

    //程序处理数据
    public String year;
    public String month;
    public String day;


    public String getZHENGWEN() {
        return ZHENGWEN;
    }

    public void setZHENGWEN(String ZHENGWEN) {
        this.ZHENGWEN = ZHENGWEN;
    }

    public String getNUMBERCOUNT() {
        return NUMBERCOUNT;
    }

    public void setNUMBERCOUNT(String NUMBERCOUNT) {
        this.NUMBERCOUNT = NUMBERCOUNT;
    }

    public String getHANLDEDATE() {
        return HANLDEDATE;
    }

    public void setHANLDEDATE(String HANLDEDATE) {
        this.HANLDEDATE = HANLDEDATE;
    }

    public String getUPDATEDATE() {
        return UPDATEDATE;
    }

    public void setUPDATEDATE(String UPDATEDATE) {
        this.UPDATEDATE = UPDATEDATE;
    }

    public String getHUIQIANIDEAR() {
        return HUIQIANIDEAR;
    }

    public void setHUIQIANIDEAR(String HUIQIANIDEAR) {
        this.HUIQIANIDEAR = HUIQIANIDEAR;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getDOCTITLE() {
        return DOCTITLE;
    }

    public void setDOCTITLE(String DOCTITLE) {
        this.DOCTITLE = DOCTITLE;
    }

    public String getINFOPUBLICMODE() {
        return INFOPUBLICMODE;
    }

    public void setINFOPUBLICMODE(String INFOPUBLICMODE) {
        this.INFOPUBLICMODE = INFOPUBLICMODE;
    }

    public String getZHUBANIDEAR() {
        return ZHUBANIDEAR;
    }

    public void setZHUBANIDEAR(String ZHUBANIDEAR) {
        this.ZHUBANIDEAR = ZHUBANIDEAR;
    }

    public String getCOPYNUMBER() {
        return COPYNUMBER;
    }

    public void setCOPYNUMBER(String COPYNUMBER) {
        this.COPYNUMBER = COPYNUMBER;
    }

    public String getBANGONGSHIIDEAR() {
        return BANGONGSHIIDEAR;
    }

    public void setBANGONGSHIIDEAR(String BANGONGSHIIDEAR) {
        this.BANGONGSHIIDEAR = BANGONGSHIIDEAR;
    }

    public String getDRAFTTEL() {
        return DRAFTTEL;
    }

    public void setDRAFTTEL(String DRAFTTEL) {
        this.DRAFTTEL = DRAFTTEL;
    }

    public String getPRINCIPALDEPT() {
        return PRINCIPALDEPT;
    }

    public void setPRINCIPALDEPT(String PRINCIPALDEPT) {
        this.PRINCIPALDEPT = PRINCIPALDEPT;
    }

    public String getWORKFLOWINSTANCE_GUID() {
        return WORKFLOWINSTANCE_GUID;
    }

    public void setWORKFLOWINSTANCE_GUID(String WORKFLOWINSTANCE_GUID) {
        this.WORKFLOWINSTANCE_GUID = WORKFLOWINSTANCE_GUID;
    }

    public String getBARCODE() {
        return BARCODE;
    }

    public void setBARCODE(String BARCODE) {
        this.BARCODE = BARCODE;
    }

    public String getDOCNUMBER() {
        return DOCNUMBER;
    }

    public void setDOCNUMBER(String DOCNUMBER) {
        this.DOCNUMBER = DOCNUMBER;
    }

    public String getINSTANCESTATUS() {
        return INSTANCESTATUS;
    }

    public void setINSTANCESTATUS(String INSTANCESTATUS) {
        this.INSTANCESTATUS = INSTANCESTATUS;
    }

    public String getLEADERDIRECTION() {
        return LEADERDIRECTION;
    }

    public void setLEADERDIRECTION(String LEADERDIRECTION) {
        this.LEADERDIRECTION = LEADERDIRECTION;
    }

    public String getSIGNCONTENT() {
        return SIGNCONTENT;
    }

    public void setSIGNCONTENT(String SIGNCONTENT) {
        this.SIGNCONTENT = SIGNCONTENT;
    }

    public String getNIGAOCHUSHI() {
        return NIGAOCHUSHI;
    }

    public void setNIGAOCHUSHI(String NIGAOCHUSHI) {
        this.NIGAOCHUSHI = NIGAOCHUSHI;
    }

    public String getURGENTDEGREE() {
        return URGENTDEGREE;
    }

    public void setURGENTDEGREE(String URGENTDEGREE) {
        this.URGENTDEGREE = URGENTDEGREE;
    }
}
