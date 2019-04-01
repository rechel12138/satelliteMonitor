package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/xsrjgl/")
public class UiXsrjglController {

    /**
     * 分类管理
     * @return
     */
    @RequestMapping("category")
    public String category(){

        return "/htdxjk/xsrjgl/classifyManage";
    }

    /**
     * 显示软件
     * @return
     */
    @RequestMapping("soft")
    public String soft(){

        return "";
    }
}
