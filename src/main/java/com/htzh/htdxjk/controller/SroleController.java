package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.ISmoduleService;
import com.htzh.htdxjk.service.ISroleService;
import com.htzh.htdxjk.service.ISrolemoduleService;
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
 * @since 2019-02-20
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"账号角色"})
@RequestMapping("/api/srole")
public class SroleController {


    @Autowired
    private ISroleService iSroleService;

    @Autowired
    private ISmoduleService iSmoduleService;

    @Autowired
    private ISrolemoduleService iSrolemoduleService;

    //查询

    @PostMapping(value = "listSrole", produces = "application/json;charset=UTF-8")
    public String listSrole() {
        List map = iSroleService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**
     * 增加
     */

    @PostMapping(value = "addSrole", produces = "application/json;charset=UTF-8")
    public String addSrole(Srole inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setSrAtpid(UUID.randomUUID().toString());
            inputEntity.setSrAtpcreatedatetime(Utils.getNow());
            inputEntity.setSrAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setSrAtplastmodifydatetime(Utils.getNow());
            inputEntity.setSrAtplastmodifyuser(ls.getSUser().getSuAccount());

            iSroleService.save(inputEntity);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 修改
     */

    @PostMapping(value = "updateSrole", produces = "application/json;charset=UTF-8")
    public String updateSrole(Srole inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setSrAtplastmodifydatetime(Utils.getNow());
            inputEntity.setSrAtplastmodifyuser(ls.getSUser().getSuAccount());

            iSroleService.updateById(inputEntity);
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
    @PostMapping(value = "removeSrole", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "srAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeSrole(@RequestParam("srAtpid") String srAtpid) {
        try {
            String[] idsArray = srAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iSroleService.removeByIds(arrayList);
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
                                  @RequestParam(value = "sort", defaultValue = "sr_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Srole obj = new Srole();
        //筛选字段list
        List list = new ArrayList();

        list.add("sr_name");
        list.add("sr_remark");


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iSroleService);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        return JSON.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");


    }


    /**
     * 获取所有的菜单列表（树结构）
     *
     * @return
     */
    @PostMapping(value = "getAllMenu", produces = "application/json;charset=UTF-8")
    public String getAllMenu() {

        Smodule fm = new Smodule();
        fm.setSmFksmoduleid("");
        fm.setSmIsdisplay("0");
        //查询需要登录显示的菜单
        fm.setSmIslogineddisplay(0);
        QueryWrapper<Smodule> fq = new QueryWrapper<Smodule>(fm);
        List<Smodule> fMenuList = iSmoduleService.list(fq);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (Smodule fMenuItem : fMenuList) {

            Map<String, Object> result = new HashMap<String, Object>();
            String fMenuId = String.valueOf(fMenuItem.getSmAtpid());
            String fMenuName = String.valueOf(fMenuItem.getSmName());
            result.put("id", fMenuId);
            result.put("text", fMenuName);
            result.put("iconCls", "");
            result.put("level", 1);

            List<Map<String, Object>> sMenuMapList = new ArrayList<Map<String, Object>>();
            Smodule sm = new Smodule();
            sm.setSmFksmoduleid(fMenuId);
            QueryWrapper<Smodule> sq = new QueryWrapper<Smodule>(sm);
            List<Smodule> sMenuList = iSmoduleService.list(sq);
            for (Smodule sMenuItem : sMenuList) {

                Map<String, Object> sMenuItemMap = new HashMap<String, Object>();
                sMenuItemMap.put("id", sMenuItem.getSmAtpid());
                sMenuItemMap.put("text", sMenuItem.getSmName());
                sMenuItemMap.put("iconCls", "");
                sMenuItemMap.put("level", 2);
                sMenuItemMap.put("children", Utils.getMenuActionList(sMenuItem.getSmAction()));
                sMenuMapList.add(sMenuItemMap);
            }

            result.put("children", sMenuMapList);

            resultList.add(result);
        }

        return JSON.toJSONString(resultList, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 根据角色id获取已配置的权限
     *
     * @param roleId
     * @return
     */
    @PostMapping(value = "getMenuByRoleId", produces = "application/json;charset=UTF-8")
    public String getMenuByRoleId(String roleId) {

        Srolemodule rm = new Srolemodule();
        rm.setSrmFksroleid(roleId);
        QueryWrapper<Srolemodule> q = new QueryWrapper<Srolemodule>(rm);
        List<Srolemodule> list = iSrolemoduleService.list(q);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Srolemodule item : list) {

            Map<String, Object> result = new HashMap<>();
            result.put("roleId", item.getSrmFksroleid());
            result.put("moduleId", item.getSrmFksmoduleid());
            result.put("action", StringUtils.isNotEmpty(item.getSrmSmaction()) ? item.getSrmSmaction().split(",") : new String[]{});
            resultList.add(result);
        }

        return JSON.toJSONString(resultList, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 保存配置
     *
     * @param roleId
     * @param action
     * @return
     */
    @PostMapping(value = "saveRoleMenu", produces = "application/json;charset=UTF-8")
    public String saveRoleMenu(String roleId, String action, HttpSession httpSession) {

        try {

            if (StringUtils.isEmpty(roleId)) {

                return Utils.makeJSONResponseMsg(0, "roldId不能为空", null);
            }

            if (StringUtils.isEmpty(action)) {

                return Utils.makeJSONResponseMsg(0, "权限配置项不能为空", null);
            }


            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");

            /**      * 删除该角色的所有权限      */
            Srolemodule sm = new Srolemodule();
            sm.setSrmFksroleid(roleId);
            QueryWrapper<Srolemodule> q = new QueryWrapper<>(sm);
            iSrolemoduleService.remove(q);

            //保存该角色配置的权限
            String[] actions = action.split("\\|");
            for (String item : actions) {

                String moduleId = item.split("_")[0];
                String moduleAction = "null".equals(item.split("_")[1]) ? "" : item.split("_")[1];
                Srolemodule rm = new Srolemodule();
                rm.setSrmAtpid(UUID.randomUUID().toString());
                rm.setSrmAtpcreatedatetime(Utils.getNow());
                rm.setSrmAtpcreateuser(ls.getSUser().getSuAccount());
                rm.setSrmAtplastmodifydatetime(Utils.getNow());
                rm.setSrmAtplastmodifyuser(ls.getSUser().getSuAccount());
                rm.setSrmFksroleid(roleId);
                rm.setSrmFksmoduleid(moduleId);
                rm.setSrmSmaction(moduleAction);

                iSrolemoduleService.save(rm);
            }


            return Utils.makeJSONResponseMsg(1, "保存成功", null);

        } catch (Exception e) {

            e.printStackTrace();
            return Utils.makeJSONResponseMsg(0, "保存失败", null);
        }
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iSroleService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 导出excel
     *
     * @return
     */
    @GetMapping(value = "/exportExcel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(value = "keyword", defaultValue = "") String keyword,
                            @RequestParam(value = "sort", defaultValue = "sr_atplastmodifydatetime") String sort,
                            @RequestParam(value = "order", defaultValue = "desc") String order) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("角色列表");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("sr_name", keyword);
        queryWrapper.or();
        queryWrapper.like("sr_remark", keyword);
        queryWrapper.or();
        queryWrapper.like("sr_atpcreateuser", keyword);
        queryWrapper.or();
        queryWrapper.like("sr_indexurl", keyword);
        queryWrapper.or();
        queryWrapper.like("sr_atpcreatedatetime", keyword);

        if (order.equals("desc")) {
            queryWrapper.orderByDesc(sort);
        } else {
            queryWrapper.orderByAsc(sort);
        }
        List<Srole> roleList = iSroleService.list(queryWrapper);
        String fileName = "角色列表" + Utils.getNow() + ".xls";
        String[] headers = {"角色名称", "首页链接", "备注", "创建人", "创建时间"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //写表头 第0行
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //写数据行内容 从第1行开始
        int rowNum = 1;
        for (Srole role : roleList) {

            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(role.getSrName());
            row1.createCell(1).setCellValue(role.getSrIndexurl());
            row1.createCell(2).setCellValue(role.getSrRemark());
            row1.createCell(3).setCellValue(role.getSrAtpcreateuser());
            row1.createCell(4).setCellValue(role.getSrAtpcreatedatetime());
            rowNum++;
        }

        response.setContentType("application/octet-stream;");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
