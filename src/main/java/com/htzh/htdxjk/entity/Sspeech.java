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
 * @since 2019-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_sspeech")
public class Sspeech implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String sspcAtpid;

    private String sspcAtpcreatedatetime;

    private String sspcAtpcreateuser;

    private String sspcAtplastmodifydatetime;

    private String sspcAtplastmodifyuser;

    private String sspcAtpstatus;

    private Integer sspcAtpsort;

    private String sspcAtpdotype;

    private String sspcAtpremark;

    private String sspcDt;

    private String sspcSpeechdt;

    private String sspcType;

    private String sspcStatus;

    private String sspcContent;

    private String sspcSpeechcontent;

    private Integer sspcCount;


}
