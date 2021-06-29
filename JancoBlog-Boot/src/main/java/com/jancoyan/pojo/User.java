package com.jancoyan.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tbl_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id，自动分配
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String userNickname;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 性别
     */
    private Integer userSex;

    /**
     * 角色，管理员、游客啥的
     */
    private Integer userRole;

    /**
     * 用户创建时间
     */
    private Date userCreateDate;

    /**
     * 上一次登录的时间
     */
    private Date userLastLoginDate;

    /**
     * 用户头像的路径
     */
    private String userProfile;

    /**
     * 注册的ip地址
     */
    private String userIp;

}
