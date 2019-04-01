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
@TableName("htdxjk_bsattop")
public class BsatTop implements Serializable {


    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bstAtpid;

    private String bstAtpcreatedatetime;

    private String bstAtpcreateuser;

    private String bstAtplastmodifydatetime;

    private String bstAtplastmodifyuser;

    private String bstAtpstatus;

    private Integer bstAtpsort;

    private String bstAtpdotype;

    private String bstAtpremark;

    /**
     * 外键角色主键
     */
    private String bstFksuserid;

    /**
     * 外键模块主键
     */
    private String bstFkbsatid;

    private String bstAdmin;

    private int bstSeq;


}
