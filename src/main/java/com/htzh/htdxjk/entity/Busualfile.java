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
@TableName("htdxjk_busualfile")
public class Busualfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bufAtpid;

    private String bufAtpcreatedatetime;

    private String bufAtpcreateuser;

    private String bufAtplastmodifydatetime;

    private String bufAtplastmodifyuser;

    private String bufAtpstatus;

    private Integer bufAtpsort;

    private String bufAtpdotype;

    private String bufAtpremark;

    /**
     * 外键角色主键
     */
    private String bufName;

    /**
     * 外键模块主键
     */
    private String bufFilepostfix;

    private String bufFilelocation;

    private String bufUpdatetime;


}
