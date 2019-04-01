package com.htzh.htdxjk.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.*;
import com.htzh.htdxjk.utils.EzuiUtil;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 * 值班记录
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"值班记录"})
@RequestMapping("/api/bzbsw")
public class BzbswController {


    @Autowired
    private IBzbswService iBzbswService;

    @Autowired
    private IBzbjlService bzbjlService;

    @Autowired
    private IBzbbwlService bzbbwlService;

    @Autowired
    private IBzbbwlrelService bzbbwlrelService;

    @Autowired
    private IBckplanService bckplanService;

    @Autowired
    private IBzgycService bzgycService;

    @Autowired
    private ISuserService suserService;


    @ApiOperation(value = "值班记录", notes = "查询")
    @PostMapping(value = "listBzbsw", produces = "application/json;charset=UTF-8")
    public String listBzbsw() {
        List map = iBzbswService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**
     * 增加值班记录
     */

    @ApiOperation(value = "增加值班记录(值班人首页值班列表里添加记录专用,完成)", notes = "增加值班记录(值班人首页值班列表里添加记录专用)")
    @PostMapping(value = "addBzbsw", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public String addBzbsw(Bzbsw inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            String loginUserId = ls.getSUser().getSuAtpid();
            String loginUserChinessName = ls.getSUser().getSuChinesename();
            /*String nextDutyName1 = null;
            String nextDutyName2 = null;
            String dutyId = null;*/
            //先判断值班人，现在不加责任人
            if ("是".equals(inputEntity.getIsTrace())) {
               /* nextDuty = bzbjlService.findNextDuty(0);
                if (StringUtils.isEmpty(nextDuty)) {
                    return Utils.makeJSONResponseMsg(0, "没有下一班值班列表，请先创建", null);
                }
                if (StrUtil.hasEmpty(nextDuty.getBzbjlPerson1()) && StrUtil.hasEmpty(nextDuty.getBzbjlPerson2())) {
                    return Utils.makeJSONResponseMsg(0, "下一班值班人为空,请求添加值班人", null);
                }*/
                Bzbbwl bzbbwl = new Bzbbwl();
                final String bwlId = UUID.randomUUID().toString();
                bzbbwl.setBzbbwlSatid(inputEntity.getBzbswSatid());
                bzbbwl.setBzbbwlShowdays("1");
                bzbbwl.setBzbbwlSatcode(inputEntity.getBzbswSatcode());
                bzbbwl.setBzbbwlAtpcreatedatetime(Utils.getNow());
                bzbbwl.setBzbbwlAtpcreateuser(loginUserChinessName);
                bzbbwl.setBzbbwlAddtime(Utils.getNow());
                bzbbwl.setBzbbwlAddperson(loginUserId);
                bzbbwl.setBzbbwlAtplastmodifyuser(loginUserChinessName);
                bzbbwl.setBzbbwlAtplastmodifydatetime(Utils.getNow());
                bzbbwl.setBzbbwlAtpid(bwlId);
               /* String today= DateUtil.today();//当前日期字符串，格式：yyyy-MM-dd*/
                Date date = DateUtil.tomorrow();
                String showDate = DateUtil.format(date, "yyyy-MM-dd 08:30:00");
                bzbbwl.setBzbbwlShowdate(showDate);
                bzbbwl.setBzbbwlConfirm("未确认");
                bzbbwl.setBzbbwlState("open");
                bzbbwl.setBzbbwlShowtitle(inputEntity.getBzbswPhemdesc());
                bzbbwl.setBzbbwlShowdetail(inputEntity.getBzbswResltdesc());
                bzbbwlService.save(bzbbwl);
                inputEntity.setBzbswSendto("下一班值班人");
               /* List<Bzbbwlrel> bzbbwlrelArrayList = new ArrayList<>();
                if (!StrUtil.hasEmpty(nextDuty.getBzbjlPerson1())) {
                    Suser byId = suserService.getById(nextDuty.getBzbjlPerson1());
                    nextDutyName1 = byId.getSuChinesename();
                    Bzbbwlrel bzbbwlrel = new Bzbbwlrel();
                    bzbbwlrel.setBzbbwlrelUserId(nextDuty.getBzbjlPerson1());
                    bzbbwlrelArrayList.add(bzbbwlrel);
                }
                if (!StrUtil.hasEmpty(nextDuty.getBzbjlPerson2())) {
                    Suser byId = suserService.getById(nextDuty.getBzbjlPerson2());
                    nextDutyName2 = byId.getSuChinesename();
                    Bzbbwlrel bzbbwlrel = new Bzbbwlrel();
                    bzbbwlrel.setBzbbwlrelUserId(nextDuty.getBzbjlPerson2());
                    bzbbwlrelArrayList.add(bzbbwlrel);
                }
                for (Bzbbwlrel bzbbwlrel : bzbbwlrelArrayList) {
                    bzbbwlrel.setBzbbwlrelAtpcreatedatetime(Utils.getNow());
                    bzbbwlrel.setBzbbwlrelAtpcreateuser(loginUserChinessName);
                    bzbbwlrel.setBzbbwlrelAtpcreatedatetime(Utils.getNow());
                    bzbbwlrel.setBzbbwlrelAtpid(UUID.randomUUID().toString());
                    bzbbwlrel.setBzbbwlrelAtplastmodifydatetime(Utils.getNow());
                    bzbbwlrel.setBzbbwlrelAtplastmodifyuser(loginUserChinessName);
                    bzbbwlrel.setBzbbwlrelBwlId(bwlId);
                    bzbbwlrel.setBzbbwlrelBjlId(inputEntity.getBzbswInfoid());
                }
<<<<<<< .mine
                bzbbwlrelService.saveBatch(bzbbwlrelArrayList);*/
            }/*else {
                *//*Bzbjl nextDuty = bzbjlService.findNextDuty(1);
                dutyId = nextDuty.getBzbjlAtpid();

||||||| .r725
                bzbbwlrelService.saveBatch(bzbbwlrelArrayList);
            }else {
                nextDuty = bzbjlService.findNextDuty(1);
=======
                bzbbwlrelService.saveBatch(bzbbwlrelArrayList);
            } else {
                nextDuty = bzbjlService.findNextDuty(1);
>>>>>>> .r735
                if (StringUtils.isEmpty(nextDuty)) {
                    return Utils.makeJSONResponseMsg(0, "今日没有值班列表，请先创建", null);
                }
                if (StrUtil.hasEmpty(nextDuty.getBzbjlPerson1()) && StrUtil.hasEmpty(nextDuty.getBzbjlPerson2())) {
                    return Utils.makeJSONResponseMsg(0, "今日值班人为空,请先添加值班人", null);
                }*//*
            }*/

            inputEntity.setBzbswAtpid(UUID.randomUUID().toString());
            inputEntity.setBzbswAtpcreatedatetime(Utils.getNow());
            inputEntity.setBzbswAtpcreateuser(loginUserChinessName);
            inputEntity.setBzbswAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBzbswAtplastmodifyuser(loginUserChinessName);
            inputEntity.setBzbswAddperson(loginUserId);
            inputEntity.setBzbswAddtime(Utils.getNow());
            inputEntity.setBzbswModifytime(Utils.getNow());
            iBzbswService.save(inputEntity);
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
    @ApiOperation(value = "修改", notes = "修改")
    @PostMapping(value = "updateBzbsw", produces = "application/json;charset=UTF-8")
    public String updateBzbsw(Bzbsw inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBzbswAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBzbswAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBzbswService.updateById(inputEntity);
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
    @PostMapping(value = "removeBzbsw", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "bzbswAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBzbsw(@RequestParam("bzbswAtpid") String bzbswAtpid) {
        try {
            String[] idsArray = bzbswAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBzbswService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "bzbsw_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bzbsw obj = new Bzbsw();
        //筛选字段list
        List list = new ArrayList();

        list.add("bzbsw_detailid");
        list.add("bzbsw_infoid");
        list.add("bzbsw_satcode");
        list.add("bzbsw_infotype");
        list.add("bzbsw_phemdesc");
        list.add("bzbsw_resltdesc");
        list.add("bzbsw_addtime");
        list.add("bzbsw_addperson");
        list.add("bzbsw_modifytime");
        list.add("bzbsw_state");
        list.add("bzbsw_sendto");
        list.add("bzbsw_serialnum");
        list.add("bzbsw_shownotifyflag");
        list.add("bzbsw_phemtime");
        list.add("bzbsw_reslttime");
        list.add("bzbsw_createbykettle");
        list.add("bzbsw_idinnorbit");

        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBzbswService);
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
    @ApiOperation(value = "根据值班列表的atpid获取信息(完成)", notes = "根据atpid获取信息")

    @PostMapping(value = "/getByAtpId", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "atpId", value = "值班列表的atpId", required = true, dataType = "String"),
            @ApiImplicitParam(name = "keyWord", value = "关键字", dataType = "String"),
            @ApiImplicitParam(name = "satcode", value = "卫星型号Id，多个用逗号隔开", required = false, dataType = "String"),
            @ApiImplicitParam(name = "infotype", value = "类型", dataType = "String"),
            @ApiImplicitParam(name = "phemdesc", value = "现象", dataType = "String"),
            @ApiImplicitParam(name = "resltdesc", value = "处理结果", dataType = "String"),
            @ApiImplicitParam(name = "addPerson", value = "添加人", dataType = "String"),
            @ApiImplicitParam(name = "state", value = "状态", dataType = "String"),
            @ApiImplicitParam(name = "sendto", value = "发送给谁", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页数据", dataType = "String")
    })
    public String getByAtpId(@RequestParam(value = "atpId", defaultValue = "", required = true) String atpId,
                             @RequestParam(value = "keyWord", defaultValue = "", required = false) String keyWord,
                             @RequestParam(value = "satcode", defaultValue = "") String satcode,
                             @RequestParam(value = "infotype", defaultValue = "", required = false) String infotype,
                             @RequestParam(value = "phemdesc", defaultValue = "", required = false) String phemdesc,
                             @RequestParam(value = "resltdesc", defaultValue = "", required = false) String resltdesc,
                             @RequestParam(value = "addPerson", defaultValue = "", required = false) String addPerson,
                             @RequestParam(value = "state", defaultValue = "", required = false) String state,
                             @RequestParam(value = "sendto", defaultValue = "", required = false) String sendto,
                             @RequestParam(value = "page", defaultValue = "1") String page,
                             @RequestParam(value = "rows", defaultValue = "10") String rows) {
        try {
            int offset = Integer.parseInt(page) * Integer.parseInt(rows) - Integer.parseInt(rows);
            Map<String, Object> map = new HashMap<>();
            List<String> arrList = new ArrayList<>();
            if (!StrUtil.hasEmpty(satcode)) {
                java.lang.String[] isStr = satcode.split(",");
                arrList = new ArrayList<>(Arrays.asList(isStr));
            }
            map.put("atpId", atpId);
            map.put("keyWord", keyWord);
            map.put("infotype", infotype);
            map.put("phemdesc", phemdesc);
            map.put("resltdesc", resltdesc);
            map.put("addPerson", addPerson);
            map.put("state", state);
            map.put("sendto", sendto);
            map.put("page", page);
            map.put("offset", offset);
            map.put("rows", Integer.parseInt(rows));
            map.put("arrList", arrList);
            Map<String, Object> resultMap = iBzbswService.listByAtpId(map);
            return JSON.toJSONString(resultMap, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 根据atpid获取一条信息
     *
     * @param atpId
     * @return
     */
    @ApiOperation(value = "根据atpid获取信息(完成)", notes = "根据atpid获取信息")

    @PostMapping(value = "/getOneById", produces = "application/json;charset=UTF-8")
    public String getOneById(@RequestParam(value = "atpId", defaultValue = "") String atpId) {
        try {
            return Utils.makeJSONResponseMsg(1, "成功", iBzbswService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

}
