package com.jancoyan.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Jancoyan
 * @since 2021-06-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tbl_type")
public class Type extends Model<Type> {

    private static final long serialVersionUID = 1L;

    /**
     * 类型名称	
     */
    @TableId(value = "type_id")
    private Integer typeId;

    /**
     * 上级类型id
     */
    private Integer preTypeId;

    /**
     * 上级类型名称
     */
    @TableField(exist = false)
    private String preTypeName;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型描述
     */
    private String typeDescription;

}
