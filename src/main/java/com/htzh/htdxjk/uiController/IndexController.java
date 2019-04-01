package com.htzh.htdxjk.uiController;


import com.htzh.htdxjk.entity.LoginStatus;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Api(value = "user-api", tags = {"主页入口"})
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(HttpSession httpSession, HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {

        //判断用户是否登录，如果未登录，则清除cookie
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
        if(ls==null){

            //清除客户端 cookie
            String cookie_key = Base64.getEncoder().encodeToString("ht_user_info".getBytes("utf-8"));
            Cookie c = new Cookie(cookie_key,null);
            c.setMaxAge(0);
            c.setPath("/");
            httpServletResponse.addCookie(c);
        }
        return "/htdxjk/index/index";
    }


}


