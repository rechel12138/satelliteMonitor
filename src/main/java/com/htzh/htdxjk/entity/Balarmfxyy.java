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
@TableName("htdxjk_balarmfxyy")
public class Balarmfxyy implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String balarmfxyyAtpid;

    private String balarmfxyyAtpcreatedatetime;

    private String balarmfxyyAtpcreateuser;

    private String balarmfxyyAtplastmodifydatetime;

    private String balarmfxyyAtplastmodifyuser;

    private String balarmfxyyAtpstatus;

    private Integer balarmfxyyAtpsort;

    private String balarmfxyyAtpdotype;

    private String balarmfxyyAtpremark;

    /**
     * 外键角色主键
     */
    private String balarmfxyyAlarmid;

    /**
     * 外键模块主键
     */
    private String balarmfxyySatid;

    private String balarmfxyyBegintime;

    private String balarmfxyyEndtime;

    private String balarmfxyyAlarmlevel;

    private String balarmfxyyAlarmmsg;

    private String balarmfxyyResponetime;

    private String balarmfxyyUsername;

    private String balarmfxyyResponse;

    private String balarmfxyyAlarmvalue;

    private String balarmfxyyType;

    private String balarmfxyyRemark;

    private String balarmfxyySavetime;

    private String balarmfxyySaveuser;

    private String balarmfxyyPlan;

    private String balarmfxyyCreatetime;


}
