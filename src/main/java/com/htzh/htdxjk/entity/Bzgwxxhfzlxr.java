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
@TableName("htdxjk_bzgwxxhfzlxr")
public class Bzgwxxhfzlxr implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bzgwxxhfzlxrAtpid;

    private String bzgwxxhfzlxrAtpcreatedatetime;

    private String bzgwxxhfzlxrAtpcreateuser;

    private String bzgwxxhfzlxrAtplastmodifydatetime;

    private String bzgwxxhfzlxrAtplastmodifyuser;

    private String bzgwxxhfzlxrAtpstatus;

    private Integer bzgwxxhfzlxrAtpsort;

    private String bzgwxxhfzlxrAtpdotype;

    private String bzgwxxhfzlxrAtpremark;

   //外键
    private String bzgwxxhfzlxrSatid;

    //外键
    private String bzgwxxhFkbxhzcdwid;



}
