package com.yang.cloud.wms_all.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms_all.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户(User)实体类
 *
 * @author makejava
 * @since 2024-01-23 10:53:50
 */
@Accessors(chain = true)
@Data
@TableName("USER")
public class User extends BaseEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 用户锁
     */
    private Boolean isLocked;
    /**
     * 姓名
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 性别;0：female 1:female -1：unknown
     */
    private Integer gender;
    /**
     * 生日
     */
    private Date birthday;
}

