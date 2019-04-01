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
@TableName("htdxjk_bfile")
public class Bfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bfileAtpid;

    private String bfileAtpcreatedatetime;

    private String bfileAtpcreateuser;

    private String bfileAtplastmodifydatetime;

    private String bfileAtplastmodifyuser;

    private String bfileAtpstatus;

    private Integer bfileAtpsort;

    private String bfileAtpdotype;

    private String bfileAtpremark;

    /**
     * 外键角色主键
     */
    private String bfileAppinfoId;

    /**
     * 外键模块主键
     */
    private String bfileLocalDirc;

    private String bfileAppname;

    private String bfileShownName;

    private String bfileSourcDirc;


}
