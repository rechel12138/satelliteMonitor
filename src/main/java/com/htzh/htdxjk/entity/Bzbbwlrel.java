package com.htzh.htdxjk.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_bzbbwlrel")
public class Bzbbwlrel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bzbbwlrelAtpid;

    private String bzbbwlrelAtpcreatedatetime;

    private String bzbbwlrelAtpcreateuser;

    private String bzbbwlrelAtplastmodifydatetime;

    private String bzbbwlrelAtplastmodifyuser;

    private String bzbbwlrelAtpstatus;

    private Integer bzbbwlrelAtpsort;

    private String bzbbwlrelAtpdotype;

    private String bzbbwlrelAtpremark;

    /**
     * 外键
     */
    private String bzbbwlrelUserId;

    /**
     * 外键
     */
    private String bzbbwlrelBwlId;

    private String bzbbwlrelBjlId;


}
