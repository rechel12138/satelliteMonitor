package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/watcherindex/")
public class UiWatcherIndexController {

    @RequestMapping("index")
    public String index(){

        return "/htdxjk/watcherindex/zhibanren";
    }
}
