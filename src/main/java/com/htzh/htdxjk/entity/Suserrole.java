package com.htzh.htdxjk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.Api;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_suserrole")
public class Suserrole implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String surAtpid;

    private String surAtpcreatedatetime;

    private String surAtpcreateuser;

    private String surAtplastmodifydatetime;

    private String surAtplastmodifyuser;

    private String surAtpstatus;

    private Integer surAtpsort;

    private String surAtpdotype;

    private String surAtpremark;

    /**
     * 外键账户主键
     */
    private String surFksuserid;

    /**
     * 外键角色主键
     */
    private String surFksroleid;

    /*角色名称*/
    @TableField(exist = false)
    private String srName;
}
