package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/htdxjk/fileShare/")
public class UiFileshareController {

    /**
     * 文件共享区
     * @return
     */
    @RequestMapping("index")
    public  String index(){

        return "/htdxjk/fileShare/wenjianfenxiangqu";
    }
}
