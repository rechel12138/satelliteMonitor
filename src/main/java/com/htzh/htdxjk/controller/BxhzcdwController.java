package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.IBxhzcdwService;
import com.htzh.htdxjk.service.IBxhzcdwService;
import com.htzh.htdxjk.service.IBxxrjlService;
import com.htzh.htdxjk.utils.EzuiUtil;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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
 * @since 2019-02-23
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"型号支持队伍"})
@RequestMapping("/api/bxhzcdw")
public class BxhzcdwController {


    @Autowired
    private IBxhzcdwService iBxhzcdwService;


    /**
     * 查询
     *
     * @return
     */
    @PostMapping(value = "listBxhzcdw", produces = "application/json;charset=UTF-8")
    public String listBxhzcdw() {
        List map = iBxhzcdwService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, map);
    }

    /**
     * 根据航天器id查询信息
     *
     * @param satId
     * @return
     */
    @ApiOperation(value = "根据航天器id查询信息(完成)", notes = "查询信息")
    @PostMapping(value = "listBxhzcdwBySatid", produces = "application/json;charset=UTF-8")
    public String listBxhzcdwBySatid(@RequestParam(value = "satId", defaultValue = "") String satId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("bxhzcdw_satcode", satId);
        Bxhzcdw bxhzcdw = iBxhzcdwService.getOne(queryWrapper);

        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, bxhzcdw);
    }

    /**
     * 增加
     */

    @PostMapping(value = "addBxhzcdw", produces = "application/json;charset=UTF-8")
    public String addBxhzcdw(Bxhzcdw inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {

                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBxhzcdwAtpid(UUID.randomUUID().toString());
            inputEntity.setBxhzcdwAtpcreatedatetime(Utils.getNow());
            inputEntity.setBxhzcdwAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBxhzcdwAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBxhzcdwAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBxhzcdwService.save(inputEntity);
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

    @PostMapping(value = "updateBxhzcdw", produces = "application/json;charset=UTF-8")
    public String updateBxhzcdw(Bxhzcdw inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBxhzcdwAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBxhzcdwAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBxhzcdwService.updateById(inputEntity);
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
    @PostMapping(value = "removeBxhzcdw", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "bxhzcdwAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBxhzcdw(@RequestParam("bxhzcdwAtpid") String bxhzcdwAtpid) {
        try {
            String[] idsArray = bxhzcdwAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBxhzcdwService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "bxhzcdw_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bxhzcdw obj = new Bxhzcdw();
        //筛选字段list
        List list = new ArrayList();

        list.add("bxhzcdw_name");
        list.add("bxhzcdw_unit");
        list.add("bxhzcdw_phone");
        list.add("bxhzcdw_telephone");
        list.add("bxhzcdw_remark");
        list.add("bxhzcdw_post");
        list.add("bxhzcdw_seq");
        list.add("bxhzcdw_id");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBxhzcdwService);
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBxhzcdwService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());

            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    /**
     * 根据satId获取在轨技术支持队伍信息
     * @param satId
     * @param keyWord
     * @return
     */
    @ApiOperation(value = "根据satId获取在轨技术支持队伍信息", notes = "据satId获取在轨技术支持队伍信息")
    @PostMapping(value = "/getBySatId", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "satId", value = "卫星ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "keyWord", value = "关键字", dataType = "String")
    })
    public String getBySatId(@RequestParam(value = "satId", required = true) String satId,
                             @RequestParam(value = "keyWord", defaultValue = "") String keyWord) {
        try {
            List<Bxhzcdw> bySatIdOrSatCode = iBxhzcdwService.findBySatIdOrSatCode(satId, keyWord);
            Map<String, Object> map = new HashMap<>();
            map.put("rows", bySatIdOrSatCode);
            return JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    @ApiOperation(value = "根据航天器atpid获取在责任人信息(完成 by Rechel)", notes = "根据航天器atpid获取在责任人信息")
    @ApiImplicitParam(name = "atpid", value = "航天器atpid", required = true, dataType = "String")
    @PostMapping(value = "/getZrrInfoByBsatAtpId", produces = "application/json;charset=UTF-8")
    public String getZrrInfoByBsatAtpId(@RequestParam(value = "atpid", required = true) String atpid) {
        try {
            List<Bxhzcdw> bxhzcdws = iBxhzcdwService.findZrrBySatAtpId(atpid);
            Map<String, Object> map = new HashMap<>();
            map.put("rows", bxhzcdws);
            return JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    @ApiOperation(value = "根据航天器atpid获取支持队伍信息(完成 by Rechel)", notes = "根据航天器atpid获取支持队伍信息")
    @ApiImplicitParam(name = "atpid", value = "航天器atpid", required = true, dataType = "String")
    @PostMapping(value = "/getZcdwInfoByBsatAtpId", produces = "application/json;charset=UTF-8")
    public String getZcdwInfoByBsatAtpId(@RequestParam(value = "atpid", required = true) String atpid) {
        try {
            List<Bxhzcdw> bxhzcdws = iBxhzcdwService.findZcdwBySatAtpId(atpid);
            Map<String, Object> map = new HashMap<>();
            map.put("rows", bxhzcdws);
            return JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }
}
