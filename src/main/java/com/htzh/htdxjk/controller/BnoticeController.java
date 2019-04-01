package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.Bnotice;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.entity.Suser;
import com.htzh.htdxjk.service.IBnoticeService;
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
 *  前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-02
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"通知管理"})
@RequestMapping("/api/bnotice")
public class BnoticeController {


    @Autowired
    private IBnoticeService iBnoticeService;


    //查询
    @PostMapping(value = "listBnotice",produces = "application/json;charset=UTF-8")
    public String listBnotice() {
        List map = iBnoticeService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**      * 增加     */
    @ApiOperation(value = "新增通知（完成）", notes = "新增通知")
    @PostMapping(value = "addBnotice",produces = "application/json;charset=UTF-8")
    public String addBnotice(Bnotice inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
            inputEntity.setBnteAtpid(UUID.randomUUID().toString());
            inputEntity.setBnteAtpcreatedatetime(Utils.getNow());
            inputEntity.setBnteAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBnteAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBnteAtplastmodifyuser(ls.getSUser().getSuAccount());

            inputEntity.setBnteAtpstatus("INUSE");
            iBnoticeService.save(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    /**
     *修改
     */
    @ApiOperation(value = "修改通知（完成）", notes = "修改通知")
    @PostMapping(value = "updateBnotice",produces = "application/json;charset=UTF-8")
    public String updateBnotice(Bnotice inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
            }
            //具体过程
            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
            inputEntity.setBnteAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBnteAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBnoticeService.updateById(inputEntity);
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
    @PostMapping(value = "removeBnotice",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除（完成）", notes = "批量删除")
    @ApiImplicitParam(name = "BnoticeAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBnotice(@RequestParam("BnoticeAtpid") String BnoticeAtpid) {
        try {
            String[] idsArray=BnoticeAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBnoticeService.removeByIds(arrayList);
            return Utils.makeJSONResponseMsg(ResultTo.DEL_SUCCESS_STATUS, ResultTo.DEL_SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.DEL_FAIL_STATUS, ResultTo.DEL_FAIL_STATUS_MSG, null);
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
                                  @RequestParam(value = "sort", defaultValue = "bnte_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bnotice obj = new Bnotice();
        //筛选字段list
        List list = new ArrayList();

        list.add("bnte_atpstatus");
        list.add("bnte_atpsort");
        list.add("bnte_atpdotype");
        list.add("bnte_atpremark");
        list.add("bnte_type");
        list.add("bnte_desc");
        list.add("bnte_timelong");

        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBnoticeService);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        return JSON.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");


    }



    @ApiOperation(value = "根据关键字查询通知信息(完成)", notes = "查询信息")
    @PostMapping(value = "listBzgwxxhfzlxrByKeyword", produces = "application/json;charset=UTF-8")
    public String listBzgwxxhfzlxrByKeyword(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                            @RequestParam(value = "page", defaultValue = "1") String page,
                                            @RequestParam(value = "rows", defaultValue = "10") String rows,
                                            @RequestParam(value = "sort", defaultValue = "bnte_atplastmodifydatetime") String sort,
                                            @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bnotice obj = new Bnotice();
        //筛选字段list
        List list = new ArrayList();

        list.add("bnte_atpstatus");
        list.add("bnte_atpsort");
        list.add("bnte_atpdotype");
        list.add("bnte_atpremark");
        list.add("bnte_type");
        list.add("bnte_desc");
        list.add("bnte_timelong");

        String col="bnte_atpstatus";

        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResultForBnotice(page, rows, sort, order, obj, keyword, list, iBnoticeService, col);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        return JSON.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");


    }

    /**
     * 根据atpid获取一条信息
     * @param atpId
     * @return
     */
    @ApiOperation(value = "根据atpid获取一条信息", notes = "根据atpid获取一条信息")
    @PostMapping(value = "/getOneById", produces = "application/json;charset=UTF-8")
    public String getOneById(@RequestParam(value = "atpId", defaultValue = "") String atpId) {
        try {
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBnoticeService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    /**
     *
     * 获取所有有效期内的通知列表
     * @return
     */
    @ApiOperation(value = "获取所有有效期内的通知列表(完成) by borid_deng", notes = "获取所有有效期内的通知列表")
    @PostMapping(value = "/getAvailableNoticeLists", produces = "application/json;charset=UTF-8")
    public String getAvailableNoticeLists(){

        try{

            List<Map<String,Object>> list = iBnoticeService.getAvailableNoticeLists();
            return Utils.makeJSONResponseMsg(1, "获取成功", list);

        }catch (Exception e){

            e.printStackTrace();
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }
}
