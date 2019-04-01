package com.htzh.htdxjk.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
@Data
public class UserListModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String suAtpid;

    private String suAtpcreatedatetime;

    private String suAtpcreateuser;

    private String suAtplastmodifydatetime;

    private String suAtplastmodifyuser;

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

    private String suTelephonenum;

    private String suWorkphonenum;

    private String suUserjobroleid;

    private String suMangeplatformgroupsates;

    @TableField("sr_name")
    private String srName;


}
