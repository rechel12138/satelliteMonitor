package com.htzh.htdxjk.config;

import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.service.ISuserService;
import com.htzh.htdxjk.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

/**
 * @Author: Rechel
 * @Date: 2019/3/18 下午3:18
 */
@Slf4j
public class ApiPermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        boolean b = false;
        if (Utils.checkLoginStatus(httpServletRequest.getSession())) {

            LoginStatus ls = (LoginStatus)httpServletRequest.getSession().getAttribute("loginStatus");

            b=ls.getApiPermissionSet().contains(httpServletRequest.getRequestURI());


        }else {

            HashSet hashSet=(HashSet)httpServletRequest.getSession().getServletContext().getAttribute("defultApiSet");
            b=hashSet.contains(httpServletRequest.getRequestURI());
        }

        if(!b){
            String result = Utils.makeJSONResponseMsg(0,"没有该接口的权限",null);
            httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getWriter().print(result);
        }

        return  b;


    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
