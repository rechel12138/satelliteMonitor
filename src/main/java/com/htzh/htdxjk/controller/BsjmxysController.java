package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.Bsjmxys;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.entity.Suser;
import com.htzh.htdxjk.service.IBsjmxysService;
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
 * 前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-24
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"三级门限延时报警信息"})
@RequestMapping("/api/bsjmxys")
public class BsjmxysController {


    @Autowired
    private IBsjmxysService iBsjmxysService;


    /**
     * 查询
     */
    @PostMapping(value = "listBsjmxys", produces = "application/json;charset=UTF-8")
    public String listBsjmxys() {
        List map = iBsjmxysService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**
     * 增加
     */

    @PostMapping(value = "addBsjmxys", produces = "application/json;charset=UTF-8")
    public String addBsjmxys(Bsjmxys inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBsjmxysAtpid(UUID.randomUUID().toString());
            inputEntity.setBsjmxysAtpcreatedatetime(Utils.getNow());
            inputEntity.setBsjmxysAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBsjmxysAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBsjmxysAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBsjmxysService.save(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 修改
     */

    @PostMapping(value = "updateBsjmxys", produces = "application/json;charset=UTF-8")
    public String updateBsjmxys(Bsjmxys inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBsjmxysAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBsjmxysAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBsjmxysService.updateById(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }

    }



    /**
     * 删除
     */
    @PostMapping(value = "removeBsjmxys", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "bsjmxysAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBsjmxys(@RequestParam("bsjmxysAtpid") String bsjmxysAtpid) {
        try {
            String[] idsArray = bsjmxysAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBsjmxysService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "bsjmxys_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bsjmxys obj = new Bsjmxys();
        //筛选字段list
        List list = new ArrayList();

        list.add("bsjmxys_satid");
        list.add("bsjmxys_begintime");
        list.add("bsjmxys_endtime");
        list.add("bsjmxys_alarmlevel");
        list.add("bsjmxys_alarmmsg");
        list.add("bsjmxys_responetime");
        list.add("bsjmxys_username");
        list.add("bsjmxys_response");
        list.add("bsjmxys_alarmvalue");
        list.add("bsjmxys_createtime");
        list.add("bsjmxys_updatetime");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBsjmxysService);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        return JSON.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");


    }

    /**
     * 根据atpid获取一条信息
     *
     * @param atpId
     * @return
     */
    @ApiOperation(value = "根据atpid获取一条信息", notes = "根据atpid获取一条信息")
    @PostMapping(value = "/getOneById", produces = "application/json;charset=UTF-8")
    public String getOneById(@RequestParam(value = "atpId", defaultValue = "") String atpId) {
        try {
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBsjmxysService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }
}
