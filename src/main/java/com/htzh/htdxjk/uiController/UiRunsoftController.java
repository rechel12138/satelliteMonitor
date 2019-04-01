package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/runSoft/")
public class UiRunsoftController {

    /**
     * 所有卫星
     * @return
     */
    @RequestMapping("sywx")
    public String sywx(String st,Model model){

        model.addAttribute("st",st);
        System.out.println(st);
        return "htdxjk/runSoft/suoyouweixing";
    }

    /**
     * 传输型遥感卫星
     * @return
     */
    @RequestMapping("csxygwx")
    public String csxygwx(){

        return "htdxjk/runsoft/chuanshuxingyaoganweixing";
    }

    /**
     * 通信卫星
     * @return
     */
    @RequestMapping("txwx")
    public String txwx(){

        return "htdxjk/runsoft/tongxinweixing";
    }

    /**
     * 导航卫星
     * @return
     */
    @RequestMapping("dhwx")
    public String dhwx(){

        return "htdxjk/runsoft/daohangweixing";
    }

    /**
     * 小卫星
     * @return
     */
    @RequestMapping("xwx")
    public String xwx(){

        return "htdxjk/runsoft/xiaoweixing";
    }
}
