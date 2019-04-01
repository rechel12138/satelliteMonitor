package com.htzh.htdxjk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName AlarmModel
 * @Description TOOD
 * @Author guoconglin
 * @DATE 2019/3/12 19:51
 * @Version 1.0
 **/


@Data
public class AlarmModel implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String atpid;

    //型号代号
    private String bsatCode;

    //报警开始时间
    private String alarmBeginTime;

    //报警结束时间
    private String alarmEndTime;

    //参数代码
    private String paramCode;

    //报警级别
    private String alarmLevel;

    //参数级别
    private String  paramLevel;

    //当前值
    private String currentVal;

    //报警值
    private String alarmVal;

    //报警信息
    private String alarmMsg;

    //报警来源
    private String alarmSource;

    //确认时间
    private String confirmTime;

    //型号ID（26）
    private String satId;

    //确认类别
    private String confirmType;

    //实际确认类别
    private String realConfirmType;

    //实际情况说明
    private String relRemark;

    //情况说明
    private String remark;

    //确认人
    private String confirmUser;

    //实判或者预判
    private String iden;

    //责任人ID
    private String dutyUserId;

    //报警ID
    private String alarmId;

    //如果不是空 则显示小红旗
    private String sufid;


}
