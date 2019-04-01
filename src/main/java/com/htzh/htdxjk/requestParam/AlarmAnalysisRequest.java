package com.htzh.htdxjk.requestParam;

import lombok.Data;

import java.util.List;

/**
 * @ClassName AlarmAnalysisRequest
 * @Description TOOD
 * @Author guoconglin
 * @DATE 2019/3/22 13:29
 * @Version 1.0
 **/

@Data
public class AlarmAnalysisRequest {

    private List<AlarmIdAndSource> list;
}
