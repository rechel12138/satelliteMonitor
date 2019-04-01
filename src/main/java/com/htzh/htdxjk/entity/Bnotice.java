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
 * @since 2019-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_bnotice")
public class Bnotice implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bnteAtpid;

    private String bnteAtpcreatedatetime;

    private String bnteAtpcreateuser;

    private String bnteAtplastmodifydatetime;

    private String bnteAtplastmodifyuser;

    private String bnteAtpstatus;

    private Integer bnteAtpsort;

    private String bnteAtpdotype;

    private String bnteAtpremark;

    /**
     * 外键角色主键
     */
    private String bnteType;

    /**
     * 外键模块主键
     */
    private String bnteDesc;

    private String bnteTimelong;


}
