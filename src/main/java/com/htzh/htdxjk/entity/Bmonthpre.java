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
@TableName("htdxjk_bmonthpre")
public class Bmonthpre implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bmonthpreAtpid;

    private String bmonthpreAtpcreatedatetime;

    private String bmonthpreAtpcreateuser;

    private String bmonthpreAtplastmodifydatetime;

    private String bmonthpreAtplastmodifyuser;

    private String bmonthpreAtpstatus;

    private Integer bmonthpreAtpsort;

    private String bmonthpreAtpdotype;

    private String bmonthpreAtpremark;

    //型号ID关联
    private String bmonthpreSatid;

    private String bmonthpreBtime;

    private String bmonthpreEtime;

    private String bmonthpreSatellite;

    private String bmonthpreMstype;

    private String bmonthpreSpan;


}
