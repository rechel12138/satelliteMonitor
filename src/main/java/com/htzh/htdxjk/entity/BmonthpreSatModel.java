package com.htzh.htdxjk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: Rechel
 * @Date: 2019/3/2 下午3:02
 */

@Data
public class BmonthpreSatModel {


    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bmonthpreAtpid;

    private String bmonthpreAtpcreatedatetime;

    private String bmonthpreAtpcreateuser;

    private String bmonthpreAtplastmodifydatetime;

    private String bmonthpreAtplastmodifyuser;

    private String bmonthpreAtpstatus;

    private Integer bmonthpreAtpsort;

    private String bmonthpreAtpdotype;

    private String bmonthpreAtpremark;

    /**
     * 外键角色主键
     */
    private String bmonthpreId;

    /**
     * 外键模块主键
     */
    private String bmonthpreBtime;

    private String bmonthpreEtime;

    private String bmonthpreSatellite;

    private String bmonthpreMstype;

    private String bmonthpreSpan;

    @TableField("bsat_code")
    private String bsatCode;

}
