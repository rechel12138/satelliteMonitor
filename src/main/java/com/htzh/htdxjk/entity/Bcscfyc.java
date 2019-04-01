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
@TableName("htdxjk_bcscfyc")
public class Bcscfyc implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bcscfycAtpid;

    private String bcscfycAtpcreatedatetime;

    private String bcscfycAtpcreateuser;

    private String bcscfycAtplastmodifydatetime;

    private String bcscfycAtplastmodifyuser;

    private String bcscfycAtpstatus;

    private Integer bcscfycAtpsort;

    private String bcscfycAtpdotype;

    private String bcscfycAtpremark;

    /**
     * 外键
     */
    private String bcscfycSatid;


    private String bcscfycCode;

    private String bcscfycSatellitecode;

    private String bcscfycExceptionname;

    private String bcscfycOccurredtime;

    private String bcscfycAppearance;

    private String bcscfycResult;

    private String bcscfycOwner;

    private String bcscfycStatus;


}
