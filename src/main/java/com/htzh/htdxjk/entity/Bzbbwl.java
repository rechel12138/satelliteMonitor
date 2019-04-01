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
@TableName("htdxjk_bzbbwl")
public class Bzbbwl implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bzbbwlAtpid;

    private String bzbbwlAtpcreatedatetime;

    private String bzbbwlAtpcreateuser;

    private String bzbbwlAtplastmodifydatetime;

    private String bzbbwlAtplastmodifyuser;

    private String bzbbwlAtpstatus;

    private Integer bzbbwlAtpsort;

    private String bzbbwlAtpdotype;

    private String bzbbwlAtpremark;

    /**
     * 外键 与bsatId关联
     */
    private String bzbbwlSatid;


    private String bzbbwlShowdate;

    private String bzbbwlShowtitle;

    private String bzbbwlShowdetail;

    private String bzbbwlAddtime;

    private String bzbbwlAddperson;

    private String bzbbwlSatcode;

    private String bzbbwlSerialnum;

    private String bzbbwlShowdays;

    private String bzbbwlCreatebykettle;

    private String bzbbwlIdinnorbit;

    private String bzbbwlState;

    private String bzbbwlConfirm;


}
