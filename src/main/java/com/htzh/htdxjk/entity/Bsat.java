package com.htzh.htdxjk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_bsat")
public class Bsat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String bsatAtpid;

    private String bsatAtpcreatedatetime;

    private String bsatAtpcreateuser;

    private String bsatAtplastmodifydatetime;

    private String bsatAtplastmodifyuser;

    private String bsatAtpstatus;

    private Integer bsatAtpsort;

    private String bsatAtpdotype;

    private String bsatAtpremark;

    /**
     * 外键角色主键
     */
    private String bsatId;

    /**
     * 外键模块主键
     */
    private String bsatLastModifedTime;

    private String bsatActive;

    private String bsatCode;

    private String bsatEndoflifeTime;

    private String bsatFullName;

    private String bsatLaunchTime;

    private String bsatLifeyear;

    private String bsatName;

    private String bsatAdminSubsId;

    private String bsatAdminId;

    private String bsatPlatformId;

    private String bsatRemark;

    private String bsatOutsideName;

    private String bsatMinipic;

    //显示软件ID
    private String bsatBxxrjlId;


    private String bsatFtpaccount;

    private String bsatFtppasswod;

    private String bsatFtpport;

    private String bsatFtppath;

    private String bsatLocalDirc;

    private String bsatAppName;

    private String bsatShownName;

    private String bsatSourcDirc;

    private String bsatAppParam;



}
