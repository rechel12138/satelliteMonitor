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
 * @since 2019-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_bsatdatamsg")
public class Bsatdatamsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String bsdmAtpid;

    private String bsdmAtpcreatedatetime;

    private String bsdmAtpcreateuser;

    private String bsdmAtplastmodifydatetime;

    private String bsdmAtplastmodifyuser;

    private String bsdmAtpstatus;

    private Integer bsdmAtpsort;

    private String bsdmAtpdotype;

    private String bsdmAtpremark;

    /**
     * 外键角色主键
     */
    private String bsdmFksuserid;

    /**
     * 外键模块主键
     */
    private String bsdmFilename;

    private String bsdmDownloadtime;

    private String bsdmStatus;


}
