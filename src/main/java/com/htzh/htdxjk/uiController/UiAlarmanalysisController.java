package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/alarmAnalysis/")
public class UiAlarmanalysisController {

    /**
     * 报警分类分析
     * @return
     */
    @RequestMapping("index")
    public  String index(){

        return "htdxjk/alarmAnalysis/baojingfenleifenxi";
    }
}
