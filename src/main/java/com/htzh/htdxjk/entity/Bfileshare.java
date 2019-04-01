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
 * @since 2019-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_bfileshare")
public class Bfileshare implements Serializable {


    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bfsAtpid;

    private String bfsAtpcreatedatetime;

    private String bfsAtpcreateuser;

    private String bfsAtplastmodifydatetime;

    private String bfsAtplastmodifyuser;

    private String bfsAtpstatus;

    private Integer bfsAtpsort;

    private String bfsAtpdotype;

    private String bfsAtpremark;

    /**
     * 外键角色主键
     */
    private String bfsName;

    /**
     * 外键模块主键
     */
    private String bfsFilepostfix;

    private String bfsFilelocation;

    private String bfsUpdatetime;


}
