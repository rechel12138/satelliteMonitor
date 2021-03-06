package com.htzh.htdxjk.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.IBsatTopService;
import com.htzh.htdxjk.service.ISuserroleService;
import com.htzh.htdxjk.utils.EzuiUtil;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @ClassName BsatTopController
 * @Description TOOD
 * @Author guoconglin
 * @DATE 2019/3/10 15:43
 * @Version 1.0
 **/

@RestController
@Slf4j
@Api(value = "user-api", tags = {"置顶卫星信息"})
@RequestMapping("/api/bsatTop/")
public class BsatTopController {

    @Autowired
    private IBsatTopService iBsatTopService;

    @Autowired
    private ISuserroleService iSuserroleService;

    @Value("${role.adminId}")
    private String roleAdminId;


    /**
     * 查询
     */
    @ApiOperation(value = "卫星置顶列表(完成)", notes = "卫星置顶列表")
    @PostMapping(value = "listBsatTop", produces = "application/json;charset=UTF-8")
    public String listBsatTop(HttpSession httpSession) {
        String userId = null;
        //具体过程
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
        String isAdmin = "是";
        if (!StringUtils.isEmpty(ls)) {
            userId = ls.getSUser().getSuAtpid();
            List<String> stringList = iSuserroleService.getUserRoleIdStringByUserId(userId);
            if (stringList.contains(roleAdminId)) {
                //是管理员
                isAdmin = "否";
            }

        }
        List<Map<String, Object>> listResult = iBsatTopService.findListByUserId(userId, isAdmin);

        return Utils.makeJSONResponseMsg(1, "查询成功", listResult);
    }

    /**
     * 增加
     */

    @PostMapping(value = "addBsatTop", produces = "application/json;charset=UTF-8")
    public String addBsatTop(BsatTop inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBstAtpid(UUID.randomUUID().toString());
            inputEntity.setBstAtpcreatedatetime(Utils.getNow());
            inputEntity.setBstAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBstAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBstAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBsatTopService.save(inputEntity);
            return Utils.makeJSONResponseMsg(1, "增加成功", null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "增加失败", null);
        }
    }

    /**
     * 修改
     */

    @PostMapping(value = "updateBsatTop", produces = "application/json;charset=UTF-8")
    public String updateBsatTop(BsatTop inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBstAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBstAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBsatTopService.updateById(inputEntity);
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
    @PostMapping(value = "removeBsatTop", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "bstAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBsatTop(@RequestParam("bstAtpid") String bstAtpid) {
        try {
            String[] idsArray = bstAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBsatTopService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "bst_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Bsjmxys obj = new Bsjmxys();
        //筛选字段list
        List list = new ArrayList();


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBsatTopService);
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBsatTopService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }
}
