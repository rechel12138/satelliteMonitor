package com.htzh.htdxjk.entity;

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
 * @since 2019-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Bxhzcdwrel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bxhzcdwrlAtpid;

    private String bxhzcdwrlAtpcreatedatetime;

    private String bxhzcdwrlAtpcreateuser;

    private String bxhzcdwrlAtplastmodifydatetime;

    private String bxhzcdwrlAtplastmodifyuser;

    private String bxhzcdwrlAtpstatus;

    private Integer bxhzcdwrlAtpsort;

    private String bxhzcdwrlAtpdotype;

    private String bxhzcdwrlAtpremark;

    /**
     * 外键角色主键
     */
    private String bxhzcdwrlFkbxhzcdwid;

    /**
     * 外键模块主键
     */
    private String bxhzcdwrlFkbsatid;


}
