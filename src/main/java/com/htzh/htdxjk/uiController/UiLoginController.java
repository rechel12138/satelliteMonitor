package com.htzh.htdxjk.uiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Controller
@RequestMapping("/htdxjk/login/")
public class UiLoginController {

    /**
     * 登录页面
     * @return
     */
    @RequestMapping("in")
    public String in(){

        return "htdxjk/login/in";
    }

    /**
     * 登录注销
     * @return
     */
    @RequestMapping("out")
    public String out(HttpSession hs, HttpServletResponse response) throws UnsupportedEncodingException {
        //清除服务器session
        hs.setAttribute("loginStatus", null);
        //清除客户端 cookie
        String cookie_key = Base64.getEncoder().encodeToString("ht_user_info".getBytes("utf-8"));
        //System.out.println(cookie_key);
        Cookie c = new Cookie(cookie_key,null);
        c.setMaxAge(0);
        c.setPath("/");
        response.addCookie(c);
        return "redirect:/index";
    }
}
