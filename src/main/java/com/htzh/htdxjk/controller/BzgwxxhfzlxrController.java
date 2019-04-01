package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.IBzgwxxhfzlxrService;
import com.htzh.htdxjk.service.IBzgwxxhfzlxrService;
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
@Api(value = "user-api", tags = {"在轨卫星型号责任联系人"})
@RequestMapping("/api/bzgwxxhfzlxr")
public class BzgwxxhfzlxrController {


    @Autowired
    private IBzgwxxhfzlxrService iBzgwxxhfzlxrService;


    @ApiOperation(value = "根据型号ID查询(完成)", notes = "根据型号ID查询")
    @PostMapping(value = "listBySatId", produces = "application/json;charset=UTF-8")
    public String listBySatId(@RequestParam(value = "satId") String satId) {
        try {
            List<Map<String, Object>> listBySatId = iBzgwxxhfzlxrService.findListBySatId(satId);
            Map<String, Object> map = new HashMap<>();
            map.put("rows", listBySatId);
            return Utils.makeJSONResponseMsg(1, "查询成功", map);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            log.error(e.getMessage());
            return Utils.makeJSONResponseMsg(0, "查询失败", null);
        }
    }

    //查询

    @PostMapping(value = "listBzgwxxhfzlxr", produces = "application/json;charset=UTF-8")
    public List listBzgwxxhfzlxr() {
        List map = iBzgwxxhfzlxrService.list();
        return map;
    }

    /**
     * 增加
     */

    @PostMapping(value = "addBzgwxxhfzlxr", produces = "application/json;charset=UTF-8")
    public String addBzgwxxhfzlxr(Bzgwxxhfzlxr inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBzgwxxhfzlxrAtpid(UUID.randomUUID().toString());
            inputEntity.setBzgwxxhfzlxrAtpcreatedatetime(Utils.getNow());
            inputEntity.setBzgwxxhfzlxrAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBzgwxxhfzlxrAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBzgwxxhfzlxrAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBzgwxxhfzlxrService.save(inputEntity);
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

    @PostMapping(value = "updateBzgwxxhfzlxr", produces = "application/json;charset=UTF-8")
    public String updateBzgwxxhfzlxr(Bzgwxxhfzlxr inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBzgwxxhfzlxrAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBzgwxxhfzlxrAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBzgwxxhfzlxrService.updateById(inputEntity);
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
    @PostMapping(value = "removeBzgwxxhfzlxr", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "bzgwxxhfzlxrAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBzgwxxhfzlxr(@RequestParam("bzgwxxhfzlxrAtpid") String bzgwxxhfzlxrAtpid) {
        try {
            String[] idsArray = bzgwxxhfzlxrAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBzgwxxhfzlxrService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "bzgwxxhfzlxr_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bzgwxxhfzlxr obj = new Bzgwxxhfzlxr();
        //筛选字段list
        List list = new ArrayList();




        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBzgwxxhfzlxrService);
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBzgwxxhfzlxrService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

}
