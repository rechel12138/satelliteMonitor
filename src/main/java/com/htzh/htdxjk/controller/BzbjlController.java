package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.IBzbjlService;
import com.htzh.htdxjk.service.IBzbjlService;
import com.htzh.htdxjk.service.IBzbswService;
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
 * @author 值班人列表
 * @since 2019-02-23
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"值班列表"})
@RequestMapping("/api/bzbjl")
public class BzbjlController {


    @Autowired
    private IBzbjlService iBzbjlService;


    //查询
    @ApiOperation(value = "值班列表(完成)", notes = "查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "keyWord", value = "关键字", defaultValue = "", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", defaultValue = "10", dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String"),
            @ApiImplicitParam(name = "dutyTime", value = "值班时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "dutyPerson1", value = "值班人1", required = false, dataType = "String"),
            @ApiImplicitParam(name = "dutyPerson2", value = "值班人2", required = false, dataType = "String"),
            @ApiImplicitParam(name = "addPerson", value = "添加人", required = false, dataType = "String")
    })
    @PostMapping(value = "listBzbjl", produces = "application/json;charset=UTF-8")
    public String listBzbjl(@RequestParam(value = "keyWord", defaultValue = "") String keyWord,
                            @RequestParam(value = "page", defaultValue = "1") String page,
                            @RequestParam(value = "rows", defaultValue = "10") String rows,
                            @RequestParam(value = "sort", defaultValue = "dutydate") String sort,
                            @RequestParam(value = "order", defaultValue = "desc") String order,
                            @RequestParam(value = "dutyTime", defaultValue = "") String dutyTime,
                            @RequestParam(value = "dutyPerson1", defaultValue = "") String dutyPerson1,
                            @RequestParam(value = "dutyPerson2", defaultValue = "") String dutyPerson2,
                            @RequestParam(value = "addPerson", defaultValue = "") String addPerson) {
        Map<String, Object> map = new HashMap<>();
        int offset = Integer.parseInt(page) * Integer.parseInt(rows) - Integer.parseInt(rows);
        map.put("keyWord", keyWord);
        map.put("offset", offset);
        map.put("rows", Integer.parseInt(rows));
        map.put("sort", sort);
        map.put("order", order);
        map.put("dutyTime", dutyTime);
        map.put("dutyPerson1", dutyPerson1);
        map.put("dutyPerson2", dutyPerson2);
        map.put("addPerson", addPerson);
        Map<String, Object> bzbjlListByParamEzui = iBzbjlService.findBzbjlListByParamEzui(map);
        return JSON.toJSONString(bzbjlListByParamEzui, SerializerFeature.WriteMapNullValue);
    }

    @ApiOperation(value = "多条件查询值班列表", notes = "查询")
    @PostMapping(value = "listBzbjlByCon", produces = "application/json;charset=UTF-8")
    public String listBzbjlByCon(@RequestParam("startTime") String startTime, @RequestParam("zbr1id") String zbr1id, @RequestParam("zbr2id") String zbr2id, @RequestParam("tjrid") String tjrid, @RequestParam("endTime") String endTime) {
        List map = iBzbjlService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**
     * 增加
     */
    @ApiOperation(value = "增加（完成）", notes = "增加")
    @PostMapping(value = "addBzbjl", produces = "application/json;charset=UTF-8")
    public String addBzbjl(Bzbjl inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            String atpId = UUID.randomUUID().toString();
            inputEntity.setBzbjlAtpid(atpId);
            inputEntity.setBzbjlAtpcreatedatetime(Utils.getNow());
            inputEntity.setBzbjlAddtime(Utils.getNow());
            inputEntity.setBzbjlAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBzbjlAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBzbjlAtplastmodifyuser(ls.getSUser().getSuAccount());
            inputEntity.setBzbjlInfoid(atpId);
            inputEntity.setBzbjlAddperson(ls.getSUser().getSuAtpid());

            iBzbjlService.save(inputEntity);
            return Utils.makeJSONResponseMsg(1, "新增成功", null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改")
    @PostMapping(value = "updateBzbjl", produces = "application/json;charset=UTF-8")
    public String updateBzbjl(Bzbjl inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBzbjlAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBzbjlAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBzbjlService.updateById(inputEntity);
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
    @PostMapping(value = "removeBzbjl", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "bzbjlAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBzbjl(@RequestParam("bzbjlAtpid") String bzbjlAtpid) {
        try {
            String[] idsArray = bzbjlAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBzbjlService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "bzbjl_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bzbjl obj = new Bzbjl();
        //筛选字段list
        List list = new ArrayList();

        list.add("bzbjl_infoid");
        list.add("bzbjl_person1");
        list.add("bzbjl_person2");
        list.add("bzbjl_addperson");
        list.add("bzbjl_addtime");
        list.add("bzbjl_week");
        list.add("bzbjl_dutydate");
        list.add("bzbjl_infomodifytime");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBzbjlService);
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBzbjlService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }
}
