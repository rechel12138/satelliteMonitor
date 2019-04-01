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
 * @since 2019-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_borbit")
public class Borbit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String borbitAtpid;

    private String borbitAtpcreatedatetime;

    private String borbitAtpcreateuser;

    private String borbitAtplastmodifydatetime;

    private String borbitAtplastmodifyuser;

    private String borbitAtpstatus;

    private Integer borbitAtpsort;

    private String borbitAtpdotype;

    private String borbitAtpremark;
    /**
     * 外键角色主键
     */

    private String borbitSatid;

    private String borbitTimeStamp;

    private String borbitSatname;

    private String borbitA;

    private String borbitE;

    private String borbitI;

    private String borbitO;

    private String borbitW;

    private String borbitM;

    private String borbitPDot1;

    private String borbitPDot2;

    private String borbitP;

    private String borbitHp;

    private String borbitHa;

    private String borbitType;


}
