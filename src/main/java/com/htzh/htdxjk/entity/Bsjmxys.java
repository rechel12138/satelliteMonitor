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
 * @since 2019-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_bsjmxys")
public class Bsjmxys implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bsjmxysAtpid;

    private String bsjmxysAtpcreatedatetime;

    private String bsjmxysAtpcreateuser;

    private String bsjmxysAtplastmodifydatetime;

    private String bsjmxysAtplastmodifyuser;

    private String bsjmxysAtpstatus;

    private Integer bsjmxysAtpsort;

    private String bsjmxysAtpdotype;

    private String bsjmxysAtpremark;

    private String bsjmxysSatid;

    private String bsjmxysBegintime;

    private String bsjmxysEndtime;

    private String bsjmxysAlarmlevel;

    private String bsjmxysAlarmmsg;

    private String bsjmxysResponetime;

    private String bsjmxysUsername;

    private String bsjmxysResponse;

    private String bsjmxysAlarmvalue;

    private String bsjmxysCreatetime;

    private String bsjmxysUpdatetime;

    private String bsjmxysPreJudgeTime;

    private String bsjmxysPreJudgeType;

    private String bsjmxysPreJudgeRemark;


}
