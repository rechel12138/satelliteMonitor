package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/basicData/")
public class UiBasicdataController {

    /**
     * 分类管理
     * @return
     */
    @RequestMapping("category")
    public String category(){

        return "/htdxjk/basicDataQueryManage/category";
    }

    /**
     * 基础数据
     * @return
     */
    @RequestMapping("data")
    public String data(){

        return "data";
    }
}
