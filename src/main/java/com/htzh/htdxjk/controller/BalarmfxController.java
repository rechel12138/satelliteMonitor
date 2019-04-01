package com.htzh.htdxjk.controller;


import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.requestParam.AlarmIdAndSource;
import com.htzh.htdxjk.service.*;
import com.htzh.htdxjk.utils.EzuiUtil;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 历史报警分类分析
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"报警分类分析"})
@RequestMapping("/api/balarmfx")
public class BalarmfxController {


    @Autowired
    private IBalarmfxService iBalarmfxService;

    @Autowired
    private ISspeechService iSspeechService;

    @Autowired
    private IBsjmxssService bsjmxssService;

    @Autowired
    private IBsjmxysService bsjmxysService;

    @Autowired
    private IBdxznzdxtssbjService bdxznzdxtssbjService;


    /**
     * 查询
     *
     * @param alarmlevel
     * @return
     */
    @ApiOperation(value = "报警信息列表展示", notes = "报警信息列表展示")
    @ApiImplicitParam(name = "alarmlevel", value = "报警级别", dataType = "String")
    @PostMapping(value = "listBalarmfx", produces = "application/json;charset=UTF-8")
    public String listBalarmfx(@RequestParam(value = "alarmlevel", defaultValue = "") String alarmlevel) {
        Map balarmfxByParma = iBalarmfxService.getBalarmfxByParma(alarmlevel);
        return Utils.makeJSONResponseMsg(ResultTo.QUERY_SUCCESS_STATUS, ResultTo.QUERY_SUCCESS_STATUS_MSG, balarmfxByParma);
    }


    @ApiOperation(value = "根据卫星型号id查询历史报警分类分析", notes = "查询")
    @PostMapping(value = "listBalarmfxBySatid", produces = "application/json;charset=UTF-8")
    public String listBalarmfxBySatid(@RequestParam("satId") String satId) {
        List map = iBalarmfxService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    @ApiOperation(value = "根据卫星型号id、多条件 查询历史报警分类分析", notes = "查询")
    @PostMapping(value = "listBalarmfxBySatidAndCon", produces = "application/json;charset=UTF-8")
    public String listBalarmfxBySatidAndCon(@RequestParam("satId") String satId) {
        List map = iBalarmfxService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**
     * 增加
     *
     * @param inputEntity
     * @param httpSession
     * @return
     */
    @ApiOperation(value = "新增历史报警分类分析(责任人分析)（完成）", notes = "新增")
    @PostMapping(value = "addBalarmfx", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public String addBalarmfx(Balarmfx inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBalarmfxAtpid(UUID.randomUUID().toString());
            inputEntity.setBalarmfxAlarmid(inputEntity.getBalarmfxAtpid());
            inputEntity.setBalarmfxAtpcreatedatetime(Utils.getNow());
            inputEntity.setBalarmfxAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBalarmfxAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBalarmfxAtplastmodifyuser(ls.getSUser().getSuAccount());
            iBalarmfxService.save(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 修改
     *
     * @param inputEntity
     * @param httpSession
     * @return
     */
    @PostMapping(value = "updateBalarmfx", produces = "application/json;charset=UTF-8")
    public String updateBalarmfx(Balarmfx inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBalarmfxAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBalarmfxAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBalarmfxService.updateById(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }

    }


    /**
     * 删除
     *
     * @param balarmfxAtpid
     * @return
     */
    @PostMapping(value = "removeBalarmfx", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "balarmfxAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBalarmfx(@RequestParam("balarmfxAtpid") String balarmfxAtpid) {
        try {
            String[] idsArray = balarmfxAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBalarmfxService.removeByIds(arrayList);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    @ApiOperation(value = "根据关键字查询信息", notes = "查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "型号集", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })

    @PostMapping(value = "findByParamEzui", produces = "application/json;charset=UTF-8")
    public String findByParamEzui(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                  @RequestParam(value = "page", defaultValue = "1") String page,
                                  @RequestParam(value = "rows", defaultValue = "10") String rows,
                                  @RequestParam(value = "sort", defaultValue = "balarmfx_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {

        Balarmfx obj = new Balarmfx();
        //筛选字段list
        List list = new ArrayList();

        list.add("balarmfx_alarmid");
        list.add("balarmfx_satid");
        list.add("balarmfx_begintime");
        list.add("balarmfx_endtime");
        list.add("balarmfx_alarmlevel");
        list.add("balarmfx_alarmmsg");
        list.add("balarmfx_responetime");
        list.add("balarmfx_username");
        list.add("balarmfx_response");
        list.add("balarmfx_alarmvalue");
        list.add("balarmfx_type");
        list.add("balarmfx_remark");
        list.add("balarmfx_savetime");
        list.add("balarmfx_saveuser");
        list.add("balarmfx_plan");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBalarmfxService);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());

            return Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBalarmfxService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    /*
     * 根据卫星型号ID获取报警分类数据
     * @author guoconglin
     * @date 2019/3/13 15:16
     * @param: [satId, beginTime, endTime, alarmType]
     * @return: java.lang.String
     */
    @ApiOperation(value = "根据卫星satId获取信息(报警分析详情)(完成)", notes = "根据卫星satId获取信息(报警分析详情)")

    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "satId", value = "卫星ID", dataType = "String", required = true),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "String"),
            @ApiImplicitParam(name = "alarmType", value = "报警类别", dataType = "String"),
            @ApiImplicitParam(name = "alarmSource", value = "报警来源(包括三级门限实时、多星)", dataType = "String")
    })
    @PostMapping(value = "getBySatIdParamEzui", produces = "application/json;charset=UTF-8")
    public String getBySatIdParamEzui(@RequestParam(value = "satId", required = true) String satId,
                                      @RequestParam(value = "beginTime", defaultValue = "") String beginTime,
                                      @RequestParam(value = "endTime", defaultValue = "") String endTime,
                                      @RequestParam(value = "alarmType", defaultValue = "") String alarmType,
                                      @RequestParam(value = "alarmSource", defaultValue = "") String alarmSource) {
        try {
            Map<String, Object> bySatIdParamEzui = iBalarmfxService.getBySatIdParamEzui(satId, beginTime, endTime, alarmType, alarmSource);
            return JSON.toJSONString(bySatIdParamEzui, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    /**
     * 近期卫星报警信息柱状图
     *
     * @return
     */
    @ApiOperation(value = "近期卫星报警信息柱状图", notes = "近期卫星报警信息柱状图")
    @PostMapping(value = "/findList", produces = "application/json;charset=UTF-8")
    public String findList() {
        try {
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBalarmfxService.getById(1));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 近期卫星报警信息柱状图筛选近期天数
     *
     * @param
     * @return
     */
    @ApiOperation(value = "值班人近期卫星报警信息柱状图筛选近期天数(完成)", notes = "近期卫星报警信息柱状图筛选近期天数")
    @ApiImplicitParam(name = "numDays", value = "天数(默认3，一天传1，一周传7)", required = false, dataType = "int", defaultValue = "3")
    @PostMapping(value = "/findListByParam", produces = "application/json;charset=UTF-8")
    public String findListByParam(@RequestParam(value = "numDays", defaultValue = "3") int numDays) {
        try {
            Map<String, Object> map = iBalarmfxService.alarmStatistical(numDays);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, map);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    @ApiOperation(value = "报警确认(完成)", notes = "报警确认")
    @ApiImplicitParam(name = "atpId", value = "主键ID", required = true, dataType = "String")
    @GetMapping(value = "confirmBalarmfx", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public String confirmBalarmfx(@RequestParam("atpId") String atpId, HttpSession httpSession) {
        //检查权限
        if (!Utils.checkLoginStatus(httpSession)) {
            return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
        }
        //具体过程
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
        Balarmfx balarmfx = iBalarmfxService.getById(atpId);
        if (balarmfx == null) {
            return Utils.makeJSONResponseMsg(0, "该信息不存在", null);
        }
        balarmfx.setBalarmfxAtplastmodifydatetime(Utils.getNow());
        balarmfx.setBalarmfxAtplastmodifyuser(ls.getSUser().getSuAccount());
        balarmfx.setBalarmfxSavetime(Utils.getNow());
        balarmfx.setBalarmfxSaveuser(ls.getSUser().getSuAccount());
        boolean b = iBalarmfxService.updateById(balarmfx);

        iSspeechService.removeById(balarmfx.getBalarmfxAtpid());


        if (b) {
            return Utils.makeJSONResponseMsg(1, "报警确认成功", b);
        }
        return Utils.makeJSONResponseMsg(0, "报警确认失败", b);
    }


    /**
     * 导出excel
     *
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "导出excel", notes = "导出excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    @GetMapping(value = "exportExcel")
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(value = "keyword", defaultValue = "") String keyword,
                            @RequestParam(value = "sort", defaultValue = "balarmfx_atplastmodifydatetime") String sort,
                            @RequestParam(value = "order", defaultValue = "desc") String order) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");

        Balarmfx obj = new Balarmfx();
        //筛选字段list
        List list = new ArrayList();

        list.add("balarmfx_alarmid");
        list.add("balarmfx_satid");
        list.add("balarmfx_begintime");
        list.add("balarmfx_endtime");
        list.add("balarmfx_alarmlevel");
        list.add("balarmfx_alarmmsg");
        list.add("balarmfx_responetime");
        list.add("balarmfx_username");
        list.add("balarmfx_response");
        list.add("balarmfx_alarmvalue");
        list.add("balarmfx_type");
        list.add("balarmfx_remark");
        list.add("balarmfx_savetime");
        list.add("balarmfx_saveuser");
        list.add("balarmfx_plan");

        QueryWrapper modelQueryWrapperw = new QueryWrapper(obj);
        if (!com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(keyword)) {
            for (int i = 0; i < list.size(); i++) {

                if (i == (list.size() - 1)) {
                    modelQueryWrapperw.like(list.get(i), keyword);
                } else {
                    modelQueryWrapperw.like(list.get(i), keyword);
                    modelQueryWrapperw.or();
                }
            }
        }

        if (order.equals("asc")) {
            modelQueryWrapperw.orderByAsc(sort);
        } else if (order.equals("desc")) {
            modelQueryWrapperw.orderByDesc(sort);
        } else {
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }


        List<Balarmfx> bckplanList = iBalarmfxService.list(modelQueryWrapperw);
        //设置要导出的文件的名字
        String fileName = "alarm" + Utils.getNow() + ".xls";
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"序号", "报警id", "卫星id", "开始时间", "结束时间", "报警级别", "报警信息", "响应时间", "响应人", "响应", "报警值", "确认类别", "情况说明", "确认时间", "确认人", "计划"};


        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (Balarmfx bckplan : bckplanList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(rowNum);
            row1.createCell(1).setCellValue(bckplan.getBalarmfxAlarmid());
            row1.createCell(2).setCellValue(bckplan.getBalarmfxSatid());
            row1.createCell(3).setCellValue(bckplan.getBalarmfxBegintime());
            row1.createCell(4).setCellValue(bckplan.getBalarmfxEndtime());
            row1.createCell(5).setCellValue(bckplan.getBalarmfxAlarmlevel());
            row1.createCell(6).setCellValue(bckplan.getBalarmfxAlarmmsg());
            row1.createCell(7).setCellValue(bckplan.getBalarmfxResponetime());
            row1.createCell(8).setCellValue(bckplan.getBalarmfxUsername());
            row1.createCell(9).setCellValue(bckplan.getBalarmfxResponse());
            row1.createCell(10).setCellValue(bckplan.getBalarmfxAlarmlevel());
            row1.createCell(11).setCellValue(bckplan.getBalarmfxType());
            row1.createCell(12).setCellValue(bckplan.getBalarmfxRemark());
            row1.createCell(13).setCellValue(bckplan.getBalarmfxSavetime());
            row1.createCell(14).setCellValue(bckplan.getBalarmfxSaveuser());
            row1.createCell(15).setCellValue(bckplan.getBalarmfxPlan());
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }


    @ApiOperation(value = "获得遥测当前值", notes = "获得遥测当前值")
    @ApiImplicitParam(name = "ids", value = "主键ID", required = true, dataType = "String")
    @GetMapping(value = "getCurrentTm", produces = "application/json;charset=UTF-8")
    public String getCurrentTm(@RequestParam("ids") String ids, HttpSession httpSession) {
        if (null == ids) {
            return Utils.makeJSONResponseMsg(0, "参数不能为null", null);
        }
        ArrayList<Map<String, String>> retObj = new ArrayList<Map<String, String>>();
        String[] ids_Array = ids.split(",");
        for (String t : ids_Array) {
            Map<String, String> m = new HashMap<String, String>();
            m.put("id", t);
            m.put("value", String.valueOf(Math.random()));
            retObj.add(m);
        }
        return Utils.makeJSONResponseMsg(1, "获取成功", retObj);
    }


    @ApiOperation(value = "获取报警分类柱状", notes = "获取报警分类柱状")
    @ApiImplicitParam(name = "dayNum", value = "天数")
    @PostMapping(value = "getStatistical", produces = "application/json;charset=UTF-8")
    public String getStatistical(@RequestParam(value = "dayNum", defaultValue = "1") int dayNum, HttpSession httpSession) {
        if (!Utils.checkLoginStatus(httpSession)) {
            return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
        }
        //具体过程
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
        String userId = ls.getSUser().getSuAtpid();
        List statistical = iBalarmfxService.getStatistical(userId, dayNum);
        return Utils.makeJSONResponseMsg(1, "获取信息成功", statistical);


    }


    @ApiOperation(value = "根据关键字查询报警分类分析信息", notes = "查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "levels", value = "报警级别（多个用逗号分隔）", required = false, dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String"),
            @ApiImplicitParam(name = "showQr", value = "是否显示已确认", required = false, dataType = "String")
    })
    @PostMapping(value = "findByParamEzuiForBalarmfx", produces = "application/json;charset=UTF-8")
    public String findByParamEzuiForBalarmfx(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                             @RequestParam(value = "levels", defaultValue = "") String levels,
                                             @RequestParam(value = "startTime", defaultValue = "2000-01-01 00:00:00") String startTime,
                                             @RequestParam(value = "endTime", defaultValue = "2099-01-01 00:00:00") String endTime,
                                             @RequestParam(value = "page", defaultValue = "1") String page,
                                             @RequestParam(value = "rows", defaultValue = "10") String rows,
                                             @RequestParam(value = "sort", defaultValue = "bjsj") String sort,
                                             @RequestParam(value = "order", defaultValue = "desc") String order,
                                             @RequestParam(value = "showQr", defaultValue = "0") String showQr,
                                             HttpSession httpSession) {


        //检查权限
        if (!Utils.checkLoginStatus(httpSession)) {
            return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
        }

        IPage<Suser> pageResult = null;
        Page modelPage = new Page(Integer.parseInt(page), Integer.parseInt(rows));
        if (order.equals("asc")) {
            modelPage.setAsc(sort);
        } else if (order.equals("desc")) {
            modelPage.setDesc(sort);
        } else {
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }

        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
        String bsatCodes = ls.getBsatCodes();
        levels = levels.replace(",", "','");

        Map map = new HashMap();
        map.put("bsatCodes", bsatCodes);
        map.put("levels", levels);
        map.put("keyword", keyword);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("sort", sort);
        map.put("order", order);
        map.put("showQr", showQr);


        pageResult = iBalarmfxService.getBalarmfxUnion(modelPage, map);

        return JSONObject.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");
    }


    @ApiOperation(value = "根据关键字查询报警分类分析信息(完成)", notes = "查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "alarmlevel", value = "报警级别多个用逗号隔开", required = false, dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段（bsatCode,alarmSource）", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式desc,asc)", required = false, dataType = "String"),
            @ApiImplicitParam(name = "param", value = "筛选条件，由sort字段去判断", required = false, dataType = "String"),
            @ApiImplicitParam(name = "showQr", value = "是否显示未处理（0->不显示 1->显示）", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "days", value = "自定义按时间查询", required = false, dataType = "String"),

    })
    @PostMapping(value = "findAlarmListByParam", produces = "application/json;charset=UTF-8")
    public String findAlarmListByParam(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                       @RequestParam(value = "alarmlevel", defaultValue = "") String alarmlevel,
                                       @RequestParam(value = "startTime", defaultValue = "") String startTime,
                                       @RequestParam(value = "endTime", defaultValue = "") String endTime,
                                       @RequestParam(value = "page", defaultValue = "1") String page,
                                       @RequestParam(value = "rows", defaultValue = "10") String rows,
                                       @RequestParam(value = "sort", defaultValue = "") String sort,
                                       @RequestParam(value = "order", defaultValue = "") String order,
                                       @RequestParam(value = "param", defaultValue = "") String param,
                                       @RequestParam(value = "showQr", defaultValue = "0") String showQr,
                                       @RequestParam(value = "days", defaultValue = "0") String days,
                                       HttpSession httpSession) {


        //检查权限
        if (!Utils.checkLoginStatus(httpSession)) {
            return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
        }
        //具体过程
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
        String userId = ls.getSUser().getSuAtpid();
        int dayNum = Integer.parseInt(days);
        Map<String, Object> alarmListByParam = iBalarmfxService.findAlarmListByParam(Integer.parseInt(page), Integer.parseInt(rows), keyword, userId, alarmlevel, startTime, endTime, sort, order, showQr, dayNum,param);

        return JSONObject.toJSONString(alarmListByParam, SerializerFeature.WriteMapNullValue);
    }

    @ApiOperation(value = "报警分类分析批量分析", notes = "报警分类分析批量分析")
    @PostMapping(value = "batchAnalysis", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public String batchAnalysis(@RequestBody List<AlarmIdAndSource> alarmAnalysisRequest, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            String userId = ls.getSUser().getSuAtpid();
            String loginUserAccount = ls.getSUser().getSuAccount();
            List<Balarmfx> balarmfxList = new ArrayList<>();
            if (CollUtil.isNotEmpty(alarmAnalysisRequest)) {
                for (AlarmIdAndSource alarmIdAndSource : alarmAnalysisRequest) {
                    Balarmfx balarmfx = new Balarmfx();
                    balarmfx.setBalarmfxAtpid(UUID.randomUUID().toString());
                    balarmfx.setBalarmfxAtplastmodifyuser(loginUserAccount);
                    balarmfx.setBalarmfxAtplastmodifydatetime(Utils.getNow());
                    balarmfx.setBalarmfxAtpcreateuser(loginUserAccount);
                    balarmfx.setBalarmfxAtpcreatedatetime(Utils.getNow());
                    balarmfx.setBalarmfxRemark(alarmIdAndSource.getConfirmMsg());
                    balarmfx.setBalarmfxType(alarmIdAndSource.getConfirmType());
                    balarmfx.setBalarmfxSource(alarmIdAndSource.getAlarmSource());
                    if ("三级门限实时报警".equals(alarmIdAndSource.getAlarmSource())) {
                        Bsjmxss byId = bsjmxssService.getById(alarmIdAndSource.getAtpId());
                        balarmfx.setBalarmfxAlarmlevel(byId.getBsjmxssAlarmlevel());
                        balarmfx.setBalarmfxAlarmid(byId.getBsjmxssAtpid());
                        balarmfx.setBalarmfxAlarmmsg(byId.getBsjmxssAlarmmsg());
                        balarmfx.setBalarmfxAlarmvalue(byId.getBsjmxssAlarmvalue());
                        balarmfx.setBalarmfxBegintime(byId.getBsjmxssBegintime());
                        balarmfx.setBalarmfxEndtime(byId.getBsjmxssEndtime());
                        balarmfx.setBalarmfxSaveuser(byId.getBsjmxssUsername());
                        balarmfx.setBalarmfxSavetime(byId.getBsjmxssResponetime());
                        balarmfx.setBalarmfxIden("实判");
                    } else if ("多星智能诊断系统实时报警".equals(alarmIdAndSource.getAlarmSource())) {
                        Bdxznzdxtssbj byId = bdxznzdxtssbjService.getById(alarmIdAndSource.getAtpId());
                        balarmfx.setBalarmfxAlarmlevel(byId.getBdxznzdxtssbjAlarmLevel());
                        balarmfx.setBalarmfxAlarmid(byId.getBdxznzdxtssbjAtpid());
                        balarmfx.setBalarmfxAlarmmsg(byId.getBdxznzdxtssbjExplain());
                        balarmfx.setBalarmfxAlarmvalue(byId.getBdxznzdxtssbjValLevel());
                        balarmfx.setBalarmfxBegintime(byId.getBdxznzdxtssbjBegintime());
                        balarmfx.setBalarmfxEndtime(byId.getBdxznzdxtssbjEndtime());
                        balarmfx.setBalarmfxSaveuser(byId.getBdxznzdxtssbjConfirmUser());
                        balarmfx.setBalarmfxSavetime(byId.getBdxznzdxtssbjRecordTime());
                        balarmfx.setBalarmfxIden("实判");
                    } else if ("三级门限延时报警".equals(alarmIdAndSource.getAlarmSource())) {
                        Bsjmxys byId = bsjmxysService.getById(alarmIdAndSource.getAtpId());
                        balarmfx.setBalarmfxAlarmlevel(byId.getBsjmxysAlarmlevel());
                        balarmfx.setBalarmfxAlarmid(byId.getBsjmxysAtpid());
                        balarmfx.setBalarmfxAlarmmsg(byId.getBsjmxysAlarmmsg());
                        balarmfx.setBalarmfxAlarmvalue(byId.getBsjmxysAlarmvalue());
                        balarmfx.setBalarmfxBegintime(byId.getBsjmxysBegintime());
                        balarmfx.setBalarmfxEndtime(byId.getBsjmxysEndtime());
                        balarmfx.setBalarmfxSaveuser(byId.getBsjmxysUsername());
                        balarmfx.setBalarmfxSavetime(byId.getBsjmxysResponetime());
                        balarmfx.setBalarmfxIden("实判");
                    } else {
                        //后续再加报警来源
                    }
                    balarmfxList.add(balarmfx);
                }
                iBalarmfxService.saveBatch(balarmfxList);
            } else {
                return Utils.makeJSONResponseMsg(0, "请选择信息", null);
            }
            return Utils.makeJSONResponseMsg(1, "分析成功", null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "分析失败", null);
        }
    }


    @ApiOperation(value = "近期卫星报警分类统计图(完成by Rechel)", notes = "根据时间范围、用户atpid查询责任人首页近期卫星报警分类统计图信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, dataType = "String")
    })
    @PostMapping(value = "countZrrIndexType", produces = "application/json;charset=UTF-8")
    public String countZrrIndexType(
            @RequestParam(value = "startTime", defaultValue = "1900-01-01 00:00:00") String startTime,
            @RequestParam(value = "endTime", defaultValue = "2099-01-01 00:00:00") String endTime,
            HttpSession httpSession) {

        if (!Utils.checkLoginStatus(httpSession)) {
            return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

        }
        //具体过程
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");


        Map map = new HashMap();
        map.put("userId", ls.getSUser().getSuAtpid());
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        List<Map> mapList = iBalarmfxService.countZrrIndexType(map);

        return JSONObject.toJSONString(mapList, SerializerFeature.WriteMapNullValue);
    }

    @ApiOperation(value = "近期卫星报警情况统计图(完成by Rechel)", notes = "根据时间范围、用户atpid查询责任人首页近期卫星报警情况统计图信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, dataType = "String")
    })
    @PostMapping(value = "getBjFlfxCount", produces = "application/json;charset=UTF-8")
    public String getBjFlfxCount(
            @RequestParam(value = "startTime", defaultValue = "1900-01-01 00:00:00") String startTime,
            @RequestParam(value = "endTime", defaultValue = "2099-01-01 00:00:00") String endTime,
            HttpSession httpSession) {
        if (!Utils.checkLoginStatus(httpSession)) {
            return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

        }
        //具体过程
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");

        Map map = new HashMap();
        map.put("userId", ls.getSUser().getSuAtpid());
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        List<Map> mapList = iBalarmfxService.getBjFlfxCount(map);

        return JSONObject.toJSONString(mapList, SerializerFeature.WriteMapNullValue);
    }


}
