package com.Jancoyan.Domain;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;


/**
 * @author Jancoyan
 */
@Component
public class User {
    /**
     *`user_id` varchar(10)
     * NOT NULL
     * '用户id，系统随机分配',
     */
    private String userId;
    /**
     * `user_name` varchar(50)
     * NOT NULL COMMENT
     * '用户自己起的名字，不能重复，用来登录',
     */
    private String userName;
    /**
     * `user_pwd` varchar(20)
     * NOT NULL
     * '登录用的密码'
     */
    private String userPwd;
    /**
     *`user_sex` int(1)
     * NULL DEFAULT NULL
     * '性别'
     */
    private int userSex;
    /**
     * `user_create_date` date
     * NULL DEFAULT NULL
     * '账号创建日期',
     */
    private Date createDate;
    /**
     * `user_email` varchar(30)
     * NULL DEFAULT NULL
     * '邮箱',
     */
    private String userEmail;
    /**
     * `user_birthday` date
     * NULL DEFAULT NULL
     * '出生日期',
     */
    private Date userBirthday;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
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

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

}
