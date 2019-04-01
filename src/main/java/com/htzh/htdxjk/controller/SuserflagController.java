package com.htzh.htdxjk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htzh.htdxjk.entity.Bckplan;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.entity.Suserflag;
import com.htzh.htdxjk.entity.Suserrole;
import com.htzh.htdxjk.service.ISuserflagService;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-30
 */
@RestController
@RequestMapping("/htdxjk/suserflag")
@Slf4j
@Api(value = "user-api", tags = {"用户报警标志"})
public class SuserflagController {

    @Autowired
    ISuserflagService iSuserflagService;

    /**
     * 增加
     */
    @ApiOperation(value = "新增", notes = "新增")
    @ApiImplicitParams(value = {
    @ApiImplicitParam(name = "from", value = "来源", required = true, dataType = "String"),
    @ApiImplicitParam(name = "bjAtpid", value = "报警id", required = true, dataType = "String")
    })
    @PostMapping(value = "addSuserflag", produces = "application/json;charset=UTF-8")
    public String addSuserflag(String from,String bjAtpid,HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);


            }

            Suserflag inputEntity= new Suserflag();
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setSufAtpid(UUID.randomUUID().toString());
            inputEntity.setSufAtpcreatedatetime(Utils.getNow());
            inputEntity.setSufAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setSufAtplastmodifydatetime(Utils.getNow());
            inputEntity.setSufAtplastmodifyuser(ls.getSUser().getSuAccount());
            inputEntity.setSufFrom(from);
            inputEntity.setSufFkbjid(bjAtpid);
            inputEntity.setSufFksuserid(ls.getSUser().getSuAtpid());

            iSuserflagService.save(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, inputEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 删除
     */
    @PostMapping(value = "removeSuserflag", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParam(name = "sufid", value = "小红旗id", required = true, dataType = "String")
    public String removeSuserflag(@RequestParam("sufid") String sufid) {
        try {
            iSuserflagService.removeById(sufid);

            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            return Utils.makeJSONResponseMsg(ResultTo.UPDATE_FAIL_STATUS, ResultTo.UPDATE_FAIL_STATUS_MSG, null);
        }
    }

}
