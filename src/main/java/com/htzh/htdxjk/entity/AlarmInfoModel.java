package com.htzh.htdxjk.entity;

import lombok.Data;

/**
 * @ClassName AlarmInfoModel
 * @Description TOOD
 * @Author guoconglin
 * @DATE 2019/3/30 19:43
 * @Version 1.0
 **/

@Data
public class AlarmInfoModel {

    private String satId;

    private String bsatCode;

    private String beginTime;

    private String dutyId;

    private String dutyName;
}
