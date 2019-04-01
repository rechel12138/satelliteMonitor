package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.Bmonthpre;
import com.htzh.htdxjk.entity.BmonthpreSatModel;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.entity.Suser;
import com.htzh.htdxjk.service.IBmonthpreService;
import com.htzh.htdxjk.utils.EzuiUtil;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@Api(value = "user-api", tags = {"地月影预报"})
@RequestMapping("/api/bmonthpre")
public class BmonthpreController {


    @Autowired
    private IBmonthpreService iBmonthpreService;


    /**
     * 查询
     *
     * @return
     */
    @PostMapping(value = "listBmonthpre", produces = "application/json;charset=UTF-8")
    public String listBmonthpre() {
        List map = iBmonthpreService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**
     * 查询
     *
     * @param satid
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    @ApiOperation(value = "根据航天器id查询地月影信息", notes = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "satid", value = "航天器型号id", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    @PostMapping(value = "listBmonthpreBySatid", produces = "application/json;charset=UTF-8")
    public String listBmonthpreBySatid(@RequestParam("satid") String satid,
                                       @RequestParam(value = "page", defaultValue = "1") String page,
                                       @RequestParam(value = "rows", defaultValue = "10") String rows,
                                       @RequestParam(value = "sort", defaultValue = "bmonthpre_atplastmodifydatetime") String sort,
                                       @RequestParam(value = "order", defaultValue = "desc") String order) {

        Page modelPage = new Page(Integer.parseInt(page), Integer.parseInt(rows));
        if (order.equals("desc")) {
            modelPage.setDesc(sort);
        } else if (order.equals("asc")) {
            modelPage.setAsc(sort);
        } else {
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        IPage<BmonthpreSatModel> pageResult = null;
        pageResult = iBmonthpreService.selectBmonthpreSatModelList(modelPage, satid);

        return JSONObject.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");
    }

    /**
     * 增加
     */

    @PostMapping(value = "addBmonthpre", produces = "application/json;charset=UTF-8")
    public String addBmonthpre(Bmonthpre inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBmonthpreAtpid(UUID.randomUUID().toString());
            inputEntity.setBmonthpreAtpcreatedatetime(Utils.getNow());
            inputEntity.setBmonthpreAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBmonthpreAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBmonthpreAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBmonthpreService.save(inputEntity);
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

    @PostMapping(value = "updateBmonthpre", produces = "application/json;charset=UTF-8")
    public String updateBmonthpre(Bmonthpre inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBmonthpreAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBmonthpreAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBmonthpreService.updateById(inputEntity);
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
    @PostMapping(value = "removeBmonthpre", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "bdxznzdxtssbjAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBmonthpre(@RequestParam("bdxznzdxtssbjAtpid") String bdxznzdxtssbjAtpid) {
        try {
            String[] idsArray = bdxznzdxtssbjAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBmonthpreService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "bmonthpre_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bmonthpre obj = new Bmonthpre();
        //筛选字段list
        List list = new ArrayList();

        list.add("bmonthpre_id");
        list.add("bmonthpre_btime");
        list.add("bmonthpre_etime");
        list.add("bmonthpre_satellite");
        list.add("bmonthpre_mstype");
        list.add("bmonthpre_span");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBmonthpreService);
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBmonthpreService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 根据satId获取地月影预报信息
     *
     * @param satId
     * @return
     */
    @ApiOperation(value = "根据satId获取地月影预报信息(完成)", notes = "根据satId获取地月影预报信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "satId", value = "卫星ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "keyWord", value = "关键字", dataType = "String")
    })
    @PostMapping(value = "/getBySatId", produces = "application/json;charset=UTF-8")
    public String getBySatId(@RequestParam(value = "satId", required = true) String satId,
                             @RequestParam(value = "keyWord", defaultValue = "") String keyWord) {
        try {
            List<BmonthpreSatModel> bySatIdAndSatCode = iBmonthpreService.getBySatIdAndSatCode(satId, keyWord);
            Map<String, Object> map = new HashMap<>();
            map.put("rows", bySatIdAndSatCode);
            return JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

}
