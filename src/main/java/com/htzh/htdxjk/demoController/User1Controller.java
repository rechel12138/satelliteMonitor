package com.htzh.htdxjk.demoController;


import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htzh.htdxjk.entity.User1;
import com.htzh.htdxjk.entity.User2;
import com.htzh.htdxjk.mapper.User2Mapper;
import com.htzh.htdxjk.service.IUser1Service;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Rechel
 * @since 2019-02-20
 */
//@Api(value = "user-api", tags = {"user1"})
//@RestController
//@RequestMapping("/api/user1")
@Transactional(rollbackFor = Exception.class)
public class User1Controller {

    @Autowired
    private IUser1Service iUser1Service;

    @Autowired
    private User2Mapper user2Mapper;

    @GetMapping("/index")
    public String add(Model model) {
        model.addAttribute("username", "测试变量输出");
        return "user1/index";
    }


    @GetMapping("/listdata")
    public String listdata(Model model) {
        return "success listdata ";
    }


    @PostMapping("listUser")
    public List listUser() {
        List map = iUser1Service.list();
        return map;
    }





    @PostMapping("listUser1")
    public Map<String, Object> listUser1() {
        Map<String, Object> map = iUser1Service.listUser1();
        return map;
    }


    @PostMapping("addForm")
    public void addForm(User1 user1) {
        iUser1Service.save(user1);
    }


    @PostMapping("deleteUser")
    public String deleteUser(@RequestParam("iid") Long id) {
        try {
            user2Mapper.deleteById(id);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 测试事务回滚
     */
    @PostMapping("testTransactions")
    public void testTransactions(HttpServletResponse httpServletResponse) {



            User1 user1 = new User1();
            user1.setId(32L);
            user1.setAge(18);
            user1.setName("halou");
            user1.setEmail("111");

            iUser1Service.save(user1);
            User1 user11 = new User1();
            user11.setId(33L);
            user11.setAge(18);
            user11.setName("11wwwwwk;lkj;lkj;laksjdf;lkjas;dlfkja;sldkjf;aslkdjf;aslkdjf;alksjdf;alksjdf;alksdjf;alskdjf;alskdjf;askdjf");
            user11.setEmail("111");
            iUser1Service.save(user11);


    }


    @Autowired
    WebApplicationContext applicationContext;

    @PostMapping(value = "getAllUrl")
    public Object getAllUrl() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();


        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            Map<String, String> map1 = new HashMap<String, String>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                map1.put("url", url);
            }
//            map1.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
//            map1.put("method", method.getMethod().getName()); // 方法名
//            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
//            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
//                map1.put("type", requestMethod.toString());
//            }

            list.add(map1);
        }

        return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping (value = "testWebsocket")
    public String testWebsocket() {

        for(int i=0;i<=10000;i++){

        }

        return "";
    }

}
