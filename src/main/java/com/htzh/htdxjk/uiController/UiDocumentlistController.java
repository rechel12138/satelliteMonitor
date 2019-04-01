package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/documentList/")
public class UiDocumentlistController {

    /**
     * 常用文件
     * @return
     */
    @RequestMapping("index")
    public String index(){

        return "htdxjk/documentList/changyongwenjian";
    }
}
