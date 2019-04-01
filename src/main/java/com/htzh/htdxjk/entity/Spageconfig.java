package com.htzh.htdxjk.entity;

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
 * @since 2019-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("htdxjk_spageconfig")
public class Spageconfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bpcAtpid;

    private String bpcAtpcreatedatetime;

    private String bpcAtpcreateuser;

    private String bpcAtplastmodifydatetime;

    private String bpcAtplastmodifyuser;

    private String bpcAtpstatus;

    private int bpcAtpsort;

    private String bpcAtpdotype;

    private String bpcAtpremark;

    private String bpcFksuserid;

    private String bpcUrl;

    private int bpcSort;

    private String bpcFlag;

    private String bpcName;

    private String bpcTitle;


}
