package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/workSpace/")
public class UiWorkspaceController {
    /**
     * 页面管理
     * @return
     */
    @RequestMapping("ymgl")
    public String ymgl(){

        return "/htdxjk/workSpace/ymgl";
    }

    /**
     * 个人网盘
     * @return
     */
    @RequestMapping("grwp")
    public String grwp(){

        return "grwp";
    }

    /**
     * 型号分类管理
     * @return
     */
    @RequestMapping("xhflgl")
    public String xhflgl(){

        return "xhflgl";
    }


}
