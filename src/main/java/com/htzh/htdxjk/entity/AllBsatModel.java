package com.htzh.htdxjk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: Rechel
 * @Date: 2019/3/4 下午4:19
 */
@Data
public class AllBsatModel {
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

    private String bsatBxhzcdwId;

    private String bsatDomain;

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

    @TableField("tdrdh")
    private String tdrdh;

    @TableField("zrrname")
    private String zrrname;

    @TableField("zrrdh")
    private String zrrdh;

    @TableField("tdrname")
    private String tdrname;

    @TableField("sjsm")
    private String sjsm;

    @TableField("fssj")
    private String fssj;

    @TableField("valuestatus")
    private  String valuestatus;


}
