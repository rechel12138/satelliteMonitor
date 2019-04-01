package com.htzh.htdxjk.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@TableName("htdxjk_suser")
public class Suser implements Serializable {


    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String suAtpid;

    private String suAtpcreatedatetime;

    private String suAtpcreateuser;

    private String suAtplastmodifydatetime;

    private String suAtplastmodifyuser;

    /**
     * 0-启用  1-禁用
     */
    private String suAtpstatus;

    private Integer suAtpsort;

    private String suAtpdotype;

    private String suAtpremark;

    /**
     * 账户编号系统自动生成
     */
    private String suBizcode;

    /**
     * 员工编号
     */
    private String suEmpcode;

    private String suChinesename;

    private String suEnglishname;

    private String suNickname;

    /**
     * 性别（男、女、未知）
     */
    private String suGender;

    private String suRemark;

    private String suAccount;

    private String suPassword;

    private String suLastlogindatetime;

    private String suId;

    private String suLastModifedTime;

    private String suOriginid;

    private String suFullname;

    private String suIsactive;

    private String suIsadmin;

    private String suMangePlatformgroupSates;

    private String suTelePhoneNum;

    private String suUserJobroleId;

    private String suUsername;

    private String suWorkPhoneNum;

    @JSONField(serialize = false)
    @TableField(exist = false)
    private List<Srole> roles = new ArrayList<>();


}
