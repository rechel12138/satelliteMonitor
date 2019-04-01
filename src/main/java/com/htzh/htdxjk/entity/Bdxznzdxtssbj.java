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
@TableName("htdxjk_bdxznzdxtssbj")
public class Bdxznzdxtssbj implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bdxznzdxtssbjAtpid;

    private String bdxznzdxtssbjAtpcreatedatetime;

    private String bdxznzdxtssbjAtpcreateuser;

    private String bdxznzdxtssbjAtplastmodifydatetime;

    private String bdxznzdxtssbjAtplastmodifyuser;

    private String bdxznzdxtssbjAtpstatus;

    private Integer bdxznzdxtssbjAtpsort;

    private String bdxznzdxtssbjAtpdotype;

    private String bdxznzdxtssbjAtpremark;

    /**
     * 外键角色主键
     */
    private String bdxznzdxtssbjId;

    /**
     * 外键模块主键
     */
    private String bdxznzdxtssbjSatId;

    private String bdxznzdxtssbjSatname;

    private String bdxznzdxtssbjSatcode;

    private String bdxznzdxtssbjRecordTime;

    private String bdxznzdxtssbjEventId;

    private String bdxznzdxtssbjTitle;

    private String bdxznzdxtssbjTmlist;

    private String bdxznzdxtssbjExplain;

    private String bdxznzdxtssbjSubsystem;

    private String bdxznzdxtssbjComponent;

    private String bdxznzdxtssbjIsolate;

    private String bdxznzdxtssbjBegintime;

    private String bdxznzdxtssbjEndtime;

    private String bdxznzdxtssbjAlarmLevel;

    private String bdxznzdxtssbjValLevel;

    private String bdxznzdxtssbjPreJudgeTime;

    private String bdxznzdxtssbjPreJudgeType;

    private String bdxznzdxtssbjPreJudgeRemark;

    private String bdxznzdxtssbjConfirmUser;


}
