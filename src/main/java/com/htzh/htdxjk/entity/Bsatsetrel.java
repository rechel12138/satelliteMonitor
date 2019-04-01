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
@TableName("htdxjk_bsatsetrel")
public class Bsatsetrel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bssrAtpid;

    private String bssrAtpcreatedatetime;

    private String bssrAtpcreateuser;

    private String bssrAtplastmodifydatetime;

    private String bssrAtplastmodifyuser;

    private String bssrAtpstatus;

    private Integer bssrAtpsort;

    private String bssrAtpdotype;

    private String bssrAtpremark;

    /**
     * 外键角色主键
     */
    private String bssrSatid;

    /**
     * 外键模块主键
     */
    private String bssrBssid;


}
