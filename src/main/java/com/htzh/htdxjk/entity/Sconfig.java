package com.htzh.htdxjk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.EAN;

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
@TableName("htdxjk_sconfig")
public class Sconfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String scAtpid;

    private String scAtpcreatedatetime;

    private String scAtpcreateuser;

    private String scAtplastmodifydatetime;

    private String scAtplastmodifyuser;

    private String scAtpstatus;

    private Integer scAtpsort;

    private String scAtpdotype;

    private String scAtpremark;

    /**
     * 分类
     */
    private String scType;

    /**
     * 模式(字典状态码 字符串、图片、等)
     */
    private String scMode;

    private String scKey;

    private String scValue;


}
