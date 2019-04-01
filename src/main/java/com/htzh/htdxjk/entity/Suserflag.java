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
 * @since 2019-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_suserflag")
public class Suserflag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String sufAtpid;

    private String sufAtpcreatedatetime;

    private String sufAtpcreateuser;

    private String sufAtplastmodifydatetime;

    private String sufAtplastmodifyuser;

    private String sufAtpstatus;

    private Integer sufAtpsort;

    private String sufAtpdotype;

    private String sufAtpremark;

    /**
     * 外键，用户atpid
     */
    private String sufFksuserid;

    /**
     * 来源
     */
    private String sufFrom;

    /**
     * 外键，报警id
     */
    private String sufFkbjid;


}
