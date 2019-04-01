package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/basicDataQuery/")
public class UiBasicdataqueryController {

    /**
     * 测控计划
     * @return
     */
    @RequestMapping("ckjh")
    public String ckjh(){

        return "/htdxjk/basicDataQuery/cekongjihua";
    }

    /**
     * 实际跟踪弧段
     * @return
     */
    @RequestMapping("sjgzhd")
    public String sjgzhd(){

        return "/htdxjk/basicDataQuery/shijigenzonghuduan";
    }

    /**
     * 轨道根数
     * @return
     */
    @RequestMapping("gdgs")
    public String gdgs(){

        return "/htdxjk/basicDataQuery/guidaogenshu";
    }

    /**
     * 航天器基本信息
     * @return
     */
    @RequestMapping("htqjdxx")
    public String htqjdxx(){

        return "/htdxjk/basicDataQuery/hangtianqijibenxinxi";
    }
}
