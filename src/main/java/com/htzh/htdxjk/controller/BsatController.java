package com.htzh.htdxjk.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.IBsatService;
import com.htzh.htdxjk.service.IBsatTopService;
import com.htzh.htdxjk.service.IBxxrjlService;
import com.htzh.htdxjk.service.ISuserroleService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Struct;
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
@Api(value = "user-api", tags = {"航天器基本信息"})
@RequestMapping("/api/bsat/")
public class BsatController {


    @Autowired
    private IBsatService iBsatService;

    @Autowired
    private IBsatTopService iBsatTopService;

    @Autowired
    private ISuserroleService iSuserroleService;

    @Autowired
    private  IBxxrjlService iBxxrjlService;


    @Value("${role.adminId}")
    private String roleAdminId;


    private static String IS_ADMIN = "是";

    private static String IS_NO_ADMIN = "否";


    /**
     * 卫星类型：所有、传输遥感卫星，通信卫星，导航卫星，小卫星
     *
     * @return
     */
    @ApiOperation(value = "所有卫星详情列表", notes = "需要关联用户表责任人、替代人的电话信息，关联详情htdxjk_bxhzcdw、htdxjk_bzgwxxhfzlxr")
    @PostMapping(value = "listBsatInfoToList", produces = "application/json;charset=UTF-8")
    public String listBsatInfoToList(@RequestParam("satellType") String satellType) {
        List map = iBsatService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }


    /**
     * 所有卫星分类数量统计 ：全部卫星数量，传输遥感卫星（占比，数量），通信卫星（占比，数量），导航卫星（占比，数量），小卫星（占比，数量）
     *
     * @return
     */
    @ApiOperation(value = "所有卫星分类数量统计(done)", notes = "全部卫星数量，传输遥感卫星（占比，数量），通信卫星（占比，数量），导航卫星（占比，数量），小卫星（占比，数量）")
    @PostMapping(value = "countEverySatellite", produces = "application/json;charset=UTF-8")
    public String countEverySatellite() {

        try {
            int countAll = iBsatService.count();
            Map<String, Object> map = new HashMap<>();
            map.put("type", "全部卫星");
            map.put("count", countAll);
            List<Map<String, Object>> result = iBsatService.countBsat();
            result.add(map);

            return Utils.makeJSONResponseMsg(1, "获取成功", result);

        } catch (Exception e) {

            e.printStackTrace();
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);

        }

    }


    @ApiOperation(value = "统计卫星是否超寿(done)", notes = "统计卫星是否超寿")
    @PostMapping(value = "countIsCSSatellite", produces = "application/json;charset=UTF-8")
    public String countIsCSSatellite() {

        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.apply("TIMESTAMPDIFF(DAY, htdxjk_bsat.bsat_launch_time, CURDATE())>htdxjk_bsat.bsat_lifeyear*365");
        int countCS1 = iBsatService.count(queryWrapper1);

        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper2.apply("TIMESTAMPDIFF(DAY, htdxjk_bsat.bsat_launch_time, CURDATE())=htdxjk_bsat.bsat_lifeyear*365");
        int countCS2 = iBsatService.count(queryWrapper2);

        QueryWrapper queryWrapper3 = new QueryWrapper();
        queryWrapper3.apply("TIMESTAMPDIFF(DAY, htdxjk_bsat.bsat_launch_time, CURDATE())-htdxjk_bsat.bsat_lifeyear*365=1");
        int countCS3 = iBsatService.count(queryWrapper3);

        QueryWrapper queryWrapper4 = new QueryWrapper();
        queryWrapper4.apply("htdxjk_bsat.bsat_launch_time<>''\n" +
                "AND htdxjk_bsat.bsat_launch_time IS NOT NULL");
        int countCS4 = iBsatService.count(queryWrapper4);

        HashMap hashMap = new HashMap();
        hashMap.put("countYCSWX", countCS1);
        hashMap.put("countMRJCS", countCS2);
        hashMap.put("countJRGGCS", countCS3);
        hashMap.put("countZGWXSL", countCS4);

        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, hashMap);
    }


    /**
     * 增加
     */
    @ApiOperation(value = "增加航天器(done)", notes = "增加航天器")
    @PostMapping(value = "addBsat", produces = "application/json;charset=UTF-8")
    public String addBsat(Bsat inputEntity,String zrrAtpid,String tdrid, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBsatAtpid(UUID.randomUUID().toString());
            inputEntity.setBsatAtpcreatedatetime(Utils.getNow());
            inputEntity.setBsatAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBsatAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBsatAtplastmodifyuser(ls.getSUser().getSuAccount());
            inputEntity.setBsatAdminSubsId(zrrAtpid);
            inputEntity.setBsatAdminId(tdrid);

            iBsatService.save(inputEntity);
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
    @ApiOperation(value = "修改航天器（done）", notes = "修改航天器")
    @PostMapping(value = "updateBsat", produces = "application/json;charset=UTF-8")
    public String updateBsat(Bsat inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            if (org.springframework.util.StringUtils.isEmpty(inputEntity.getBsatAtpid())) {
                return Utils.makeJSONResponseMsg(0, "修改主键为空", null);
            }
            if (iBsatService.getById(inputEntity.getBsatAtpid()) == null) {
                return Utils.makeJSONResponseMsg(0, "修改对象不存在", null);
            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBsatAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBsatAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBsatService.updateById(inputEntity);
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
    @PostMapping(value = "removeBsat", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除(done)", notes = "批量删除")
    @ApiImplicitParam(name = "bsatAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBsat(@RequestParam("bsatAtpid") String bsatAtpid) {
        try {
            String[] idsArray = bsatAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBsatService.removeByIds(arrayList);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    /**
     * 置顶
     *
     * @param atpIds
     * @param httpSession
     * @return
     */
    @PostMapping(value = "topBsat", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids置顶卫星(done)", notes = "置顶卫星")
    @ApiImplicitParam(name = "atpIds", value = "卫星ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String topBsat(@RequestParam("atpIds") String atpIds, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            //验证登陆用户角色
            List<String> stringByUserId = iSuserroleService.getUserRoleIdStringByUserId(ls.getSUser().getSuAtpid());
            String[] idsArray = atpIds.split(",");
            List<BsatTop> bsatTopList = new ArrayList<>();
            for (String str : idsArray) {
                BsatTop bsatTop = new BsatTop();
                bsatTop.setBstAtpid(UUID.randomUUID().toString());
                bsatTop.setBstAtpcreatedatetime(Utils.getNow());
                bsatTop.setBstAtpcreateuser(ls.getSUser().getSuAccount());
                bsatTop.setBstAtplastmodifydatetime(Utils.getNow());
                bsatTop.setBstAtplastmodifyuser(ls.getSUser().getSuAccount());
                bsatTop.setBstFkbsatid(str);
                bsatTop.setBstFksuserid(ls.getSUser().getSuAtpid());
                bsatTop.setBstSeq(0);
                if (stringByUserId.contains(roleAdminId)) {
                    bsatTop.setBstAdmin(IS_ADMIN);
                } else {
                    bsatTop.setBstAdmin(IS_NO_ADMIN);
                }
                bsatTopList.add(bsatTop);
            }
            boolean b = iBsatTopService.saveBatch(bsatTopList);
            if (b) {
                return Utils.makeJSONResponseMsg(1, "所选卫星置顶成功", b);
            }
            return Utils.makeJSONResponseMsg(0, "所选卫星置顶失败", b);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "置顶失败", null);
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
                                  @RequestParam(value = "sort", defaultValue = "bsat_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bsat obj = new Bsat();
        //筛选字段list
        List list = new ArrayList();

        list.add("bsat_id");
        list.add("bsat_last_modifed_time");
        list.add("bsat_active");
        list.add("bsat_code");
        list.add("bsat_endoflife_time ");
        list.add("bsat_full_name");
        list.add("bsat_launch_time");
        list.add("bsat_lifeyear");
        list.add("bsat_name");
        list.add("bsat_outside_name");
        list.add("bsat_admin_subs_id");
        list.add("bsat_admin_id");
        list.add("bsat_remark");
        list.add("bsat_platform_id");
        list.add("bsat_domain");
        list.add("bsat_ftpaccount");
        list.add("bsat_ftppasswod");
        list.add("bsat_ftpport");
        list.add("bsat_ftppath");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBsatService);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        return JSON.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");


    }


    /**
     * 导入excel
     *
     * @param httpSession
     * @param file
     * @return
     */
    @ApiOperation(value = "导入excel（完成）", notes = "导入excel")
    @PostMapping(value = "/importExcel", produces = "application/json;charset=UTF-8")
    public String importEmp(HttpSession httpSession, MultipartFile file) {
        return null;
    }

    /**
     * 根据atpid获取一条信息
     *
     * @param atpId
     * @return
     */
    @ApiOperation(value = "根据atpid获取一条信息(完成 by Rechel)", notes = "根据atpid获取一条信息")
    @PostMapping(value = "/getOneById", produces = "application/json;charset=UTF-8")
    public String getOneById(@RequestParam(value = "atpId", defaultValue = "") String atpId) {
        try {
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBsatService.getById(atpId));
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
    @ApiOperation(value = "根据atpid获取一条带关联的信息(完成 by Rechel)", notes = "根据atpid获取一条信息")
    @PostMapping(value = "/getOneBsatById", produces = "application/json;charset=UTF-8")
    public String getOneBsatById(@RequestParam(value = "atpId", defaultValue = "") String atpId) {
        try {
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBsatService.selectOneSatInfoById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    /**
     * 根据satId获取一条信息
     *
     * @param satId
     * @return
     */
    @ApiOperation(value = "根据atpId获取一条带关联的信息(确认报警功能使用)（完成）", notes = "根据atpId获取一条带关联的信息(确认报警功能使用)")
    @ApiImplicitParam(name = "satId", value = "卫星ID", required = true)
    @PostMapping(value = "/getOneBsatBySatId", produces = "application/json;charset=UTF-8")
    public String getOneBsatBySatId(@RequestParam(value = "satId", required = true) String satId) {
        try {
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBsatService.getOneBySatId(satId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    /**
     * 获取所有卫星型号id
     */

    @ApiOperation(value = "获取所有卫星型号代号（done）", notes = "获取所有卫星型号代号")
    @ApiImplicitParam(name = "bxxrjlId", value = "所属领域的atpid", required = false, dataType = "String")
    @PostMapping(value = "/getAllSatIds", produces = "application/json;charset=UTF-8")
    public String getAllSatIds(@RequestParam(value = "bxxrjlId", defaultValue = "") String bxxrjlId) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer stringBuffer1 = new StringBuffer();

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.select("bsat_atpid,bsat_code");
            if (!StrUtil.hasEmpty(bxxrjlId) && !StrUtil.hasBlank(bxxrjlId)) {
                queryWrapper.eq("bsat_bxxrjl_id", bxxrjlId);
            }

            List<Map> mapList = new ArrayList<>();


            List<Bsat> bsatList = iBsatService.list(queryWrapper);
            for (int i = 0; i < bsatList.size(); i++) {

                Map map = new HashMap();
                map.put("id", bsatList.get(i).getBsatAtpid());
                map.put("code", bsatList.get(i).getBsatCode());
                mapList.add(map);
            }


            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, mapList);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    /**
     * @param satIds
     * @return
     */
    @ApiOperation(value = "根据多个型号代号查询卫星详情(完成)", notes = "根据多个型号代号查询卫星详情")
    @PostMapping(value = "listBsatInfoBySatids", produces = "application/json;charset=UTF-8")
    public String listBsatInfoBySatids(@RequestParam(value = "satIds", defaultValue = "") String satIds,
                                       @RequestParam(value = "page", defaultValue = "1") String page,
                                       @RequestParam(value = "rows", defaultValue = "10") String rows,
                                       @RequestParam(value = "sort", defaultValue = "bsat_atplastmodifydatetime") String sort,
                                       @RequestParam(value = "order", defaultValue = "desc") String order) {
        IPage<Suser> pageResult = null;
        Page modelPage = new Page(Integer.parseInt(page), Integer.parseInt(rows));
        if (order.equals("asc")) {
            modelPage.setAsc(sort);
        } else if (order.equals("desc")) {
            modelPage.setDesc(sort);
        } else {
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        String newSatids = satIds.replace(",", "','");


        pageResult = iBsatService.selectAllSatListByIdsPage(modelPage, newSatids);
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, pageResult);
    }


    /**
     * @param satIds
     * @return
     */
    @ApiOperation(value = "根据多个型号代号更新卫星领域，删除时domain传空值（）", notes = "根据多个型号代号更新卫星领域")
    @PostMapping(value = "updateSatDomainBySatids", produces = "application/json;charset=UTF-8")
    public String updateSatDomainBySatids(@RequestParam(value = "satIds", defaultValue = "") String satIds, @RequestParam(value = "bxxrjlAtpid", defaultValue = "") String bxxrjlAtpid) {
        try {
            QueryWrapper queryWrapper = new QueryWrapper();

            String newSatids = satIds.replace(",", "','");
            queryWrapper.apply("htdxjk_bsat.bsat_code in ('" + newSatids + "')");
            List<Bsat> bsatList = iBsatService.list(queryWrapper);
            for (Bsat bsat : bsatList) {
                bsat.setBsatBxxrjlId(bxxrjlAtpid);
            }
            iBsatService.updateBatchById(bsatList);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.UPDATE_FAIL_STATUS, ResultTo.UPDATE_FAIL_STATUS_MSG, null);
        }
    }

    /**
     * @param satIds
     * @return
     */
    @ApiOperation(value = "根据多个型号代号添加卫星领域(done)", notes = "根据多个型号代号添加卫星领域")
    @PostMapping(value = "addSatDomainBySatids", produces = "application/json;charset=UTF-8")
    public String addSatDomainBySatids(HttpSession httpSession, @RequestParam(value = "satIds", defaultValue = "") String satIds, @RequestParam(value = "bxxrjlAtpid", defaultValue = "") String bxxrjlAtpid) {
        try {
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            String[] result1 = satIds.split(",");
            List<Bsat> bsatList = new ArrayList<>();
            for (String str : result1) {
                Bsat bsat = new Bsat();
                bsat.setBsatAtpid(UUID.randomUUID().toString());
                bsat.setBsatBxxrjlId(bxxrjlAtpid);
                bsat.setBsatAtplastmodifydatetime(Utils.getNow());
                bsat.setBsatAtplastmodifyuser(ls.getSUser().getSuAccount());
                bsat.setBsatAtpcreatedatetime(Utils.getNow());
                bsat.setBsatAtpcreatedatetime(Utils.getNow());
                bsat.setBsatAtpcreateuser(ls.getSUser().getSuAccount());
                bsat.setBsatCode(str);
                bsatList.add(bsat);
            }
            iBsatService.saveBatch(bsatList);
            return Utils.makeJSONResponseMsg(1, "新增成功", null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "新增失败", null);
        }
    }

//    @ApiOperation(value = "显示软件分类管理查询(废弃:改用/api/bxxrjl/findByParamEzui)", notes = "根据分类名称查询")
//    @ApiImplicitParam(name = "keyWord", value = "关键字", dataType = "String")
//    @PostMapping(value = "getBsatDomains", produces = "application/json;charset=UTF-8")
//    public String getBsatDomains(@RequestParam(value = "keyWord", defaultValue = "") String keyWord,
//                                 @RequestParam(value = "page", defaultValue = "1") String page,
//                                 @RequestParam(value = "rows", defaultValue = "10") String rows,
//                                 @RequestParam(value = "sort", defaultValue = "bsat_last_modifed_time") String sort,
//                                 @RequestParam(value = "order", defaultValue = "desc") String order) {
//
//        try {
//            IPage<HashMap> pageResult = null;
//
//            Page modelPage = new Page(Integer.parseInt(page), Integer.parseInt(rows));
//            if (order.equals("asc")) {
//                modelPage.setAsc(sort);
//            } else if (order.equals("desc")) {
//                modelPage.setDesc(sort);
//            } else {
//                Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
//            }
//            pageResult = iBsatService.getBsatDomainsPage(modelPage, keyWord);
//            return JSONObject.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            log.error(e.getLocalizedMessage());
//            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
//        }
//    }


    @ApiOperation(value = "根据关键字查询信息(完成 by Rechel)", notes = "查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })

    @PostMapping(value = "getBsatDomains", produces = "application/json;charset=UTF-8")
    public String getBsatDomains(@RequestParam(value = "keyWord", defaultValue = "") String keyWord,
                                  @RequestParam(value = "page", defaultValue = "1") String page,
                                  @RequestParam(value = "rows", defaultValue = "10") String rows,
                                  @RequestParam(value = "sort", defaultValue = "bxxrjl_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        int currPage = Integer.parseInt(page);

        int countRows = Integer.parseInt(rows);

        int offset = currPage * countRows - countRows;


        Map map = new HashMap();
        map.put("keyword", keyWord);
        map.put("rows", rows);
        map.put("offset", String.valueOf(offset));
        map.put("sort", sort);
        map.put("order", order);

        List<Map> list = iBxxrjlService.findBxxrjlBsats(map);

        int count = iBxxrjlService.findBxxrjlBsatsCount(map);

        Map<String, Object> result = new HashMap<>();
        result.put("total", count);
        result.put("rows", list);
        return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }

//    @ApiOperation(value = "根据分类名称查询(完成)", notes = "根据分类名称查询")
//    @PostMapping(value = "getBsatDomain", produces = "application/json;charset=UTF-8")
//    public String getBsatDomain(@RequestParam(value = "domain", defaultValue = "") String domain) {
//
//        try {
//            List<HashMap<String, String>> pageResult = iBsatService.getBsatDomain(domain);
//            return JSONObject.toJSONString(pageResult, SerializerFeature.WriteMapNullValue);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            log.error(e.getLocalizedMessage());
//            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
//        }
//    }

    /**
     * 获取id获取实体信息
     *
     * @param id
     * @return
     */
    @PostMapping(value = "getBsatById", produces = "application/json;charset=UTF-8")
    public String getBsatById(String id) {

        try {

            if (StringUtils.isNotEmpty(id)) {

                return Utils.makeJSONResponseMsg(0, "缺少参数:id", null);
            }

            Bsat bsat = iBsatService.getById(id);
            return Utils.makeJSONResponseMsg(1, "获取成功", bsat);


        } catch (Exception e) {

            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    @ApiOperation(value = "根据时间范围、多个型号代号、关键字、卫星类型 分页查询信息(done)", notes = "查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "satCodes", value = "型号代号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "domain", value = "卫星类型", required = false, dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    @PostMapping(value = "findByParamEzuiForBsat", produces = "application/json;charset=UTF-8")
    public String findByParamEzuiForBsat(@RequestParam(value = "satCodes", defaultValue = "") String satCodes,
                                         @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                         @RequestParam(value = "domain", defaultValue = "") String domain,
                                         @RequestParam(value = "startTime", defaultValue = "2000-01-01 00:00:00") String startTime,
                                         @RequestParam(value = "endTime", defaultValue = "2099-01-01 00:00:00") String endTime,
                                         @RequestParam(value = "page", defaultValue = "") String page,
                                         @RequestParam(value = "rows", defaultValue = "") String rows,
                                         @RequestParam(value = "sort", defaultValue = "b.bsat_atplastmodifydatetime") String sort,
                                         @RequestParam(value = "order", defaultValue = "desc") String order) {




        satCodes = satCodes.replace(",", "','");

        Map map = new HashMap();
        map.put("satCodes", satCodes);
        map.put("keyword", keyword);
        map.put("domain", domain);
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        if(StrUtil.hasBlank(page)||StrUtil.hasBlank(rows)){
            map.put("rows", "");
            map.put("offset", "");
        }else{
            int currPage = Integer.parseInt(page);
            int countRows = Integer.parseInt(rows);
            int offset = currPage * countRows - countRows;
            map.put("rows", rows);
            map.put("offset", String.valueOf(offset));
        }

        map.put("sort", sort);
        map.put("order", order);

        List<AllBsatModel> list = iBsatService.findByParamEzuiForBsat(map);

        int count = iBsatService.countByParamEzuiForBsat(map);

        Map<String, Object> result = new HashMap<>();
        result.put("total", count);
        result.put("rows", list);
        return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);


    }

    /**
     * 获取所有卫星型号 by boris_deng
     *
     * @return
     */

    @ApiOperation(value = "获取所有卫星型号by boris_deng(完成)", notes = "获取所有卫星型号")
    @PostMapping(value = "getSatelliteCodeList", produces = "application/json;charset=UTF-8")
    public String getSatelliteCodeList() {

        try {

            List<Map<String, Object>> list = iBsatService.getSatelliteCodeList();
            return Utils.makeJSONResponseMsg(1, "获取成功", list);

        } catch (Exception e) {

            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }

    }

    /**
     * 根据卫星型号列表更新domain值 by boris_deng
     *
     * @param domain 分类名称
     * @param codes  型号列表，多个用逗号隔开
     * @return
     */
    @ApiOperation(value = "根据卫星型号列表更新domain值by boris_deng(完成)", notes = "根据卫星型号列表更新domain值")
    @PostMapping(value = "updateSatelliteDomainByCodes", produces = "application/json;charset=UTF-8")
    public String updateSatelliteDomainByCodes(@RequestParam(value = "domain", defaultValue = "") String domain, @RequestParam(value = "codes", defaultValue = "") String codes) {

        try {

            if (StringUtils.isEmpty(codes)) {

                return Utils.makeJSONResponseMsg(0, "缺少参数codes", null);
            }
            String[] codesArray = codes.split(",");
            iBsatService.updateSatelliteDomainByCodes(domain, codesArray);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);

        } catch (Exception e) {

            e.printStackTrace();
            return Utils.makeJSONResponseMsg(ResultTo.UPDATE_FAIL_STATUS, ResultTo.UPDATE_FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 根据卫星分类(domain)列出所属型号(code) by boris_deng
     *
     * @param domain 分类名称
     * @return
     */
    @ApiOperation(value = "根据卫星分类(domain)列出所属型号(code) by boris_deng(完成)", notes = "根据卫星分类(domain)列出所属型号(code)")
    @PostMapping(value = "getSatelliteCodesByDomain", produces = "application/json;charset=UTF-8")
    public String getSatelliteCodesByDomain(@RequestParam(value = "domain", defaultValue = "") String domain) {

        try {

            if (StringUtils.isEmpty(domain)) {

                return Utils.makeJSONResponseMsg(0, "缺少参数domain", null);
            }

            Map<String, Object> result = iBsatService.getSatelliteCodesByDomain(domain);
            return Utils.makeJSONResponseMsg(1, "获取成功", result);

        } catch (Exception e) {

            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    @ApiOperation(value = "查询卫星分类信息(去重)", notes = "查询卫星分类信息(去重)")
    @GetMapping(value = "getDistDomain", produces = "application/json;charset=UTF-8")
    public String getDistDomain() {
        List<String> distDomain = iBsatService.getDistDomain();
        return Utils.makeJSONResponseMsg(1, "查询成功", distDomain);

    }


    /**
     * 导出excel
     *
     * @param response
     * @throws IOException
     */

    @ApiOperation(value = "根据时间范围、关键字导出excel(完成)", notes = "导出excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    @GetMapping(value = "exportExcel")
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(value = "keyword", defaultValue = "") String keyword,
                            @RequestParam(value = "startTime", defaultValue = "1900-01-01 00:00:00") String startTime,
                            @RequestParam(value = "endTime", defaultValue = "2099-01-01 00:00:00") String endTime,
                            @RequestParam(value = "sort", defaultValue = "bsat_atplastmodifydatetime") String sort,
                            @RequestParam(value = "order", defaultValue = "desc") String order) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        Map map = new HashMap();
        map.put("keyword", keyword);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("sort", sort);
        map.put("order", order);
        List<AllBsatModel> mapList = iBsatService.findByParamEzuiForBsat(map);
        //设置要导出的文件的名字
        String fileName = "bckplan-info" + Utils.getNow() + ".xls";
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"序号", "型号代号", "型号代号(5院)", "对外名称", "发射时间", "考核寿命", "状态", "责任人", "责任人电话", "替代人", "替代人电话"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (AllBsatModel map1 : mapList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(rowNum);
            if (map1.getBsatCode() != null) {
                row1.createCell(1).setCellValue(map1.getBsatCode());
            } else {
                row1.createCell(1).setCellValue("");
            }
            if (map1.getBsatName() != null) {
                row1.createCell(2).setCellValue(map1.getBsatName());
            } else {
                row1.createCell(2).setCellValue("");
            }
            if (map1.getBsatOutsideName() != null) {
                row1.createCell(3).setCellValue(map1.getBsatOutsideName());
            } else {
                row1.createCell(3).setCellValue("");
            }
            if (map1.getBsatLaunchTime() != null) {
                row1.createCell(4).setCellValue(map1.getBsatLaunchTime());
            } else {
                row1.createCell(4).setCellValue("");
            }
            if (map1.getBsatLifeyear() != null) {
                row1.createCell(5).setCellValue(map1.getBsatLifeyear());
            } else {
                row1.createCell(5).setCellValue("");
            }
            if (map1.getBsatActive() != null) {
                row1.createCell(6).setCellValue(map1.getBsatActive());
            } else {
                row1.createCell(6).setCellValue("");
            }
            if (map1.getZrrname() != null) {
                row1.createCell(7).setCellValue(map1.getZrrname());
            } else {
                row1.createCell(7).setCellValue("");
            }
            if (map1.getZrrdh() != null) {
                row1.createCell(8).setCellValue(map1.getZrrdh());
            } else {
                row1.createCell(8).setCellValue("");
            }
            if (map1.getTdrname() != null) {
                row1.createCell(9).setCellValue(map1.getTdrname());
            } else {
                row1.createCell(9).setCellValue("");
            }
            if (map1.getTdrdh() != null) {
                row1.createCell(10).setCellValue(map1.getZrrdh());
            } else {
                row1.createCell(10).setCellValue("");
            }
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }


}
