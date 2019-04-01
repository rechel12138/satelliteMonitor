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
 * @since 2019-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_sdict")
public class Sdict implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String sdAtpid;

    private String sdAtpcreatedatetime;

    private String sdAtpcreateuser;

    private String sdAtplastmodifydatetime;

    private String sdAtplastmodifyuser;

    private String sdAtpstatus;

    private Integer sdAtpsort;

    private String sdAtpdotype;

    private String sdAtpremark;

    /**
     * 字典类别 字典状态码(系统、自定义，（系统表示不能删除）)
     */
    private String sdType;

    /**
     * 引用值
     */
    private String sdIndexvalue;

    /**
     * 显示值
     */
    private String sdShowvalue;

    /**
     * 备注
     */
    private String sdRemark;

    private String sdDesc;


}
