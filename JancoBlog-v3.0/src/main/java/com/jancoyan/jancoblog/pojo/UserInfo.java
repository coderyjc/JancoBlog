/**
 * @Author: Yan Jingcun
 * @Date: 2021/10/3
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <p>
 *
 * </p>
 *
 * @author Jancoyan
 * @since 2021-10-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tbl_user_info")
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private long userId;

    private String userEmail;

    private long userSex;

    private String userSignature;

    private String userRegion;

    private Date userBirthdate;

    private String userTelephone;

    private String userRealName;

    private String userSchool;

    private String userMajor;

    private Date userEnterSchoolDate;

    private String userAcademicDegree;

    private String userCompany;

    private String userPosition;

    private String userField;

}
