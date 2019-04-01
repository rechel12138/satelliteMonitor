package com.htzh.htdxjk.entity;

/**
 * @Author: Rechel
 * @Date: 2019/2/26 下午2:35
 */

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
@Data
public class SonMenuListModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String smAtpid;

    private String smAtpcreatedatetime;

    private String smAtpcreateuser;

    private String smAtplastmodifydatetime;

    private String smAtplastmodifyuser;

    private String smAtpstatus;

    private Integer smAtpsort;

    private String smAtpdotype;

    private String smAtpremark;

    @TableField("sm_action")
    private String smAction;

    private String smIscurrent;


    /**
     * 名称
     */
    private String smName;

    /**
     * 备注
     */
    private String smRemark;

    private String smWebpath;

    private String smFksmoduleid;

    private String smIcon;

    private String smIsdisplay;

    private int smIslogineddisplay;


}
