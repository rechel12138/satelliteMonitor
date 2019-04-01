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
@TableName("htdxjk_bzbjl")
public class Bzbjl implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bzbjlAtpid;

    private String bzbjlAtpcreatedatetime;

    private String bzbjlAtpcreateuser;

    private String bzbjlAtplastmodifydatetime;

    private String bzbjlAtplastmodifyuser;

    private String bzbjlAtpstatus;

    private Integer bzbjlAtpsort;

    private String bzbjlAtpdotype;

    private String bzbjlAtpremark;

    /**
     * 外键角色主键
     */
    private String bzbjlInfoid;

    private String bzbjlPerson1;

    private String bzbjlPerson2;

    private String bzbjlAddperson;

    private String bzbjlAddtime;

    private String bzbjlWeek;

    private String bzbjlDutydate;

    private String bzbjlInfomodifytime;


}
