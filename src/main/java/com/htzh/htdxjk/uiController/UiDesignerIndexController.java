package com.htzh.htdxjk.uiController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/designerindex/")
public class UiDesignerIndexController {

    /**
     * 设计师首页
     * @return
     */
    @RequestMapping("index")
    public String index(){

        return "/htdxjk/designerindex/shejishi";
    }
}
