package bean;

/**
 * Created by wangxiao on 16/6/3.
 */
public class User {
    private String ret;
    private String username;
    private String roleIds;

    public String getRet(){
        return ret;
    }

    public void SetRet(String ret){
        this.ret = ret;
    }

    public String getUsername(){
        return username;
    }

    public void SetUsername(String username){
        this.username = username;
    }
    public String getRoleIds(){
        return roleIds;
    }

    public void SetRoleIds(String roleIds){
        this.roleIds = roleIds;
    }
}
