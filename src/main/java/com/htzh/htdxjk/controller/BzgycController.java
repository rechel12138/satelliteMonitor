package com.htzh.htdxjk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.IBzgycService;
import com.htzh.htdxjk.service.IBzbbwlService;
import com.htzh.htdxjk.service.IBzgycService;
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
 * @since 2019-02-23
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"在轨异常"})
@RequestMapping("/api/bzgyc")
public class BzgycController {


    @Autowired
    private IBzgycService iBzgycService;


    @PostMapping(value = "listBzgyc", produces = "application/json;charset=UTF-8")
    public String listBzgyc() {
        List map = iBzgycService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**
     * 增加
     */

    @PostMapping(value = "addBzgyc", produces = "application/json;charset=UTF-8")
    public String addBzgyc(Bzgyc inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBzgycAtpid(UUID.randomUUID().toString());
            inputEntity.setBzgycAtpcreatedatetime(Utils.getNow());
            inputEntity.setBzgycAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBzgycAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBzgycAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBzgycService.save(inputEntity);
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

    @PostMapping(value = "updateBzgyc", produces = "application/json;charset=UTF-8")
    public String updateBzgyc(Bzgyc inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBzgycAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBzgycAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBzgycService.updateById(inputEntity);
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
    @PostMapping(value = "removeBzgyc", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "bzgycAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBzgyc(@RequestParam("bzgycAtpid") String bzgycAtpid) {
        try {
            String[] idsArray = bzgycAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBzgycService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "bzgyc_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bzgyc obj = new Bzgyc();
        //筛选字段list
        List list = new ArrayList();

        list.add("bzgyc_data_id");
        list.add("bzgyc_tamplate_id");
        list.add("bzgyc_astellite_id");
        list.add("bzgyc_is_big_event");
        list.add("bzgyc_c_512");
        list.add("bzgyc_xml_sata_id");
        list.add("bzgyc_c_8081");
        list.add("bzgyc_c_8083");
        list.add("bzgyc_c_8787");
        list.add("bzgyc_c_8788");
        list.add("bzgyc_c_8812");
        list.add("bzgyc_c_8813");
        list.add("bzgyc_c_8817");
        list.add("bzgyc_c_8818");
        list.add("bzgyc_c_8820");
        list.add("bzgyc_c_8871");
        list.add("bzgyc_is_delted");
        list.add("bzgyc_c_9012");
        list.add("bzgyc_c_9060");
        list.add("bzgyc_c_9064");
        list.add("bzgyc_exception_state");
        list.add("bzgyc_first_data_id");
        list.add("bzgyc_depariment");
        list.add("bzgyc_submit_time");
        list.add("bzgyc_c_9079");
        list.add("bzgyc_c_9117");
        list.add("bzgyc_submit_state");
        list.add("bzgyc_exception_report");
        list.add("bzgyc_c_9594");
        list.add("bzgyc_c_9997");
        list.add("bzgyc_c_9998");
        list.add("bzgyc_c_9999");
        list.add("bzgyc_c_10000");
        list.add("bzgyc_import_time");
        list.add("bzgyc_c_520");
        list.add("bzgyc_c_521");
        list.add("bzgyc_c_522");
        list.add("bzgyc_c_9739");
        list.add("bzgyc_device_code");
        list.add("bzgyc_email_sended");
        list.add("bzgyc_is_planned");
        list.add("bzgyc_c_9011");
        list.add("bzgyc_c_9595");
        list.add("bzgyc_c_9996");
        list.add("bzgyc_c_8819");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBzgycService);
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBzgycService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    @ApiOperation(value = "根据航天器型号查询在轨异常(完成)", notes = "根据航天器型号查询在轨异常")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "satId", value = "航天器ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bzgycName", value = "异常名称", required = false, dataType = "String")
    })
    @PostMapping(value = "listBcscfycBySatId", produces = "application/json;charset=UTF-8")
    public String listBcscfycBySatId(@RequestParam(value = "satId", required = true) String satId,
                                     @RequestParam(value = "bzgycName", defaultValue = "") String bzgycName) {
        try {
            List<Bzgyc> bzgycBySatIdAndParam = iBzgycService.getBzgycBySatIdAndParam(satId, bzgycName);
            Map<String, Object> map = new HashMap<>();
            map.put("rows", bzgycBySatIdAndParam);
            return JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            log.error(e.getMessage());
            return Utils.makeJSONResponseMsg(0, "获取失败", null);
        }
    }
}
