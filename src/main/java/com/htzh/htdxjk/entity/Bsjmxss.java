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
@TableName("htdxjk_bsjmxss")
public class Bsjmxss implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bsjmxssAtpid;

    private String bsjmxssAtpcreatedatetime;

    private String bsjmxssAtpcreateuser;

    private String bsjmxssAtplastmodifydatetime;

    private String bsjmxssAtplastmodifyuser;

    private String bsjmxssAtpstatus;

    private Integer bsjmxssAtpsort;

    private String bsjmxssAtpdotype;

    private String bsjmxssAtpremark;

    /**
     * 外键角色主键
     */
    private String bsjmxssSatid;

    /**
     * 外键模块主键
     */
    private String bsjmxssBegintime;

    private String bsjmxssEndtime;

    private String bsjmxssAlarmlevel;

    private String bsjmxssAlarmmsg;

    private String bsjmxssResponetime;

    private String bsjmxssUsername;

    private String bsjmxssResponse;

    private String bsjmxssAlarmvalue;

    private String bsjmxssTmid;

    private String bsjmxssTmcode;

    private String bsjmxssPreJudgeTime;

    private String bsjmxssPreJudgeType;

    private String bsjmxssPreJudgeRemark;


}
