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
@TableName("htdxjk_bdwgl")
public class Bdwgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bdwglAtpid;

    private String bdwglAtpcreatedatetime;

    private String bdwglAtpcreateuser;

    private String bdwglAtplastmodifydatetime;

    private String bdwglAtplastmodifyuser;

    private String bdwglAtpstatus;

    private Integer bdwglAtpsort;

    private String bdwglAtpdotype;

    private String bdwglAtpremark;

    /**
     * 外键角色主键
     */
    private String bdwglXh;

    /**
     * 外键模块主键
     */
    private String bdwglSatname;

    private String bdwglZrr;

    private String bdwglDtr;

    private String bdwglCkdw;

    private String bdwglZbdh;

    private String bdwglCzdh;

    private String bdwglHtzcdh;

    private String bdwglYhdw;

    private String bdwglYhlxr;

    private String bdwglLxfs;

    private String bdwglDgpfax;

    private String bdwglDgphone;

    private String bdwglGgfax;

    private String bdwglGgphone;


}
