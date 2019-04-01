package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/alarmConfirm/")
public class UiAlarmconfirmController{

        /**
         * 报警确认
         * @return
         */

    @RequestMapping("index")

    public String index(){

        return "htdxjk/alarmConfirm/baojingqueren";
    }


}
