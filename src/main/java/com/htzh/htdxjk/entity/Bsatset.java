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
 * @since 2019-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_bsatset")
public class Bsatset implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bssAtpid;

    private String bssAtpcreatedatetime;

    private String bssAtpcreateuser;

    private String bssatplastmodifydatetime;

    private String bssAtplastmodifyuser;

    private String bssAtpstatus;

    private Integer bssAtpsort;

    private String bssAtpdotype;

    private String bssAtpremark;


    private String bssName;


    private String bssType = "公有";

    private String bssDesc;

    private String bssCuser;

    private String bssCusertime;

    @TableField(exist = false)
    private String satids;

    @TableField(exist = false)
    private String userIds;


}
