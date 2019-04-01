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
@TableName("htdxjk_bpt")
public class Bpt implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bptAtpid;

    private String bptAtpcreatedatetime;

    private String bptAtpcreateuser;

    private String bptAtplastmodifydatetime;

    private String bptAtplastmodifyuser;

    private String bptAtpstatus;

    private Integer bptAtpsort;

    private String bptAtpdotype;

    private String bptAtpremark;

    /**
     * 外键角色主键
     */
    private String bptId;

    /**
     * 外键模块主键
     */
    private String bptLastModifedTime;

    private String bptName;

    private String bptRemark;


}
