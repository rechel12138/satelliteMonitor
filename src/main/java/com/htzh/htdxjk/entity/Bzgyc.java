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
@TableName("htdxjk_bzgyc")
public class Bzgyc implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String bzgycAtpid;

    private String bzgycAtpcreatedatetime;

    private String bzgycAtpcreateuser;

    private String bzgycAtplastmodifydatetime;

    private String bzgycAtplastmodifyuser;

    private String bzgycAtpstatus;

    private Integer bzgycAtpsort;

    private String bzgycAtpdotype;

    private String bzgycAtpremark;

    private String bzgycDataId;

    private String bzgycTamplateId;

     //外键 与bsatId关联
    private String bzgycSatId;

    private String bzgycIsBigEvent;

    private String bzgycC512;

    private String bzgycXmlSataId;

    private String bzgycC8081;

    private String bzgycC8083;

    private String bzgycC8787;

    private String bzgycC8788;

    private String bzgycC8812;

    private String bzgycC8813;

    private String bzgycC8817;

    private String bzgycC8818;

    private String bzgycC8820;

    private String bzgycC8871;

    private String bzgycIsDelted;

    private String bzgycC9012;

    private String bzgycC9060;

    private String bzgycC9064;

    private String bzgycExceptionState;

    private String bzgycFirstDataId;

    private String bzgycDepariment;

    private String bzgycSubmitTime;

    private String bzgycC9079;

    private String bzgycC9117;

    private String bzgycSubmitState;

    private String bzgycExceptionReport;

    private String bzgycC9594;

    private String bzgycC9997;

    private String bzgycC9998;

    private String bzgycC9999;

    private String bzgycC10000;

    private String bzgycImportTime;

    private String bzgycC520;

    private String bzgycC521;

    private String bzgycC522;

    private String bzgycC9739;

    private String bzgycDeviceCode;

    private String bzgycEmailSended;

    private String bzgycIsPlanned;

    private String bzgycC9011;

    private String bzgycC9595;

    private String bzgycC9996;

    private String bzgycC8819;


}
