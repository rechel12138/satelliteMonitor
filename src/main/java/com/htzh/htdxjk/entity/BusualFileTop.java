package com.htzh.htdxjk.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * <p>
 *
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-09
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_busualfiletop")
public class BusualFileTop implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String buftAtpid;

    private String buftAtpcreatedatetime;

    private String buftAtpcreateuser;

    private String buftAtplastmodifydatetime;

    private String buftAtplastmodifyuser;

    private String buftAtpstatus;

    private Integer buftAtpsort;

    private String buftAtpdotype;

    private String buftAtpremark;

    /**
     * 外键角色主键
     */
    private String buftFksuserid;

    /**
     * 外键模块主键
     */
    private String buftFkbusualfileid;

    private String buftAdmin;

    private int buftSeq;


}
