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
@TableName("htdxjk_bzbsw")
public class Bzbsw implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bzbswAtpid;

    private String bzbswAtpcreatedatetime;

    private String bzbswAtpcreateuser;

    private String bzbswAtplastmodifydatetime;

    private String bzbswAtplastmodifyuser;

    private String bzbswAtpstatus;

    private Integer bzbswAtpsort;

    private String bzbswAtpdotype;

    private String bzbswAtpremark;

    /**
     * 外键 与bsatId关联
     */
    private String bzbswSatid;

    /**
     * 外键 与值班列表ID关联
     */
    private String bzbswInfoid;

    private String bzbswSatcode;

    private String bzbswInfotype;

    private String bzbswPhemdesc;

    private String bzbswResltdesc;

    private String bzbswAddtime;

    private String bzbswAddperson;

    private String bzbswModifytime;

    private String bzbswState;

    private String bzbswSendto;

    private String bzbswSerialnum;

    private String bzbswShownotifyflag;

    private String bzbswPhemtime;

    private String bzbswReslttime;

    private String bzbswCreatebykettle;

    private String bzbswIdinnorbit;

    private String bzbswLevel;

    @TableField(exist = false)
    private String isTrace = "否";


}
