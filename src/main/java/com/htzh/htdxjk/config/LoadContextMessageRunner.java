package com.htzh.htdxjk.config;

import com.htzh.htdxjk.service.ISuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * @Author:Rechel
 * @DATE:Created on 2019/3/18 13:20
 * @Modified By:
 * @Class Description:ApplicationRunner 实现
 */
//@Component
//@Order(value = 1)   //执行顺序控制
public class LoadContextMessageRunner implements ApplicationRunner {

    @Autowired
    ISuserService iSuserService;

    @Autowired
    SpringBeanTool springBeanTool;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        HashSet hashSet = iSuserService.getDefultApi();
        springBeanTool.getServletContext().setAttribute("defultApiSet", hashSet);
    }
}