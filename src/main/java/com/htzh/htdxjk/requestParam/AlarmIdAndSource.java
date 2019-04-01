package com.htzh.htdxjk.requestParam;

import lombok.Data;

/**
 * @ClassName AlarmIdAndSource
 * @Description TOOD
 * @Author guoconglin
 * @DATE 2019/3/22 13:31
 * @Version 1.0
 **/

@Data
public class AlarmIdAndSource {

    private String confirmType;

    private String confirmMsg;

    private String atpId;

    private String alarmSource;
}
