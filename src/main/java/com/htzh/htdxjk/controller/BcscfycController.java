package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.Bcscfyc;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.entity.Suser;
import com.htzh.htdxjk.service.IBcscfycService;
import com.htzh.htdxjk.utils.EzuiUtil;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  超寿重复异常
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"超寿重复异常"})
@RequestMapping("/api/bcscfyc")
public class BcscfycController {


    @Autowired
    private IBcscfycService iBcscfycService;


    //查询

    @PostMapping(value = "listBcscfyc",produces = "application/json;charset=UTF-8")
    public String listBcscfyc() {
        List map = iBcscfycService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }
    @ApiOperation(value = "根据航天器型号id查询超寿重复异常", notes = "查询")
    @PostMapping(value = "listBcscfycBySatId",produces = "application/json;charset=UTF-8")
    public String listBcscfycBySatId(@RequestParam("satId") String satId) {
        List map = iBcscfycService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    //添加超寿重复异常
    @ApiOperation(value = "添加超寿重复异常(完成)", notes = "添加超寿重复异常")
    @PostMapping(value = "addBcscfyc",produces = "application/json;charset=UTF-8")
    public String addBcscfyc(Bcscfyc inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
            inputEntity.setBcscfycAtpid(UUID.randomUUID().toString());
            inputEntity.setBcscfycAtpcreatedatetime(Utils.getNow());
            inputEntity.setBcscfycAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBcscfycAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBcscfycAtplastmodifyuser(ls.getSUser().getSuAccount());

            boolean save = iBcscfycService.save(inputEntity);
            if(save){
                return Utils.makeJSONResponseMsg(1, "添加成功", save);
            }
            return Utils.makeJSONResponseMsg(ResultTo.ADD_SUCCESS_STATUS, ResultTo.ADD_SUCCESS_STATUS_MSG, save);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());

            return Utils.makeJSONResponseMsg(ResultTo.ADD_FAIL_STATUS, ResultTo.ADD_SUCCESS_STATUS_MSG, null);
        }
    }

    /**      * 修改      */
    @ApiOperation(value = "修改", notes = "修改")
    @PostMapping(value = "updateBcscfyc",produces = "application/json;charset=UTF-8")
    public String updateBcscfyc(Bcscfyc inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
            inputEntity.setBcscfycAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBcscfycAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBcscfycService.updateById(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }

    }

//    /**      * 删除      */
//
//    @PostMapping("removeBcscfyc")
//    public String removeBcscfyc(@RequestParam("bcscfycAtpid") String id, HttpSession httpSession) {
//
//        try {
//            //检查权限
//            if (!Utils.checkLoginStatus(httpSession)) {
//                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
//
//            }
//            //具体过程
//            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
//            iBcscfycService.removeById(id);
//            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            log.error(e.getLocalizedMessage());
//            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
//        }
//
//    }
    /**      * 删除      */
    @PostMapping(value = "removeBcscfyc",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "bcscfycAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBcscfyc(@RequestParam("bcscfycAtpid") String bcscfycAtpid) {
        try {
            String[] idsArray=bcscfycAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBcscfycService.removeByIds(arrayList);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    @ApiOperation(value = "根据关键字查询信息", notes = "查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })

    @PostMapping(value = "findByParamEzui", produces = "application/json;charset=UTF-8")
    public String findByParamEzui(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                  @RequestParam(value = "page", defaultValue = "1") String page,
                                  @RequestParam(value = "rows", defaultValue = "10") String rows,
                                  @RequestParam(value = "sort", defaultValue = "bcscfyc_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bcscfyc obj = new Bcscfyc();
        //筛选字段list
        List list = new ArrayList();

        list.add("bcscfyc_id");
        list.add("bcscfyc_code");
        list.add("bcscfyc_satellitecode");
        list.add("bcscfyc_exceptionname");
        list.add("bcscfyc_occurredtime");
        list.add("bcscfyc_appearance");
        list.add("bcscfyc_result");
        list.add("bcscfyc_owner");
        list.add("bcscfyc_status");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBcscfycService);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());

            return Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        return JSON.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");


    }

    /**
     * 根据atpid获取一条信息
     * @param atpId
     * @return
     */
    @ApiOperation(value = "根据atpid获取一条信息", notes = "根据atpid获取一条信息")
    @PostMapping(value = "/getOneById", produces = "application/json;charset=UTF-8")
    public String getOneById(@RequestParam(value = "atpId", defaultValue = "") String atpId) {
        try {
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBcscfycService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }
}
