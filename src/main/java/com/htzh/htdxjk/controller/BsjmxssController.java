package com.htzh.htdxjk.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.*;
import com.htzh.htdxjk.service.IBsjmxssService;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"三级门限实时报警信息表"})
@RequestMapping("/api/bsjmxss")
public class BsjmxssController {


    @Autowired
    private IBsjmxssService iBsjmxssService;

    @Autowired
    private IBdxznzdxtssbjService iBdxznzdxtssbjService;

    @Autowired
    private IBsjmxssService bsjmxssService;

    @Autowired
    private IBsjmxysService bsjmxysService;

    @Autowired
    private IBdxznzdxtssbjService bdxznzdxtssbjService;

    @Autowired
    private IBzbjlService iBzbjlService;

    @Autowired
    private IBzbswService bzbswService;

    @Autowired
    private IBzbbwlService bzbbwlService;

    @Autowired
    private ISuserService suserService;

    @Autowired
    private IBzbbwlrelService bzbbwlrelService;


    /**
     * 查询
     */
    @ApiOperation(value = "报警信息列表展示（不分页）(完成)", notes = "报警信息列表展示（不分页）")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "alarmlevel", value = "报警级别 默认不显示轻度报警 选择显示轻度报警，传值 轻度", dataType = "String"),
            @ApiImplicitParam(name = "satIds", value = "型号ID,多个用英文逗号隔开", dataType = "String"),
            @ApiImplicitParam(name = "source", value = "来源(三级门限实时,多星智能诊断系统实时报警)", dataType = "String"),
            @ApiImplicitParam(name = "alarmStartTime", value = "报警开始时间,形式如YYYY-MM-DD", dataType = "String"),
            @ApiImplicitParam(name = "keyWords", value = "关键字（根据sort的值判断，多个用英文逗号隔开）", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页数据", dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段(型号bsatCode,来源alarmSource)", dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式 desc asc", dataType = "String")
    })
    @PostMapping(value = "listBsjmxss", produces = "application/json;charset=UTF-8")
    public String listBsjmxss(@RequestParam(value = "alarmlevel", defaultValue = "") String alarmlevel,
                              @RequestParam(value = "satIds", defaultValue = "") String satIds,
                              @RequestParam(value = "source", defaultValue = "") String source,
                              @RequestParam(value = "keyWords", defaultValue = "") String keyWords,
                              @RequestParam(value = "alarmStartTime", defaultValue = "") String alarmStartTime,
                              @RequestParam(value = "page", defaultValue = "0") String page,
                              @RequestParam(value = "rows", defaultValue = "0") String rows,
                              @RequestParam(value = "sort", defaultValue = "") String sort,
                              @RequestParam(value = "order", defaultValue = "") String order,HttpSession httpSession) {
        //检查权限
        if (!Utils.checkLoginStatus(httpSession)) {
            return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);


        }
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
        Map<String,Object> map = new HashMap<>();
        int offset = 0;
        if(Integer.parseInt(page) != 0 && Integer.parseInt(rows) != 0){
            offset = Integer.parseInt(page)*Integer.parseInt(rows) - Integer.parseInt(rows);
        }
        map.put("offset",offset);
        map.put("rows",Integer.parseInt(rows)==0?0:Integer.parseInt(rows));
        map.put("alarmlevel",alarmlevel);
        map.put("source",source);
        if(!StrUtil.hasEmpty(satIds)){
            String[] strArray = satIds.split(",");
            Set<String> staffsSet = new HashSet<>(Arrays.asList(strArray));
            List<String> ayyayList = new ArrayList<>(staffsSet);
            map.put("satIds",ayyayList);
        }
        if(!StrUtil.hasEmpty(keyWords)){
            String[] strArray = keyWords.split(",");
            Set<String> staffsSet = new HashSet<>(Arrays.asList(strArray));
            List<String> ayyayList = new ArrayList<>(staffsSet);
            map.put("keyWords",ayyayList);
        }
        map.put("alarmStartTime",alarmStartTime);
        map.put("sort",sort);
        map.put("order",order);
        map.put("userid",ls.getSUser().getSuAtpid());
        Map<String, Object> balarmfxByParma = iBsjmxssService.getBalarmfxByParma(map);
        return JSONObject.toJSONString(balarmfxByParma, SerializerFeature.WriteMapNullValue);
    }

    @ApiOperation(value = "通过时间戳增量返回报警信息(完成)", notes = "通过时间戳增量返回报警信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "alarmlevel", value = "报警级别 默认不显示轻度报警 选择显示轻度报警，传值 轻度", dataType = "String"),
            @ApiImplicitParam(name = "satIds", value = "型号ID,多个用英文逗号隔开", dataType = "String"),
            @ApiImplicitParam(name = "source", value = "来源(三级门限实时,多星智能诊断系统实时报警)", dataType = "String"),
            @ApiImplicitParam(name = "alarmStartTime", value = "报警开始时间,形式如YYYY-MM-DD", dataType = "String"),
            @ApiImplicitParam(name = "satId", value = "型号ID", dataType = "String"),
            @ApiImplicitParam(name = "timeStamp",value = "时间戳,格式YYYYmmddHHmmss",dataType = "String",required = true)
    })
    @PostMapping(value = "incrementReturnData", produces = "application/json;charset=UTF-8")
    public String incrementReturnData(@RequestParam(value = "alarmlevel", defaultValue = "") String alarmlevel,
                                       @RequestParam(value = "satIds", defaultValue = "") String satIds,
                                       @RequestParam(value = "source", defaultValue = "") String source,
                                       @RequestParam(value = "satId", defaultValue = "") String satId,
                                       @RequestParam(value = "alarmStartTime", defaultValue = "") String alarmStartTime,
                                       @RequestParam(value = "timeStamp", defaultValue = "",required = true) String timeStamp){


        try {
            Map<String, Object> map = iBsjmxssService.incrementReturnData(alarmlevel, satIds, source, satId, alarmStartTime, timeStamp);
            return JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            log.error(e.getMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS,ResultTo.FAIL_STATUS_MSG,null);
        }

    }
    /**
     * 增加
     */
    @ApiOperation(value = "三级门限实时报警添加", notes = "三级门限实时报警添加")
    @PostMapping(value = "addBsjmxss", produces = "application/json;charset=UTF-8")
    public String addBsjmxss(Bsjmxss inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBsjmxssAtpid(UUID.randomUUID().toString());
            inputEntity.setBsjmxssAtpcreatedatetime(Utils.getNow());
            inputEntity.setBsjmxssAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBsjmxssAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBsjmxssAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBsjmxssService.save(inputEntity);
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


    @PostMapping(value = "updateBsjmxss", produces = "application/json;charset=UTF-8")
    public String updateBsjmxss(Bsjmxss inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBsjmxssAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBsjmxssAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBsjmxssService.updateById(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }

    }


    @ApiOperation(value = "值班人报警报警确认预判功能(完成)", notes = "值班人报警报警确认预判功能")
    @PostMapping(value = "preJudgAlarm", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public String preJudgAlarm(PreJudgAlarm preJudgAlarm, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }

            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            String loginUserId = ls.getSUser().getSuAtpid();
            String loginUserName = ls.getSUser().getSuChinesename();
            String dutyId = null;
            Bzbsw bzbsw = new Bzbsw();
            //具体过程
            if(StrUtil.hasEmpty(preJudgAlarm.getSource())){
                return Utils.makeJSONResponseMsg(0, "请选择报警来源", null);
            }
            if(StrUtil.hasEmpty(preJudgAlarm.getAtpId())){
                return Utils.makeJSONResponseMsg(0, "请选择一条报警信息", null);
            }
            if (StrUtil.hasEmpty(preJudgAlarm.getSource())) {
                return Utils.makeJSONResponseMsg(0, "请选择报警来源", null);
            }
            if (StrUtil.hasEmpty(preJudgAlarm.getAtpId())) {
                return Utils.makeJSONResponseMsg(0, "请选择一条报警信息", null);
            }
            if ("是".equals(preJudgAlarm.getIsTrace())) {
                bzbsw.setBzbswSendto("下一班值班人");
                //下一班值班人 取消提示
              /*  nowDateBzbjl = iBzbjlService.findNextDuty(0);
                if (StringUtils.isEmpty(nowDateBzbjl)) {
                    return Utils.makeJSONResponseMsg(0, "下一班值班列表为空，请创建值班列表", null);
                }
                if (StrUtil.hasEmpty(nowDateBzbjl.getBzbjlPerson1()) && StrUtil.hasEmpty(nowDateBzbjl.getBzbjlPerson2())) {
                    return Utils.makeJSONResponseMsg(0, "值班列表没有值班人，请先创建值班人", null);
                }*/
                //值班备忘录
                Bzbbwl bzbbwl = new Bzbbwl();
                final String bwlId = UUID.randomUUID().toString();
                bzbbwl.setBzbbwlAtpid(bwlId);
                bzbbwl.setBzbbwlAddperson(loginUserId);
                bzbbwl.setBzbbwlAtplastmodifydatetime(Utils.getNow());
                bzbbwl.setBzbbwlAtplastmodifyuser(loginUserName);
                bzbbwl.setBzbbwlAddtime(Utils.getNow());
                bzbbwl.setBzbbwlAtpcreateuser(loginUserName);
                bzbbwl.setBzbbwlAtpcreatedatetime(Utils.getNow());
                bzbbwl.setBzbbwlSatcode(preJudgAlarm.getBsatCode());
                bzbbwl.setBzbbwlSatid(preJudgAlarm.getSatId());
                bzbbwl.setBzbbwlShowdays("1");
                Date date = DateUtil.tomorrow();
                String showDate = DateUtil.format(date, "yyyy-MM-dd 08:30:00");
                bzbbwl.setBzbbwlShowdate(showDate);
                bzbbwl.setBzbbwlConfirm("未确认");
                bzbbwl.setBzbbwlState("open");
                bzbbwlService.save(bzbbwl);
                /*List<Bzbbwlrel> bzbbwlrelArrayList = new ArrayList<>();
                if (!StrUtil.hasEmpty(nowDateBzbjl.getBzbjlPerson1())) {
                    Suser byId = suserService.getById(nowDateBzbjl.getBzbjlPerson1());
                    nextDutyName1 = byId.getSuChinesename();
                    Bzbbwlrel bzbbwlrel = new Bzbbwlrel();
                    bzbbwlrel.setBzbbwlrelUserId(nowDateBzbjl.getBzbjlPerson1());
                    bzbbwlrelArrayList.add(bzbbwlrel);
                }
                if (!StrUtil.hasEmpty(nowDateBzbjl.getBzbjlPerson2())) {
                    Suser byId = suserService.getById(nowDateBzbjl.getBzbjlPerson2());
                    nextDutyName2 = byId.getSuChinesename();
                    Bzbbwlrel bzbbwlrel = new Bzbbwlrel();
                    bzbbwlrel.setBzbbwlrelUserId(nowDateBzbjl.getBzbjlPerson2());
                    bzbbwlrelArrayList.add(bzbbwlrel);
                }
                for (Bzbbwlrel bzbbwlrel : bzbbwlrelArrayList) {
                    bzbbwlrel.setBzbbwlrelBjlId(nowDateBzbjl.getBzbjlAtpid());
                    bzbbwlrel.setBzbbwlrelBwlId(bwlId);
                    bzbbwlrel.setBzbbwlrelAtplastmodifyuser(loginUserName);
                    bzbbwlrel.setBzbbwlrelAtplastmodifydatetime(Utils.getNow());
                    bzbbwlrel.setBzbbwlrelAtpid(UUID.randomUUID().toString());
                    bzbbwlrel.setBzbbwlrelAtpcreatedatetime(Utils.getNow());
                    bzbbwlrel.setBzbbwlrelAtpcreateuser(loginUserName);
                }
                bzbbwlrelService.saveBatch(bzbbwlrelArrayList);
                */
            } else {
                Bzbjl nowDateBzbjl = iBzbjlService.findNextDuty(1);
                if(nowDateBzbjl != null){
                    dutyId = nowDateBzbjl.getBzbjlAtpid();
                }
            }
            if ("三级门限实时报警".equals(preJudgAlarm.getSource())) {
                Bsjmxss byId = bsjmxssService.getById(preJudgAlarm.getAtpId());
                byId.setBsjmxssPreJudgeRemark(preJudgAlarm.getPreJudgeRemark());
                byId.setBsjmxssPreJudgeTime(preJudgAlarm.getPreJudgeTime());
                byId.setBsjmxssPreJudgeType(preJudgAlarm.getPreJudgeType());
                byId.setBsjmxssAtplastmodifydatetime(Utils.getNow());
                byId.setBsjmxssAtplastmodifyuser(loginUserId);
                bsjmxssService.updateById(byId);
            } else if ("多星智能诊断系统实时报警".equals(preJudgAlarm.getSource())) {
                Bdxznzdxtssbj byId = bdxznzdxtssbjService.getById(preJudgAlarm.getAtpId());
                byId.setBdxznzdxtssbjPreJudgeRemark(preJudgAlarm.getPreJudgeRemark());
                byId.setBdxznzdxtssbjPreJudgeTime(preJudgAlarm.getPreJudgeTime());
                byId.setBdxznzdxtssbjPreJudgeType(preJudgAlarm.getPreJudgeType());
                byId.setBdxznzdxtssbjAtplastmodifydatetime(Utils.getNow());
                byId.setBdxznzdxtssbjAtplastmodifyuser(loginUserId);
                bdxznzdxtssbjService.updateById(byId);
            } else {
                //后续再加报警来源
            }

            bzbsw.setBzbswAtpid(UUID.randomUUID().toString());
            bzbsw.setBzbswAtplastmodifydatetime(Utils.getNow());
            bzbsw.setBzbswAtpcreatedatetime(Utils.getNow());
            bzbsw.setBzbswAtplastmodifyuser(loginUserName);
            bzbsw.setBzbswAtpcreateuser(loginUserName);
            bzbsw.setBzbswAddperson(loginUserId);
            bzbsw.setBzbswInfoid(dutyId);
            bzbsw.setBzbswSatid(preJudgAlarm.getSatId());
            bzbsw.setBzbswSatcode(preJudgAlarm.getBsatCode());
            bzbsw.setBzbswAddtime(Utils.getNow());
            bzbsw.setBzbswInfotype(preJudgAlarm.getPreJudgeType());
            bzbsw.setBzbswPhemdesc(preJudgAlarm.getPreJudgeRemark());
            bzbswService.save(bzbsw);
            return Utils.makeJSONResponseMsg(1, "预判成功", null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "预判失败", null);
        }
    }



    /**
     * 删除
     */
    @PostMapping(value = "removeBsjmxss", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "bsjmxssAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBsjmxss(@RequestParam("bsjmxssAtpid") String bsjmxssAtpid) {
        try {
            String[] idsArray = bsjmxssAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBsjmxssService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "bsjmxss_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bsjmxss obj = new Bsjmxss();
        //筛选字段list
        List list = new ArrayList();

        list.add("bsjmxss_satid");
        list.add("bsjmxss_begintime");
        list.add("bsjmxss_endtime");
        list.add("bsjmxss_alarmlevel");
        list.add("bsjmxss_alarmmsg");
        list.add("bsjmxss_responetime");
        list.add("bsjmxss_username");
        list.add("bsjmxss_response");
        list.add("bsjmxss_alarmvalue");
        list.add("bsjmxss_tmid");
        list.add("bsjmxss_tmcode");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBsjmxssService);
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
    @ApiOperation(value = "根据atpid获取报警分类分析信息", notes = "根据atpid获取报警分类分析信息")
    @PostMapping(value = "/getOneById", produces = "application/json;charset=UTF-8")
    public String getOneById(@RequestParam(value = "atpId", defaultValue = "") String atpId) {
        try {
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBsjmxssService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    @ApiOperation(value = "根据型号ID（26）信息列表(完成)", notes = "根据型号ID（26）信息列表")
    @ApiImplicitParam(name = "satId", value = "型号ID（26）", required = true, dataType = "String")
    @PostMapping(value = "getBsjmxssBySatId", produces = "application/json;charset=UTF-8")
    public String getBsjmxssBySatId(@RequestParam("satId") String satId) {
        Map<String, Object> bsjmxssBySatId = iBsjmxssService.getBsjmxssBySatId(satId);

        return Utils.makeJSONResponseMsg(1, "查询成功", bsjmxssBySatId);
    }

    @ApiOperation(value = "报警确认(完成)", notes = "报警确认")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "atpId", value = "主键", required = true, dataType = "String"),
            @ApiImplicitParam(name = "soure", value = "来源（三级门限实时报警、多星智能诊断系统实时报警）", defaultValue = "三级门限实时", dataType = "String")
    })

    @GetMapping(value = "confirmBsjmxss", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public String confirmBsjmxss(@RequestParam("atpId") String atpId,
                                 @RequestParam(value = "soure", defaultValue = "三级门限实时报警") String soure,
                                 HttpSession httpSession) {
        if (!Utils.checkLoginStatus(httpSession)) {
            return Utils.makeJSONResponseMsg(0, "未登陆，请登陆", null);
        }
        //具体过程
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
        String userId = ls.getSUser().getSuAtpid();
        String loginUserAccount = ls.getSUser().getSuAccount();
        String sjmxssbj="三级门限实时报警";
        String dxznzdxtssbj="多星智能诊断系统实时报警";
        if (sjmxssbj.equals(soure)) {
            Bsjmxss bsjmxss = iBsjmxssService.getById(atpId);
            if (bsjmxss == null) {
                return Utils.makeJSONResponseMsg(0, "该对象不存在", null);
            }
            bsjmxss.setBsjmxssAtplastmodifydatetime(Utils.getNow());
            bsjmxss.setBsjmxssAtplastmodifyuser(loginUserAccount);
            bsjmxss.setBsjmxssResponetime(Utils.getNow());
            bsjmxss.setBsjmxssUsername(userId);
            iBsjmxssService.updateById(bsjmxss);
        } else if (dxznzdxtssbj.equals(soure)) {
            Bdxznzdxtssbj bdxznzdxtssbj = iBdxznzdxtssbjService.getById(atpId);
            if (bdxznzdxtssbj == null) {
                return Utils.makeJSONResponseMsg(0, "该对象不存在", null);
            }
            bdxznzdxtssbj.setBdxznzdxtssbjAtplastmodifydatetime(Utils.getNow());
            bdxznzdxtssbj.setBdxznzdxtssbjAtplastmodifyuser(loginUserAccount);
            bdxznzdxtssbj.setBdxznzdxtssbjRecordTime(Utils.getNow());
            bdxznzdxtssbj.setBdxznzdxtssbjConfirmUser(userId);
            iBdxznzdxtssbjService.updateById(bdxznzdxtssbj);
        } else {
            //后续再加判断其他的报警来源
        }

        return Utils.makeJSONResponseMsg(1, "报警确认成功", null);

    }

    /**
     * 导出excel
     *
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "导出excel(完成)", notes = "导出excel")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "alarmlevel", value = "报警级别 默认不显示轻度报警 选择显示轻度报警，传值 轻度", dataType = "String"),
            @ApiImplicitParam(name = "satIds", value = "型号集ID,多个用英文逗号隔开", dataType = "String"),
            @ApiImplicitParam(name = "source", value = "来源(三级门限实时,多星智能诊断系统实时报警)", dataType = "String"),
            @ApiImplicitParam(name = "alarmStartTime", value = "报警开始时间,形式如YYYY-MM-DD", dataType = "String"),
            @ApiImplicitParam(name = "keyWords", value = "关键字（根据sort的值判断，多个用英文逗号隔开）", dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段(型号bsatCode,来源alarmSource)", dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式 desc asc", dataType = "String")
    })
    @GetMapping(value = "exportExcel", produces = "application/json;charset=UTF-8")
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(value = "alarmlevel", defaultValue = "") String alarmlevel,
                            @RequestParam(value = "satIds", defaultValue = "") String satIds,
                            @RequestParam(value = "source", defaultValue = "") String source,
                            @RequestParam(value = "keyWords", defaultValue = "") String keyWords,
                            @RequestParam(value = "alarmStartTime", defaultValue = "") String alarmStartTime,
                            @RequestParam(value = "sort", defaultValue = "") String sort,
                            @RequestParam(value = "order", defaultValue = "") String order) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("报警信息表");

        Bsjmxss obj = new Bsjmxss();
        Map<String,Object> map = new HashMap<>();
        int offset = 0;
        map.put("offset",offset);
        map.put("rows",0);
        map.put("alarmlevel",alarmlevel);
        map.put("source",source);
        if(!StrUtil.hasEmpty(satIds)){
            String[] strArray = satIds.split(",");
            Set<String> staffsSet = new HashSet<>(Arrays.asList(strArray));
            List<String> ayyayList = new ArrayList<>(staffsSet);
            map.put("satIds",ayyayList);
        }
        map.put("keyWords",keyWords);
        map.put("alarmStartTime",alarmStartTime);
        map.put("sort",sort);
        map.put("order",order);

        Map<String, Object> balarmfxByParma = iBsjmxssService.getBalarmfxByParma(map);
        List<AlarmModel> rows = (List<AlarmModel>) balarmfxByParma.get("rows");
        //设置要导出的文件的名字
        String fileName = "alarm" + Utils.getNow() + ".xls";
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"序号", "型号代号", "报警开始时间", "参数级别", "报警级别", "当前值", "报警值", "报警信息", "报警来源", "确认时间"};

        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (AlarmModel alarmModel : rows) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(rowNum);
            row1.createCell(1).setCellValue(alarmModel.getBsatCode());
            row1.createCell(2).setCellValue(alarmModel.getAlarmBeginTime());
            row1.createCell(3).setCellValue(alarmModel.getParamCode());
            row1.createCell(4).setCellValue(alarmModel.getAlarmLevel());
            row1.createCell(5).setCellValue(alarmModel.getCurrentVal());
            row1.createCell(6).setCellValue(alarmModel.getAlarmVal());
            row1.createCell(7).setCellValue(alarmModel.getAlarmMsg());
            row1.createCell(8).setCellValue(alarmModel.getAlarmSource());
            row1.createCell(9).setCellValue(alarmModel.getConfirmTime());
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    @ApiOperation(value = "根据报警ID和来源查询报警信息", notes = "根据报警ID和来源查询报警信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "alarmId", value = "报警ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "alarmSourc", value = "报警来源", required = true, dataType = "String")
    })
    @PostMapping(value = "getByAlarmIdAndSource", produces = "application/json;charset=UTF-8")
    public String getByAlarmIdAndSource(@RequestParam(value = "alarmId",required = true) String alarmId,
                                        @RequestParam(value = "alarmSourc",required = true) String alarmSourc,
                                        HttpSession httpSession) {
        try {
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
            }
            AlarmInfoModel byAlarmIdAndSource = bsjmxssService.getByAlarmIdAndSource(alarmId, alarmSourc);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS,ResultTo.SUCCESS_STATUS_MSG,byAlarmIdAndSource);
        }catch (Exception e){
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS,ResultTo.FAIL_STATUS_MSG,null);
        }
    }




}
