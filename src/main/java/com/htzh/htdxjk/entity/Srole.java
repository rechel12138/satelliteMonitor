package com.htzh.htdxjk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_srole")
public class Srole implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String srAtpid;

    private String srAtpcreatedatetime;

    private String srAtpcreateuser;

    private String srAtplastmodifydatetime;

    private String srAtplastmodifyuser;

    private String srAtpstatus;

    private Integer srAtpsort;

    private String srAtpdotype;

    private String srAtpremark;

    /**
     * 账户编号系统自动生成
     */
    private String srName;

    private String srRemark;

    private String srIndexurl;

    @TableField(exist = false)
    private List<Smodule> permissions=new ArrayList<>();


}
