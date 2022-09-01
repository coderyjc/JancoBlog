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

}