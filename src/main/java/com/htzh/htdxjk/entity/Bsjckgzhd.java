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
@TableName("htdxjk_bsjckgzhd")
public class Bsjckgzhd implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bsjckgzhdAtpid;

    private String bsjckgzhdAtpcreatedatetime;

    private String bsjckgzhdAtpcreateuser;

    private String bsjckgzhdAtplastmodifydatetime;

    private String bsjckgzhdAtplastmodifyuser;

    private String bsjckgzhdAtpstatus;

    private Integer bsjckgzhdAtpsort;

    private String bsjckgzhdAtpdotype;

    private String bsjckgzhdAtpremark;

    /**
     * 外键
     */
    private String bsjckgzhdSatid;


    private String bsjckgzhdSatname;

    private String bsjckgzhdStartTime;

    private String bsjckgzhdEndTime;

    private String bsjckgzhdDevname;


}
