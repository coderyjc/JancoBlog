package com.Jancoyan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;

    private String userNickname;

    private String userName;

    private String userPwd;

    private String userEmail;

    private Integer userSex;

    private Integer userRole;

    private Date userCreateDate;

    private String userProfile;

    private Date userBirthday;

    private String userIp;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userNickname='" + userNickname + '\'' +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userSex=" + userSex +
                ", userRole=" + userRole +
                ", userCreateDate=" + userCreateDate +
                ", userProfile='" + userProfile + '\'' +
                ", userBirthday=" + userBirthday +
                ", userIp='" + userIp + '\'' +
                '}';
    }
}