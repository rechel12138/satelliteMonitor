package com.htzh.htdxjk.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.*;
import com.htzh.htdxjk.service.IBzbbwlService;
import com.htzh.htdxjk.utils.EzuiUtil;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.scene.input.DataFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 今日备忘事项
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"备忘录"})
@RequestMapping("/api/bzbbwl")
public class BzbbwlController {


    @Autowired
    private IBzbbwlService iBzbbwlService;

    @Autowired
    private IBzbbwlrelService bzbbwlrelService;

    @Autowired
    private IBzbswService bzbswService;


    @ApiOperation(value = "查询备忘事项", notes = "查询")
    @PostMapping(value = "listBzbbwl", produces = "application/json;charset=UTF-8")
    public String listBzbbwl() {
        List map = iBzbbwlService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    @ApiOperation(value = "多条件查询备忘事项", notes = "查询")
    @PostMapping(value = "listBzbbwlByCon", produces = "application/json;charset=UTF-8")
    public String listBzbbwlByCon(@RequestParam("startTime") String startTime, @RequestParam("zbr1id") String zbr1id, @RequestParam("zbr2id") String zbr2id, @RequestParam("tjrid") String tjrid, @RequestParam("endTime") String endTime) {
        List map = iBzbbwlService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**
     * 增加
     */
    @ApiOperation(value = "新增备忘事项(完成)", notes = "新增备忘事项")
    @PostMapping(value = "addBzbbwl", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public String addBzbbwl(Bzbbwl inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            String loginUserName = ls.getSUser().getSuChinesename();
            String loginUserId = ls.getSUser().getSuAtpid();
            final String bwlId = UUID.randomUUID().toString();
            inputEntity.setBzbbwlAtpid(bwlId);
            inputEntity.setBzbbwlAtpcreatedatetime(Utils.getNow());
            inputEntity.setBzbbwlAddtime(Utils.getNow());
            inputEntity.setBzbbwlAtpcreateuser(loginUserName);
            inputEntity.setBzbbwlAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBzbbwlAtplastmodifyuser(loginUserName);
            inputEntity.setBzbbwlAddperson(loginUserId);
            if (StrUtil.hasEmpty(inputEntity.getBzbbwlShowdate())) {
                DateTime date = DateUtil.parse(Utils.getNow());
                String showDate = DateUtil.format(date, "yyyy-MM-dd 08:30:00");
                inputEntity.setBzbbwlShowdate(showDate);
            }
            if (StrUtil.hasEmpty(inputEntity.getBzbbwlShowdays())) {
                inputEntity.setBzbbwlShowdays("1");
            }
            iBzbbwlService.save(inputEntity);
           /* Bzbbwlrel bzbbwlrel = new Bzbbwlrel();
            bzbbwlrel.setBzbbwlrelUserId(ls.getSUser().getSuAtpid());
            bzbbwlrel.setBzbbwlrelAtpcreateuser(ls.getSUser().getSuChinesename());
            bzbbwlrel.setBzbbwlrelAtpcreatedatetime(Utils.getNow());
            bzbbwlrel.setBzbbwlrelAtpid(UUID.randomUUID().toString());
            bzbbwlrel.setBzbbwlrelAtplastmodifydatetime(Utils.getNow());
            bzbbwlrel.setBzbbwlrelAtplastmodifyuser(ls.getSUser().getSuChinesename());
            bzbbwlrel.setBzbbwlrelBwlId(bwlId);
            bzbbwlrelService.save(bzbbwlrel);*/
            return Utils.makeJSONResponseMsg(1, "新增成功", null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "新增失败", null);
        }
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改备忘事项", notes = "修改备忘事项")
    @PostMapping(value = "updateBzbbwl", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public String updateBzbbwl(Bzbbwl inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBzbbwlAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBzbbwlAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBzbbwlService.updateById(inputEntity);
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
    @PostMapping(value = "removeBzbbwl", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "bzbbwlAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    @Transactional(rollbackFor = Exception.class)
    public String removeBzbbwl(@RequestParam("bzbbwlAtpid") String bzbbwlAtpid) {
        try {
            String[] idsArray = bzbbwlAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBzbbwlService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "bzbbwl_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bzbbwl obj = new Bzbbwl();
        //筛选字段list
        List list = new ArrayList();

        list.add("bzbbwl_remarkid");
        list.add("bzbbwl_showdate");
        list.add("bzbbwl_showtitle");
        list.add("bzbbwl_showdetail");
        list.add("bzbbwl_addtime");
        list.add("bzbbwl_addperson");
        list.add("bzbbwl_satcode");
        list.add("bzbbwl_state");
        list.add("bzbbwl_serialnum");
        list.add("bzbbwl_showdays");
        list.add("bzbbwl_createbykettle");
        list.add("bzbbwl_idinnorbit");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBzbbwlService);
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBzbbwlService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 今日备忘事项信息显示
     *
     * @return
     */
    @ApiOperation(value = "今日备忘事项信息显示(弃用)", notes = "今日备忘事项信息显示")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    @PostMapping(value = "/listNowBzbbwl", produces = "application/json;charset=UTF-8")
    public String listNowBzbbwl(@RequestParam(value = "page", defaultValue = "1") String page,
                                @RequestParam(value = "rows", defaultValue = "10") String rows,
                                @RequestParam(value = "sort", defaultValue = "bzbbwl_atplastmodifydatetime") String sort,
                                @RequestParam(value = "order", defaultValue = "desc") String order) {
        IPage<Bzbbwl> pageResult = null;
        QueryWrapper queryWrapper = new QueryWrapper();
        Page modelPage = new Page(Integer.parseInt(page), Integer.parseInt(rows));
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        queryWrapper.apply("    htdxjk_bzbbwl.bzbbwl_addtime >='" + sdf.format(now) + "'\n" +
                "AND htdxjk_bzbbwl.bzbbwl_addtime<='" + simpleDateFormat.format(now) + "'");
        if (order.equals("asc")) {
            modelPage.setAsc(sort);
        } else if (order.equals("desc")) {
            modelPage.setDesc(sort);
        } else {
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }

        pageResult = iBzbbwlService.page(modelPage, queryWrapper);
        return JSON.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");
    }

    /**
     * 根据搜索条件查询备忘录信息
     *
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @param satcode   卫星型号
     * @param state     状态
     * @param addperson 添加人
     * @return
     */
    @ApiOperation(value = "根据搜索条件查询备忘录信息(完成)", notes = "根据搜索条件查询备忘录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "起始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "satcode", value = "卫星型号Id，多个用逗号隔开", required = false, dataType = "String"),
            @ApiImplicitParam(name = "addperson", value = "添加人", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "state", value = "状态", required = false, dataType = "String"),
            @ApiImplicitParam(name = "title", value = "标题", required = false, dataType = "String"),
            @ApiImplicitParam(name = "detail", value = "详细", required = false, dataType = "String"),
            @ApiImplicitParam(name = "keyWord", value = "关键字", required = false, dataType = "String")
    })

    @PostMapping(value = "findBzByParamEzui", produces = "application/json;charset=UTF-8")
    public String findBzByParamEzui(@RequestParam(value = "startTime", defaultValue = "") String startTime,
                                    @RequestParam(value = "endTime", defaultValue = "") String endTime,
                                    @RequestParam(value = "satcode", defaultValue = "") String satcode,
                                    @RequestParam(value = "addperson", defaultValue = "") String addperson,
                                    @RequestParam(value = "page", defaultValue = "0") String page,
                                    @RequestParam(value = "rows", defaultValue = "0") String rows,
                                    @RequestParam(value = "state", defaultValue = "") String state,
                                    @RequestParam(value = "title", defaultValue = "") String title,
                                    @RequestParam(value = "detail", defaultValue = "") String detail,
                                    @RequestParam(value = "keyWord", defaultValue = "") String keyWord, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            Map<String,Object> map = new HashMap<>();
            int offset = 0;
            List<String> arrList = new ArrayList<>();
            if (!StrUtil.hasEmpty(satcode)) {
                String[] isStr = satcode.split(",");
                arrList = new ArrayList<>(Arrays.asList(isStr));
            }
            //map.put("userId",ls.getSUser().getSuAtpid());
            if(Integer.parseInt(page) != 0 && Integer.parseInt(rows) != 0){
                offset = Integer.parseInt(page)*Integer.parseInt(rows)-Integer.parseInt(rows);
            }
            map.put("offset",offset);
            map.put("rows",Integer.parseInt(rows));
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            map.put("arrList",arrList);
            map.put("addperson",addperson);
            map.put("title",title);
            map.put("detail",detail);
            map.put("state",state);
            map.put("keyWord",keyWord);
            Map<String, Object> resultMap = iBzbbwlService.listNowBzbbwlByParam(map);
            return JSON.toJSONString(resultMap, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            log.error(e.getMessage());
            return Utils.makeJSONResponseMsg(0, "失败", null);
        }
    }

    @ApiOperation(value = "值班人首页备忘录确认功能", notes = "值班人首页备忘录确认功能")
    @ApiImplicitParam(name = "atpId", value = "atpId主键", required = true, dataType = "String")
    @GetMapping(value = "confirmBzbbwlByAtpId", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public String confirmBzbbwlByAtpId(@RequestParam("atpId") String atpId, HttpSession httpSession) {
        try {
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            Bzbbwl byId = iBzbbwlService.getById(atpId);
            byId.setBzbbwlConfirm("确认");
            byId.setBzbbwlAtplastmodifydatetime(Utils.getNow());
            byId.setBzbbwlAtplastmodifyuser(ls.getSUser().getSuChinesename());
            byId.setBzbbwlState("closed");
            iBzbbwlService.updateById(byId);
            /*List<Bzbbwlrel> byBwlId = bzbbwlrelService.findByBwlId(atpId);
            if (CollUtil.isNotEmpty(byBwlId) && !StrUtil.hasEmpty(byBwlId.get(0).getBzbbwlrelBjlId())) {
                List<Bzbsw> byInfoid = bzbswService.findByInfoid(byBwlId.get(0).getBzbbwlrelBjlId());
                for (Bzbsw bzbsw : byInfoid) {
                    bzbsw.setBzbswAtplastmodifyuser(ls.getSUser().getSuChinesename());
                    bzbsw.setBzbswAtplastmodifydatetime(Utils.getNow());
                    bzbsw.setBzbswModifytime(Utils.getNow());
                    bzbsw.setBzbswState("closed");
                }
                bzbswService.saveBatch(byInfoid);
            }*/
            return Utils.makeJSONResponseMsg(1, "确认成功", null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "确认失败", null);
        }
    }
}
