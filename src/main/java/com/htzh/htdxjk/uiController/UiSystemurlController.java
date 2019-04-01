package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//常用软件查询模块：
@RequestMapping("/htdxjk/systemUrl/")
public class UiSystemurlController {
    /**
     * 在轨责任人平台
     * @return
     */
    @RequestMapping("zgzrrpt")
    public String zgzrrpt(){

        return "/htdxjk/systemUrl/zgzrrpt";
    }

    /**
     * 在轨数据管理与应用系统
     * @return
     */
    @RequestMapping("zgsjglyyyxt")
    public  String zgsjglyyyxt(){

        return "/htdxjk/systemUrl/zgsjglyyyxt";
    }
}
