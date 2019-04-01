package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.Bdwgl;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.entity.Suser;
import com.htzh.htdxjk.service.IBdwglService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-23
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"单位管理"})
@RequestMapping("/api/bdwgl")
public class BdwglController {


    @Autowired
    private IBdwglService iBdwglService;


    //查询

    @PostMapping(value = "listBdwgl",produces = "application/json;charset=UTF-8")
    public String listBdwgl() {
        List map = iBdwglService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**      * 增加     */

    @PostMapping(value = "addBdwgl",produces = "application/json;charset=UTF-8")
    public String addBdwgl(Bdwgl inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
            inputEntity.setBdwglAtpid(UUID.randomUUID().toString());
            inputEntity.setBdwglAtpcreatedatetime(Utils.getNow());
            inputEntity.setBdwglAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBdwglAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBdwglAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBdwglService.save(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**      * 修改      */

    @PostMapping(value = "updateBdwgl",produces = "application/json;charset=UTF-8")
    public String updateBdwgl(Bdwgl inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
            inputEntity.setBdwglAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBdwglAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBdwglService.updateById(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }

    }

//    /**      * 删除      */
//
//    @PostMapping("removeBdwgl")
//    public String removeBdwgl(@RequestParam("bdwglAtpid") String id, HttpSession httpSession) {
//
//        try {
//            //检查权限
//            if (!Utils.checkLoginStatus(httpSession)) {
//                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
//
//            }
//            //具体过程
//            LoginStatus ls = (LoginStatus)httpSession.getAttribute("loginStatus");
//            iBdwglService.removeById(id);
//            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            log.error(e.getLocalizedMessage());
//            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
//        }
//
//    }
    /**      * 删除      */
    @PostMapping(value = "removeBdwgl",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "bdwglAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBdwgl(@RequestParam("bdwglAtpid") String bdwglAtpid) {
        try {
            String[] idsArray=bdwglAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBdwglService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "bdwgl_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bdwgl obj = new Bdwgl();
        //筛选字段list
        List list = new ArrayList();

        list.add("bdwgl_xh");
        list.add("bdwgl_satname");
        list.add("bdwgl_zrr");
        list.add("bdwgl_dtr");
        list.add("bdwgl_ckdw");
        list.add("bdwgl_zbdh");
        list.add("bdwgl_czdh");
        list.add("bdwgl_htzcdh");
        list.add("bdwgl_yhdw");
        list.add("bdwgl_yhlxr");
        list.add("bdwgl_lxfs");
        list.add("bdwgl_dgpfax");
        list.add("bdwgl_dgphone");
        list.add("bdwgl_ggfax");
        list.add("bdwgl_ggphone");



        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBdwglService);
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBdwglService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }
}
