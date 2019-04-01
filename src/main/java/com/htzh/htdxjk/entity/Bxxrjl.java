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
@TableName("htdxjk_bxxrjl")
public class Bxxrjl implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bxxrjlAtpid;

    private String bxxrjlAtpcreatedatetime;

    private String bxxrjlAtpcreateuser;

    private String bxxrjlAtplastmodifydatetime;

    private String bxxrjlAtplastmodifyuser;

    private String bxxrjlAtpstatus;

    private Integer bxxrjlAtpsort;

    private String bxxrjlAtpdotype;

    private String bxxrjlAtpremark;

    /**
     * 外键角色主键
     */
    private String bxxrjlAppinfoId;

    /**
     * 外键模块主键
     */
    private String bxxrjlLocalDirc;

    private String bxxrjlAppName;

    private String bxxrjlShownName;

    private String bxxrjlSourcDirc;

    private String bxxrjlAppOaran;

    private String bxxrjlAppGroup;

    private String bxxrjlAppName2;

    private String bxxrjlAppParam2;

    private String bxxrjlMinipic;


}
