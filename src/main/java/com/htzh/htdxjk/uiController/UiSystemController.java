package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htdxjk/system/")
public class UiSystemController {

    /**
     * 系统配置-皮肤管理
     * @return
     */
    @RequestMapping("skin")
    public String skin(){

        return  "skin";
    }

    /**
     * 系统配置-菜单编辑
     * @return
     */
    @RequestMapping("module")
    public String module(){

        return "htdxjk/system/edit_nav";
    }

    /**
     * 系统配置-角色管理
     * @return
     */
    @RequestMapping("role")
    public String role(){

        return "htdxjk/system/role";
    }

    /**
     * 系统配置-用户管理
     * @return
     */
    @RequestMapping("user")
    public String user(){

        return "htdxjk/system/user";
    }

    /**
     * 系统配置-操作日志管理
     * @return
     */
    @RequestMapping("operationLog")
    public String operationLog(){
        return "htdxjk/system/operation_log";
    }

    /**
     * 系统配置-修改密码
     * @return
     */
    @RequestMapping("changePwd")
   public  String changePwd(){
        return "htdxjk/system/reset_pw";
    }


    @RequestMapping("ntkoTest")
    public String notkTest(){

        return "htdxjk/system/ntkoTest";
    }
}
