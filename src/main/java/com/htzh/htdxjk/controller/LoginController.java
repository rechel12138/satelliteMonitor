package com.htzh.htdxjk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htzh.htdxjk.entity.Bsat;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.entity.Suser;
import com.htzh.htdxjk.service.IBsatService;
import com.htzh.htdxjk.service.IBsatsetService;
import com.htzh.htdxjk.service.ISuserService;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Api(value = "user-api", tags = {"登录登出"})
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private ISuserService sUserService;

    @Autowired
    private IBsatService iBsatService;


    @ApiOperation(value = "用户登录（完成）", notes = "登录")
    @PostMapping(value="login",produces = "application/json;charset=UTF-8")
    public String login(@RequestParam("su_account") String su_account, @RequestParam("su_password") String su_password, HttpSession httpSession) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Suser suser = new Suser();
        suser.setSuAccount(su_account);


        QueryWrapper<Suser> suserQueryWrapperw = new QueryWrapper<Suser>(suser);

        Suser su = sUserService.getOne(suserQueryWrapperw);

        if ((su!=null)&&(su.getSuPassword().equals(Utils.makeMd5(su_password)))) {

            //更新最后登录时间
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            su.setSuLastlogindatetime(sdf.format(new Date()));
            sUserService.updateById(su);


            StringBuffer stringBuffer = new StringBuffer();
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("bsat_admin_subs_id",su.getSuId());
            List<Bsat> bsatList=iBsatService.list(queryWrapper);
            for(int i =0;i<bsatList.size();i++){
                if(i==bsatList.size()-1){
                    stringBuffer.append(bsatList.get(i).getBsatCode());
                }else{
                    stringBuffer.append(bsatList.get(i).getBsatCode()+"','");
                }

            }

            LoginStatus ls = new LoginStatus();
            ls.setSUser(su);
            ls.setBsatCodes(stringBuffer.toString());
            Map map = new HashMap();
            map.put("userid",ls.getSUser().getSuAtpid());
            HashSet hashSet =sUserService.getApiByUserid(map);
            ls.setApiPermissionSet(hashSet);
            su.setSuPassword("");
            httpSession.setAttribute("loginStatus", ls);


            httpSession.setMaxInactiveInterval(3600*24);
            return Utils.makeJSONResponseMsg(1, "登录成功", su);

        } else {

            return Utils.makeJSONResponseMsg(0, "该用户不存在", null);
        }


    }




}
