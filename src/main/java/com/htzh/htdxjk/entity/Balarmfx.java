package com.htzh.htdxjk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("htdxjk_balarmfx")
public class Balarmfx implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String balarmfxAtpid;

    private String balarmfxAtpcreatedatetime;

    private String balarmfxAtpcreateuser;

    private String balarmfxAtplastmodifydatetime;

    private String balarmfxAtplastmodifyuser;

    private String balarmfxAtpstatus;

    private Integer balarmfxAtpsort;

    private String balarmfxAtpdotype;

    private String balarmfxAtpremark;

    /**
     * 外键角色主键
     */
    private String balarmfxAlarmid;

    /**
     * 外键模块主键
     */
    private String balarmfxSatid;

    private String balarmfxBegintime;

    private String balarmfxEndtime;

    private String balarmfxAlarmlevel;

    private String balarmfxAlarmmsg;

    private String balarmfxResponetime;

    private String balarmfxUsername;

    private String balarmfxResponse;

    private String balarmfxAlarmvalue;

    private String balarmfxType;

    private String balarmfxRemark;

    private String balarmfxSavetime;

    private String balarmfxSaveuser;

    private String balarmfxPlan;

    private String balarmfxSource;

    private String balarmfxIden;


}
