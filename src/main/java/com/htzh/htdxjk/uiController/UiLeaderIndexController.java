package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/leaderindex/")
public class UiLeaderIndexController {

    @RequestMapping("index")
    public String index(){

        return "/htdxjk/leaderindex/zerenren";
    }
}
