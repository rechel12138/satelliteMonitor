package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/notice/")
public class UiNoticeController {

    /**
     * 通知管理
     * @return
     */
    @RequestMapping("index")
    public  String index(){

        return "/htdxjk/notice/tongzhiguanli";
    }
}
