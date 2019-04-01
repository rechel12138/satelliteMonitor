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
@TableName("htdxjk_slog")
public class Slog implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String slAtpid;

    private String slAtpcreatedatetime;

    private String slAtpcreateuser;

    private String slAtplastmodifydatetime;

    private String slAtplastmodifyuser;

    private String slAtpstatus;

    private Integer slAtpsort;

    private String slAtpdotype;

    private String slAtpremark;

    /**
     * 分类
     */
    private String slType;

    /**
     * 时间
     */
    private String slLogtime;

    /**
     * 内容
     */
    private String slContent;

    /**
     * 外键账户主键
     */
    private String slFksaccountid;

    private String slIp;


}
