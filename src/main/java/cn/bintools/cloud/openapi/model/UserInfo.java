package cn.bintools.cloud.openapi.model;

import java.io.Serializable;

/***
 * TODO
 * @author <a href="jian.huang@bintools.cn">Turbo</a>
 * @version 1.0.0 2021-06-2021/6/24-21:46
 */
public class UserInfo implements Serializable {

    private String dept;

    private String email;

    private String telephone;

    private String jobNumber;

    private String userGender;

    private String userId;

    private String userName;

    private String password;

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
