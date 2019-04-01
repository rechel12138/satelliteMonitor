package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.ISmoduleService;
import com.htzh.htdxjk.service.ISlogService;
import com.htzh.htdxjk.service.ISmoduleService;
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
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-20
 */
@RestController
@Api(value = "user-api", tags = {"雇员菜单"})
@Slf4j
@RequestMapping("/api/smodule/")
public class SmoduleController {


    @Autowired
    private ISmoduleService iSmoduleService;


    //查询

    @PostMapping(value = "listSmodule", produces = "application/json;charset=UTF-8")
    public String listSmodule() {
        List map = iSmoduleService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**
     * 增加
     */

    @PostMapping(value = "addSmodule", produces = "application/json;charset=UTF-8")
    public String addSmodule(Smodule inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setSmAtpid(UUID.randomUUID().toString());
            inputEntity.setSmAtpcreatedatetime(Utils.getNow());
            inputEntity.setSmAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setSmAtplastmodifydatetime(Utils.getNow());
            inputEntity.setSmAtplastmodifyuser(ls.getSUser().getSuAccount());

            iSmoduleService.save(inputEntity);
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

    @PostMapping(value = "updateSmodule", produces = "application/json;charset=UTF-8")
    public String updateSmodule(Smodule inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setSmAtplastmodifydatetime(Utils.getNow());
            inputEntity.setSmAtplastmodifyuser(ls.getSUser().getSuAccount());

            iSmoduleService.updateById(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.UPDATE_FAIL_STATUS, ResultTo.UPDATE_FAIL_STATUS_MSG, null);
        }

    }


    /**
     * 删除
     */
    @PostMapping(value = "removeSmodule", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "smAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeSmodule(@RequestParam("smAtpid") String smAtpid) {
        try {
            String[] idsArray = smAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iSmoduleService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "sm_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Smodule obj = new Smodule();
        //筛选字段list
        List list = new ArrayList();

        list.add("sm_name");
        list.add("sm_webpath");
        list.add("sm_fksmoduleid");
        list.add("sm_icon");
        list.add("sm_isdisplay");
        list.add("sm_action");
        list.add("sm_remark");
        list.add("sm_islogineddisplay");
        list.add("sm_iscurrent");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iSmoduleService);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        return JSON.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");


    }

    /**
     * 获取一级菜单列表
     *
     * @return
     */
    @PostMapping(value = "getFirstMenuList", produces = "application/json;charset=UTF-8")
    public String getFirstMenuList() {

        try {


            QueryWrapper q = new QueryWrapper();
            q.eq("sm_fksmoduleid", "");
            List<Smodule> list = iSmoduleService.list(q);
            return Utils.makeJSONResponseMsg(1, "获取成功", list);

        } catch (Exception e) {

            e.printStackTrace();
            return Utils.makeJSONResponseMsg(0, "获取失败", null);
        }

    }

    /**
     * 根据一级菜单获取二级菜单，返回easyui datagrid格式
     */
    @PostMapping(value = "getSecondMenuListByFId", produces = "application/json;charset=UTF-8")
    public String getSecondMenuByFId(String id, int page, int rows) {

        try {

            int offset = page * rows - rows;
            Map<String, Object> para = new HashMap<>();
            para.put("id", id);
            para.put("offset", offset);
            para.put("rows", rows);
            Map<String, Object> result = iSmoduleService.getSecondMenuByFidForEzui(para);
            return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);

        } catch (Exception e) {

            e.printStackTrace();
            return Utils.makeJSONResponseMsg(0, "获取失败", null);
        }
    }

    /**
     * 根据菜单id获取菜单信息
     *
     * @param id
     * @return
     */
    @PostMapping(value = "getMenuInfoById", produces = "application/json;charset=UTF-8")
    public String getMenuInfoById(String id) {

        try {

            Smodule s = iSmoduleService.getById(id);
            return Utils.makeJSONResponseMsg(1, "获取成功", s);

        } catch (Exception e) {

            e.printStackTrace();
            return Utils.makeJSONResponseMsg(0, "获取失败", null);
        }


    }
}