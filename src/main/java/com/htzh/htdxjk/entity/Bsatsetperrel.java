package com.htzh.htdxjk.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_bsatsetperrel")
public class Bsatsetperrel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bsdmAtpid;

    private String bsprAtpcreatedatetime;

    private String bsprAtpcreateuser;

    private String bsprAtplastmodifydatetime;

    private String bsprAtplastmodifyuser;

    private String bsprAtpstatus;

    private Integer bsprAtpsort;

    private String bsprAtpdotype;

    private String bsprAtpremark;

    /**
     * 外键角色主键
     */
    private String bsprBssid;

    /**
     * 外键模块主键
     */
    private String bsprUserid;


}
