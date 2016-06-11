package config;



import java.util.List;

import bean.Gw;
import bean.GwAttachs;
import bean.GwForm;
import bean.GwSignInfo;
import retrofit2.http.Url;

/**
 * Created by wangjingyuan on 16/6/3.
 */
public class Result {
    public String ret;
    public String username;
    public String roleIds;
    public List<Gw> gwList;
    public GwForm gwForm;
    public List<GwSignInfo> gwSignInfo;
    public List<GwAttachs> gwAttachs;
    public Url url;
    public String fileId;

//
}
