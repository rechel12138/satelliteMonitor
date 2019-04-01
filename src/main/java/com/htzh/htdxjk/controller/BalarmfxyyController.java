package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.Balarmfxyy;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.entity.Suser;
import com.htzh.htdxjk.service.IBalarmfxyyService;
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
 *  前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"历史报警分类分析-延遥"})
@RequestMapping("/api/balarmfxyy")
public class BalarmfxyyController {


    @Autowired
    private IBalarmfxyyService iBalarmfxyyService;


    //查询

    @PostMapping(value = "listBalarmfxyy",produces = "application/json;charset=UTF-8")
    public String listBalarmfxyy() {
        List map = iBalarmfxyyService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**      * 增加     */

    @PostMapping(value = "addBalarmfxyy",produces = "application/json;charset=UTF-8")
    public String addBalarmfxyy(Balarmfxyy inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
            inputEntity.setBalarmfxyyAtpid(UUID.randomUUID().toString());
            inputEntity.setBalarmfxyyAtpcreatedatetime(Utils.getNow());
            inputEntity.setBalarmfxyyAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBalarmfxyyAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBalarmfxyyAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBalarmfxyyService.save(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**      * 修改      */

    @PostMapping(value = "updateBalarmfxyy",produces = "application/json;charset=UTF-8")
    public String updateBalarmfxyy(Balarmfxyy inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
            inputEntity.setBalarmfxyyAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBalarmfxyyAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBalarmfxyyService.updateById(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }

    }

//    /**      * 删除      */
//
//    @PostMapping("removeBalarmfxyy")
//    public String removeBalarmfxyy(@RequestParam("balarmfxyyAtpid") String id, HttpSession httpSession) {
//
//        try {
//            //检查权限
//            if (!Utils.checkLoginStatus(httpSession)) {
//                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
//
//            }
//            //具体过程
//            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
//            iBalarmfxyyService.removeById(id);
//            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            log.error(e.getLocalizedMessage());
//            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
//        }
//
//    }

    /**      * 删除      */
    @PostMapping(value = "removeBalarmfxyy",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "balarmfxyyAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBalarmfxyy(@RequestParam("balarmfxyyAtpid") String balarmfxyyAtpid) {
        try {
            String[] idsArray=balarmfxyyAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBalarmfxyyService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "balarmfxyy_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Balarmfxyy obj = new Balarmfxyy();
        //筛选字段list
        List list = new ArrayList();

        list.add("balarmfxyy_alarmid");
        list.add("balarmfxyy_satid");
        list.add("balarmfxyy_begintime");
        list.add("balarmfxyy_endtime");
        list.add("balarmfxyy_alarmlevel");
        list.add("balarmfxyy_alarmmsg");
        list.add("balarmfxyy_responetime");
        list.add("balarmfxyy_username");
        list.add("balarmfxyy_response");
        list.add("balarmfxyy_alarmvalue");
        list.add("balarmfxyy_type");
        list.add("balarmfxyy_remark");
        list.add("balarmfxyy_savetime");
        list.add("balarmfxyy_saveuser");
        list.add("balarmfxyy_plan");
        list.add("balarmfxyy_createtime");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBalarmfxyyService);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBalarmfxyyService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }
}
