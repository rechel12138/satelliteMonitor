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
@TableName("htdxjk_bckplan")
public class Bckplan implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bckplanAtpid;

    private String bckplanAtpcreatedatetime;

    private String bckplanAtpcreateuser;

    private String bckplanAtplastmodifydatetime;

    private String bckplanAtplastmodifyuser;

    private String bckplanAtpstatus;

    private Integer bckplanAtpsort;

    private String bckplanAtpdotype;

    private String bckplanAtpremark;

    /**
     * 外键
     */
    private String bckplanSatid;

    private String bckplanSatname;

    private String bckplanMadetime;

    private String bckplanStartTime;

    private String bckplanEndTime;

    private String bckplanCnt;

    private String bckplanDevname;

    private String bckplanIsDelete;

    private String bckplanState;

    private String bckplanModifyTime;


}
