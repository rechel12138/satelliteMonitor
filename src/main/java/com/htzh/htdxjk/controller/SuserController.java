package com.htzh.htdxjk.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.entity.Srole;
import com.htzh.htdxjk.entity.Suser;
import com.htzh.htdxjk.entity.Suserrole;
import com.htzh.htdxjk.service.ISroleService;
import com.htzh.htdxjk.service.ISuserService;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
@Api(value = "user-api", tags = {"用户表"})
@RequestMapping("/api/suser")
public class SuserController {
    @Autowired
    private ISuserService iSuserService;

    @Autowired
    private ISuserroleService iSuserroleService;

    @Autowired
    private ISroleService iSroleService;

    @Value("${user_default_password}")
    private String defaultPassword;


    /**
     * 查询
     *
     * @return
     */
    @PostMapping(value = "listSUser", produces = "application/json;charset=UTF-8")
    public String listSUser() {
        List map = iSuserService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, map);
    }

    /**
     * 增加
     */
    @PostMapping(value = "addSUser", produces = "application/json;charset=UTF-8")
    public String addSUser(Suser suser, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
            }

            //检查数据库是否已存在这个账号
            Suser queryUser = new Suser();
            queryUser.setSuAccount(suser.getSuAccount());
            QueryWrapper qw = new QueryWrapper(queryUser);
            List<Suser> userList = iSuserService.list(qw);
            if (userList.size() != 0) {

                return Utils.makeJSONResponseMsg(0, "该用户名" + suser.getSuAccount() + "已存在", null);

            }


            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            suser.setSuAtpid(UUID.randomUUID().toString());
            suser.setSuAtpcreatedatetime(Utils.getNow());
            suser.setSuAtpcreateuser(ls.getSUser().getSuAccount());
            suser.setSuAtplastmodifydatetime(Utils.getNow());
            suser.setSuAtplastmodifyuser(ls.getSUser().getSuAccount());
            suser.setSuAtpdotype(null);
            suser.setSuAtpstatus(null);
            suser.setSuAtpsort(0);
            suser.setSuAtpstatus(null);
            suser.setSuAtpdotype(null);
            suser.setSuPassword(Utils.makeMd5(suser.getSuPassword()));

            iSuserService.save(suser);

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
    @PostMapping(value = "updateSUser", produces = "application/json;charset=UTF-8")
    public String updateSUser(Suser inputEntity, HttpSession httpSession) {


        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }


            //根据前端 传过来的实体查询数据库里是否已经存在
            Suser queryUser = new Suser();
            queryUser.setSuAccount(inputEntity.getSuAccount());
            QueryWrapper qw = new QueryWrapper(queryUser);
            List<Suser> userList = iSuserService.list(qw);
            if (userList.size() != 0) {

                if (!userList.get(0).getSuAtpid().equals(inputEntity.getSuAtpid())) {

                    return Utils.makeJSONResponseMsg(0, "该用户名" + inputEntity.getSuAccount() + "已存在", null);

                }

            }

            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setSuAtplastmodifydatetime(Utils.getNow());
            inputEntity.setSuAtplastmodifyuser(ls.getSUser().getSuAccount());

            iSuserService.updateById(inputEntity);
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
    @PostMapping(value = "removeSUser", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除", notes = "批量删除")
    @ApiImplicitParam(name = "suAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeSUser(@RequestParam("suAtpid") String suAtpid) {
        try {
            Suserrole suserrole = new Suserrole();
            QueryWrapper modelQueryWrapperw;
            String[] idsArray = suAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));

            for (int i = 0; i < arrayList.size(); i++) {
                suserrole.setSurFksuserid(arrayList.get(i));
                modelQueryWrapperw = new QueryWrapper(suserrole);
                iSuserroleService.remove(modelQueryWrapperw);
            }

            iSuserService.removeByIds(arrayList);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            return Utils.makeJSONResponseMsg(ResultTo.UPDATE_FAIL_STATUS, ResultTo.UPDATE_FAIL_STATUS_MSG, null);
        }
    }

    //根据主键进行查询
    @PostMapping(value = "getSUserById", produces = "application/json;charset=UTF-8")
    public String getSUserById(@RequestParam("suAtpid") String suAtpid) {

        try {

            Suser user = iSuserService.getById(suAtpid);
            return Utils.makeJSONResponseMsg(1, "获取成功", user);

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
    @PostMapping(value = "findByParamEzuiForUsers", produces = "application/json;charset=UTF-8")
    public String findByParamEzuiForUsers(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                          @RequestParam(value = "page", defaultValue = "1") String page,
                                          @RequestParam(value = "rows", defaultValue = "10") String rows,
                                          @RequestParam(value = "sort", defaultValue = "su_atpid") String sort,
                                          @RequestParam(value = "order", defaultValue = "asc") String order) {
        keyword = "%" + keyword + "%";
        IPage<Suser> pageResult = null;
        try {
            Page modelPage = new Page(Integer.parseInt(page), Integer.parseInt(rows));

            pageResult = iSuserService.getUserListPage(modelPage, keyword);

        } catch (Exception e) {
            e.printStackTrace();
            return Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        return JSONObject.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");


    }


    @PostMapping(value = "findByParamEzui", produces = "application/json;charset=UTF-8")
    public String findByParamEzui(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                  @RequestParam(value = "page", defaultValue = "1") String page,
                                  @RequestParam(value = "rows", defaultValue = "10") String rows,
                                  @RequestParam(value = "sort", defaultValue = "su_atpid") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        Suser obj = new Suser();
        //筛选字段list
        List list = new ArrayList();

        list.add("su_chinesename");
        list.add("su_englishname");
        list.add("su_nickname");
        list.add("su_account");
        list.add("su_tele_phone_num");
        list.add("su_work_phone_num");

        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iSuserService);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        return JSON.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");


    }

    @ApiOperation(value = "根据用户id获取菜单权限", notes = "根据用户id获取菜单权限")
    @PostMapping(value = "getPermissionBySuAtpid", produces = "application/json;charset=UTF-8")
    String getPermissionBySuAtpid(@RequestParam("userId") String userId) {

        try {

            Suser suser = iSuserService.getById(userId);
            if (suser != null) {

                List<Map<String, Object>> smoduleList = iSuserService.getLoginedMenu(userId);
                String roleNames = iSuserService.getUserRoleNamesBySuserid(userId);

                String[] strings = roleNames.split("&");

                List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("isCurrent", true);
                result.put("menu", smoduleList);
                result.put("roleNames", strings[0]);
                result.put("indexUrls", strings[1]);
                resultList.add(result);
                String jsonStr = Utils.makeJSONResponseMsg(1, "获取成功", resultList);
                String tempStr = jsonStr.replace("smName", "title").replace("smWebpath", "href").replace("smIcon", "icon").replace("childSmodule", "chidlren").replace("smIscurrent", "isCurrent");
                return tempStr;

            } else {
                return Utils.makeJSONResponseMsg(0, "该用户不存在", null);
            }

        } catch (Exception e) {
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    @PostMapping(value = "getDefaltMenus", produces = "application/json;charset=UTF-8")
    String getDefaltMenus() {

        try {

            List<Map<String, Object>> smoduleList = iSuserService.getUnLoginedMenu();
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("isCurrent", true);
            result.put("menu", smoduleList);
            resultList.add(result);
            String jsonStr = Utils.makeJSONResponseMsg(1, "获取成功", resultList);

            String tempStr = jsonStr.replace("smName", "title").replace("smWebpath", "href").replace("smIcon", "icon").replace("childSmodule", "chidlren").replace("smIscurrent", "isCurrent");
            return tempStr;

        } catch (Exception e) {

            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }

    }

    /**
     * 启用/禁用用户（支持批量）
     *
     * @param userIds
     * @return
     */
    @PostMapping(value = "updateUserStatus", produces = "application/json;charset=UTF-8")
    String updateUserStatus(String userIds) {

        if (StringUtils.isNotEmpty(userIds)) {

            try {

                String[] ids = userIds.split(",");
                for (String id : ids) {

                    //0-启用  1-禁用
                    Suser user = iSuserService.getById(id);
                    if ("0".equals(user.getSuAtpstatus())) {

                        user.setSuAtpstatus("1");
                    } else {

                        user.setSuAtpstatus("0");
                    }

                    iSuserService.updateById(user);
                }

                return Utils.makeJSONResponseMsg(1, "更新用户状态成功", null);


            } catch (Exception e) {

                return Utils.makeJSONResponseMsg(0, "更新用户状态失败", null);
            }

        } else {

            return Utils.makeJSONResponseMsg(0, "id不能为空", null);
        }
    }

    /**
     * 重置用户密码（支持批量）
     *
     * @param userIds
     * @return
     */
    @PostMapping(value = "resetPassword", produces = "application/json;charset=UTF-8")
    String resetPassword(String userIds) {

        if (StringUtils.isNotEmpty(userIds)) {

            try {

                String[] ids = userIds.split(",");
                for (String id : ids) {

                    Suser user = new Suser();
                    String defaultPassword = Utils.makeMd5(this.defaultPassword);
                    user.setSuAtpid(id);
                    user.setSuPassword(defaultPassword);
                    iSuserService.updateById(user);
                }

                return Utils.makeJSONResponseMsg(1, "重置密码成功", null);


            } catch (Exception e) {

                return Utils.makeJSONResponseMsg(0, "重置密码失败", null);
            }

        } else {

            return Utils.makeJSONResponseMsg(0, "id不能为空", null);
        }
    }


    /**
     * 导出excel（支持参数筛选）
     *
     * @param
     * @return
     */
    @GetMapping(value = "userToExcel")
    @ApiOperation(value = "根据关键字导出excel", notes = "根据关键字导出excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    public void userToExcel(HttpServletResponse response, @RequestParam(value = "keyword", defaultValue = "") String keyword,
                            @RequestParam(value = "sort", defaultValue = "su_atplastmodifydatetime") String sort,
                            @RequestParam(value = "order", defaultValue = "desc") String order) throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");

        Map map = new HashMap();
        map.put("keyword", keyword);
        map.put("sort", sort);
        map.put("order", order);


        List<Map> mapList = iSuserService.getUserInfo(map);

        //设置要导出的文件的名字
        String fileName = "user-info" + Utils.getNow() + ".xls";
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"序号", "姓名", "账号", "所属角色", "修改时间", "最后登录时间", "状态"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (Map map1 : mapList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(rowNum);
            if (map1.get("su_chinesename") != null) {

                row1.createCell(1).setCellValue(map1.get("su_chinesename").toString());
            }
            if (map1.get("su_account") != null) {
                row1.createCell(2).setCellValue(map1.get("su_account").toString());
            }
            if (map1.get("sr_name") != null) {
                row1.createCell(3).setCellValue(map1.get("sr_name").toString());
            }
            if (map1.get("su_atplastmodifydatetime") != null) {
                row1.createCell(4).setCellValue(map1.get("su_atplastmodifydatetime").toString());
            }
            if (map1.get("su_lastlogindatetime") != null) {
                row1.createCell(5).setCellValue(map1.get("su_lastlogindatetime").toString());
            }
            if (map1.get("su_atpstatus") != null) {
                row1.createCell(6).setCellValue(map1.get("su_atpstatus").toString());
            }

            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

    /**
     * 根据用户id获取该用户配置的角色
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "getRoleListByUserId", produces = "application/json;charset=UTF-8")
    String getRoleListByUserId(String userId) {

        try {

            List<Suserrole> list = iSuserroleService.getUserRoleListByUserId(userId);
            return Utils.makeJSONResponseMsg(1, "获取该用户配置的角色成功", list);

        } catch (Exception e) {

            e.printStackTrace();
            return Utils.makeJSONResponseMsg(0, "获取该用户配置的角色失败", null);
        }

    }

    /**
     * 获取所有角色
     *
     * @return
     */
    @PostMapping(value = "getAllRolesList", produces = "application/json;charset=UTF-8")
    String getAllRolesList() {

        try {

            Srole r = new Srole();
            QueryWrapper q = new QueryWrapper(r);
            q.ne("sr_atpid", "sr001");

            List<Srole> list = iSroleService.list(q);
            return Utils.makeJSONResponseMsg(1, "获取所有角色成功", list);

        } catch (Exception e) {

            return Utils.makeJSONResponseMsg(0, "获取所有角色失败", null);
        }
    }

    /**
     * 保存角色配置
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @PostMapping(value = "saveUserRole", produces = "application/json;charset=UTF-8")
    String saveUserRole(String userId, String roleIds, HttpSession httpSession) {

        try {

            /**      * 删除该用户的所有角色配置      */
            Suserrole delRole = new Suserrole();
            delRole.setSurFksuserid(userId);
            QueryWrapper<Suserrole> delParam = new QueryWrapper<Suserrole>(delRole);
            iSuserroleService.remove(delParam);

            //插入该用户的角色配置
            String[] roleIdArray = roleIds.split(",");
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            for (String roleId : roleIdArray) {

                Suserrole item = new Suserrole();
                item.setSurAtpid(UUID.randomUUID().toString());
                item.setSurFksuserid(userId);
                item.setSurFksroleid(roleId);
                item.setSurAtpcreatedatetime(Utils.getNow());
                item.setSurAtpcreateuser(ls.getSUser().getSuAccount());
                item.setSurAtplastmodifydatetime(Utils.getNow());
                item.setSurAtplastmodifyuser(ls.getSUser().getSuAccount());
                item.setSurAtpstatus("0");
                iSuserroleService.save(item);
            }

            return Utils.makeJSONResponseMsg(1, "保存角色配置成功", null);

        } catch (Exception e) {

            e.printStackTrace();
            return Utils.makeJSONResponseMsg(0, "保存角色配置失败", null);
        }
    }

    /**
     * 修改密码
     *
     * @return
     */
    @PostMapping(value = "modifyPassword", produces = "application/json;charset=UTF-8")
    String modifyPassword(String userId, String oldPassword, String newPassword) {

        try {

            if (StringUtils.isEmpty(userId)) {

                return Utils.makeJSONResponseMsg(0, "userId不能为空", null);
            }
            if (StringUtils.isEmpty(oldPassword)) {

                return Utils.makeJSONResponseMsg(0, "原密码不能为空", null);
            }
            if (StringUtils.isEmpty(newPassword)) {

                return Utils.makeJSONResponseMsg(0, "新密码不能为空", null);
            }

            Suser user = iSuserService.getById(userId);
            if (user.getSuPassword().equals(Utils.makeMd5(oldPassword))) {

                user.setSuPassword(Utils.makeMd5(newPassword));
                iSuserService.updateById(user);
                return Utils.makeJSONResponseMsg(1, "密码修改成功", null);

            } else {

                return Utils.makeJSONResponseMsg(0, "原密码不正确", null);
            }

        } catch (Exception e) {

            e.printStackTrace();
            return Utils.makeJSONResponseMsg(0, "修改密码发生错误", null);
        }
    }

    @PostMapping(value = "getUserIdByUserName", produces = "application/json;charset=UTF-8")
    public String getUserIdByUserName(@RequestParam("username") String username) {
        return null;
    }


    @GetMapping("testEveryThing")
    public String testEveryThing() {
        Map map = new HashMap();
        map.put("userid", "su001");
        return iSuserService.getApiByUserid(map).toString();
    }

    @ApiOperation(value = "单点登录：根据用户id查询用户信息", notes = "查询信息")
    @PostMapping("loginSSO")
    public String loginSSO(@RequestParam("userid") String userid) {

        try {
            Suser suser = iSuserService.getById(userid);
            if (suser == null) {
                return Utils.makeJSONResponseMsg(0, "获取用户信息失败！没有id为" + userid + "的用户！", null);
            }

            return Utils.makeJSONResponseMsg(1, "获取用户信息成功！", suser);


        } catch (Exception e) {
            e.printStackTrace();

            return Utils.makeJSONResponseMsg(0, "获取用户信息失败！", null);
        }

    }
}
