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
@TableName("htdxjk_bxhzcdw")
public class Bxhzcdw implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bxhzcdwAtpid;

    private String bxhzcdwAtpcreatedatetime;

    private String bxhzcdwAtpcreateuser;

    private String bxhzcdwAtplastmodifydatetime;

    private String bxhzcdwAtplastmodifyuser;

    private String bxhzcdwAtpstatus;

    private Integer bxhzcdwAtpsort;

    private String bxhzcdwAtpdotype;

    private String bxhzcdwAtpremark;

    /**
     * 外键角色主键
     */
    private String bxhzcdwName;

    /**
     * 外键模块主键
     */
    private String bxhzcdwUnit;

    private String bxhzcdwPhone;

    private String bxhzcdwTelephone;

    private String bxhzcdwRemark;

    private String bxhzcdwPost;

    private String bxhzcdwSeq;

    private String bxhzcdwId;


}
