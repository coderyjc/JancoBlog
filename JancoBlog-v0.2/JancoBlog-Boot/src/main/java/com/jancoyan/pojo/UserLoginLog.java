package com.jancoyan.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Jancoyan
 * @since 2021-07-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tbl_user_login_log")
public class UserLoginLog extends Model<UserLoginLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 登录时候的ip
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private Date loginTime;

}
