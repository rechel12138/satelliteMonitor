package com.htzh.htdxjk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.entity.Spageconfig;
import com.htzh.htdxjk.requestParam.SpageConfigParam;
import com.htzh.htdxjk.requestParam.SpageconfigRequest;
import com.htzh.htdxjk.service.ISpageconfigService;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-29
 */
@RestController
@Api(value = "user-api", tags = {"页面管理"})
@Slf4j
@RequestMapping("/htdxjk/spageconfig")
public class SpageconfigController {

    @Autowired
    ISpageconfigService iSpageconfigService;


    /**
     * 查询页面管理
     * @param httpSession
     * @return
     */
    @ApiOperation(value = "查询页面管理", notes = "查询页面管理")
    @PostMapping(value = "listSpageconfig", produces = "application/json;charset=UTF-8")
    public String listSpageconfig(HttpSession httpSession) {
        //检查权限
        if (!Utils.checkLoginStatus(httpSession)) {
            return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

        }
        //具体过程
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
        Map map = new HashMap();
        map.put("userid",ls.getSUser().getSuAtpid());
        List<Map> mapList = iSpageconfigService.getSpageList(map);
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, mapList);
    }


    /**
     * 查询页面管理详细信息
     * @param httpSession
     * @return
     */
    @ApiOperation(value = "查询页面管理详细信息", notes = "查询页面管理详细信息")
    @PostMapping(value = "listOneSpageconfig", produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "flag", value = "flag", required = true, dataType = "String")
    public String listOneSpageconfig(HttpSession httpSession,@RequestParam("flag") String flag) {
        //检查权限
        if (!Utils.checkLoginStatus(httpSession)) {
            return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
        }
        //具体过程
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
        Map map = new HashMap();
        map.put("userid",ls.getSUser().getSuAtpid());
        map.put("flag",flag);
        List<Map> mapList = iSpageconfigService.getOneSpageList(map);
        return Utils.makeJSONResponseMsg(ResultTo.QUERY_SUCCESS_STATUS, ResultTo.QUERY_SUCCESS_STATUS_MSG, mapList);
    }

    /**
     * 增加
     */
    @ApiOperation(value = "新增页面管理", notes = "新增页面管理")
    @PostMapping(value = "addSpageConfig", produces = "application/json;charset=UTF-8")
    public String addSpageConfig(@RequestBody SpageconfigRequest spageconfigRequest, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");

            String pageName = spageconfigRequest.getName();
            List<SpageConfigParam> slist = spageconfigRequest.getList();

            List<Spageconfig> spageconfigs = new ArrayList<>();

            String bpcFlag = UUID.randomUUID().toString();

            for (SpageConfigParam spageConfigParam : slist) {

                Spageconfig inputEntity = new Spageconfig();
                inputEntity.setBpcAtpid(UUID.randomUUID().toString());
                inputEntity.setBpcAtpcreatedatetime(Utils.getNow());
                inputEntity.setBpcAtpcreateuser(ls.getSUser().getSuAccount());
                inputEntity.setBpcAtplastmodifydatetime(Utils.getNow());
                inputEntity.setBpcAtplastmodifyuser(ls.getSUser().getSuAccount());
                inputEntity.setBpcFksuserid(ls.getSUser().getSuAtpid());
                inputEntity.setBpcName(pageName);
                inputEntity.setBpcFlag(bpcFlag);
                inputEntity.setBpcSort(spageConfigParam.getSort());
                inputEntity.setBpcTitle(spageConfigParam.getTitle());
                inputEntity.setBpcUrl(spageConfigParam.getUrl());


                spageconfigs.add(inputEntity);
            }


            iSpageconfigService.saveBatch(spageconfigs);

            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, spageconfigs);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    /**
     * 删除
     */
    @PostMapping(value = "removeSpageConfig", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据spageFlag批量", notes = "根据spageFlag批量")
    @ApiImplicitParam(name = "spageFlag", value = "spageFlag", required = true, dataType = "String")
    public String removeSpageConfig(@RequestParam("spageFlag") String spageFlag) {
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("bpc_flag",spageFlag);
            boolean b=iSpageconfigService.remove(queryWrapper);
            if(b){

                return Utils.makeJSONResponseMsg(ResultTo.DEL_SUCCESS_STATUS, ResultTo.DEL_SUCCESS_STATUS_MSG, null);
            }else {
                return Utils.makeJSONResponseMsg(ResultTo.DEL_FAIL_STATUS, ResultTo.DEL_FAIL_STATUS_MSG, null);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.DEL_FAIL_STATUS, ResultTo.DEL_FAIL_STATUS_MSG, null);
        }
    }

}
