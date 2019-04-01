package com.htzh.htdxjk.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName PreJudgAlarm
 * @Description TOOD
 * @Author guoconglin
 * @DATE 2019/3/22 12:37
 * @Version 1.0
 **/

@Data
public class PreJudgAlarm implements Serializable{

    private String atpId;

    private String preJudgeTime;

    private String preJudgeType;

    private String preJudgeRemark;

    private String source;

    private String isTrace = "否";

    private String satId;

    private String bsatCode;
}
