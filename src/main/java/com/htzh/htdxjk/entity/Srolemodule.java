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
 * @since 2019-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_srolemodule")
public class Srolemodule implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String srmAtpid;

    private String srmAtpcreatedatetime;

    private String srmAtpcreateuser;

    private String srmAtplastmodifydatetime;

    private String srmAtplastmodifyuser;

    private String srmAtpstatus;

    private Integer srmAtpsort;

    private String srmAtpdotype;

    private String srmAtpremark;

    /**
     * 外键角色主键
     */
    private String srmFksroleid;

    /**
     * 外键模块主键
     */
    private String srmFksmoduleid;

    private String srmSmaction;


}
