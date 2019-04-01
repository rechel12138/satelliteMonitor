package com.htzh.htdxjk.controller;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.IBsatsetService;
import com.htzh.htdxjk.service.IBsatsetperrelService;
import com.htzh.htdxjk.service.IBsatsetrelService;
import com.htzh.htdxjk.service.ISuserService;
import com.htzh.htdxjk.utils.EzuiUtil;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-11
 */
@RestController
@RequestMapping("/api/bsatset")
@Api(value = "user-api", tags = {"型号集信息"})
@Slf4j
public class BsatsetController {


    @Autowired
    private IBsatsetService iBsatsetService;

    @Autowired
    private IBsatsetrelService bsatsetrelService;

    @Autowired
    private IBsatsetperrelService bsatsetperrelService;

    @Autowired
    private ISuserService suserService;

    @ApiOperation(value = "型号集分类配置(完成)", notes = "型号集分类配置")
    @GetMapping(value = "findAllBsatSet", produces = "application/json;charset=UTF-8")
    public String findAllBsatSet() {
        List<Bsatset> allBsatSet = iBsatsetService.findAllBsatSet();
        Map<String, Object> map = new HashMap<>();
        map.put("rows", allBsatSet);
        return JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
    }


    /**
     * 查询
     *
     * @param keyWord
     * @param httpSession
     * @return
     */
    @ApiOperation(value = "型号集分类列表(完成)", notes = "型号集分类列表")
    @ApiImplicitParam(name = "keyWord", value = "关键字", dataType = "String")
    @PostMapping(value = "listBsatSet", produces = "application/json;charset=UTF-8")
    public String listBsatSet(@RequestParam(value = "keyWord", defaultValue = "") String keyWord, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            String userId = ls.getSUser().getSuAtpid();
            Map<String, Object> map = new HashMap<>();
            List<Map<String, Object>> list = iBsatsetService.findByParam(keyWord, userId);
            map.put("rows", list);
            return JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }

        //return Utils.makeJSONResponseMsg(1, "查询成功", list);
    }

    /**
     * 增加
     */

    @ApiOperation(value = "添加型号分类（完成）", notes = "添加型号分类(型号ids多个用逗号隔开)")
    @PostMapping(value = "addBsatSet", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public String addBsatSet(Bsatset inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            if (StrUtil.hasBlank(inputEntity.getSatids())) {
                return Utils.makeJSONResponseMsg(0, "型号未选择,请选择", null);
            }
            if (StrUtil.hasBlank(inputEntity.getUserIds()) && "共有".equals(inputEntity.getBssType())) {
                return Utils.makeJSONResponseMsg(0, "共有模式，请选择指派人", null);
            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            String loginUserName = ls.getSUser().getSuAccount();
            String loginUserid = ls.getSUser().getSuAtpid();
            final String atpId = UUID.randomUUID().toString();
            inputEntity.setBssAtpid(atpId);
            inputEntity.setBssAtpcreatedatetime(Utils.getNow());
            inputEntity.setBssAtpcreateuser(loginUserName);
            inputEntity.setBssatplastmodifydatetime(Utils.getNow());
            inputEntity.setBssAtplastmodifyuser(loginUserName);
            inputEntity.setBssCuser(loginUserid);
            inputEntity.setBssCusertime(Utils.getNow());
            boolean save = iBsatsetService.save(inputEntity);
            if (save) {
                //型号集和指派人关系
                List<Bsatsetperrel> bsatsetperrels = new ArrayList<>();
                // 共有
                switch (inputEntity.getBssType()) {
                    case "共有":
                        String[] userStrArray = inputEntity.getUserIds().concat(",").concat(loginUserid).split(",");
                        for (String str : userStrArray) {
                            Bsatsetperrel bsatsetperrel = commonAddPerRel(loginUserName, str, atpId);
                            bsatsetperrels.add(bsatsetperrel);
                        }
                        break;
                    case "私有":
                        Bsatsetperrel bsatsetperrel = commonAddPerRel(loginUserName, loginUserid, atpId);
                        bsatsetperrels.add(bsatsetperrel);
                        break;
                    default:
                        List<String> userIdList = suserService.findUserIdList();
                        for (String str : userIdList) {
                            Bsatsetperrel bsatsetperre = commonAddPerRel(loginUserName, str, atpId);
                            bsatsetperrels.add(bsatsetperre);
                        }
                        break;
                }
                if (bsatsetperrels.size() > 0) {
                    bsatsetperrelService.saveBatch(bsatsetperrels);
                }
                //型号集和型号关系
                List<Bsatsetrel> bsatsetrels = commonAddRel(inputEntity.getSatids(), loginUserName, atpId);
                if (bsatsetrels.size() > 0) {
                    bsatsetrelService.saveBatch(bsatsetrels);
                }
                return Utils.makeJSONResponseMsg(1, "增加成功", save);
            }
            return Utils.makeJSONResponseMsg(0, "增加失败", save);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(0, "增加失败", null);
        }
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改型号分类（完成）", notes = "修改型号分类")
    @PostMapping(value = "updateBsatSet", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public String updateBsatSet(Bsatset inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            if (StrUtil.hasBlank(inputEntity.getSatids())) {
                return Utils.makeJSONResponseMsg(0, "型号未选,请选择", null);
            }
            if (StrUtil.hasBlank(inputEntity.getUserIds()) && "共有".equals(inputEntity.getBssType())) {
                return Utils.makeJSONResponseMsg(0, "共有模式，请选择指派人", null);
            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            String loginUserName = ls.getSUser().getSuAccount();
            String loginUserId = ls.getSUser().getSuAtpid();
            inputEntity.setBssatplastmodifydatetime(Utils.getNow());
            inputEntity.setBssAtplastmodifyuser(loginUserName);
            boolean b = iBsatsetService.updateById(inputEntity);
            /**
             * 删除型号集和型号关联
             * */
            bsatsetrelService.deleteByBssId(inputEntity.getBssAtpid());
            /**
             * 删除型号集和指派人关联
             * */
            bsatsetperrelService.deleteByBssId(inputEntity.getBssAtpid());
            //型号集和指派人关系
            List<Bsatsetperrel> bsatsetperrels = new ArrayList<>();
            // 共有
            switch (inputEntity.getBssType()) {
                case "共有":
                    String[] userStrArray = inputEntity.getUserIds().concat(",").concat(loginUserId).split(",");
                    for (String str : userStrArray) {
                        Bsatsetperrel bsatsetperrel = commonAddPerRel(loginUserName, str, inputEntity.getBssAtpid());
                        bsatsetperrels.add(bsatsetperrel);
                    }
                    break;
                case "私有":
                    Bsatsetperrel bsatsetperrel = commonAddPerRel(loginUserName, loginUserId, inputEntity.getBssAtpid());
                    bsatsetperrels.add(bsatsetperrel);
                    break;
                default:
                    List<String> userIdList = suserService.findUserIdList();
                    for (String str : userIdList) {
                        Bsatsetperrel bsatsetperre = commonAddPerRel(loginUserName, str, inputEntity.getBssAtpid());
                        bsatsetperrels.add(bsatsetperre);
                    }
                    break;
            }
            if (bsatsetperrels.size() > 0) {
                bsatsetperrelService.saveBatch(bsatsetperrels);
            }
            //型号集和型号关系
            List<Bsatsetrel> bsatsetrels = commonAddRel(inputEntity.getSatids(), loginUserName, inputEntity.getBssAtpid());
            if (bsatsetrels.size() > 0) {
                bsatsetrelService.saveBatch(bsatsetrels);
            }
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, b);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());

            return Utils.makeJSONResponseMsg(ResultTo.UPDATE_FAIL_STATUS, ResultTo.UPDATE_FAIL_STATUS_MSG, null);
        }

    }


    /**
     * 删除
     */
    @PostMapping(value = "removeBsatSet", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除(完成)", notes = "批量删除")
    @ApiImplicitParam(name = "bssAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    @Transactional(rollbackFor = Exception.class)
    public String removeBsatSet(@RequestParam("bssAtpid") String bssAtpid) {
        try {
            String[] idsArray = bssAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            boolean b = iBsatsetService.removeByIds(arrayList);
            if (b) {
                bsatsetperrelService.deleteByBssIdBatch(arrayList);
                bsatsetrelService.deleteByBssIdBatch(arrayList);


                return Utils.makeJSONResponseMsg(ResultTo.DEL_SUCCESS_STATUS, ResultTo.DEL_SUCCESS_STATUS_MSG, null);
            }


            return Utils.makeJSONResponseMsg(ResultTo.DEL_FAIL_STATUS, ResultTo.DEL_FAIL_STATUS_MSG, null);
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


        Bsatset obj = new Bsatset();
        //筛选字段list
        List list = new ArrayList();


        IPage<Suser> pageResult = null;
        try {
            pageResult = EzuiUtil.getPageResult(page, rows, sort, order, obj, keyword, list, iBsatsetService);
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
            return Utils.makeJSONResponseMsg(1, "查询卫星集合信息成功", iBsatsetService.getOneById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    private Bsatsetperrel commonAddPerRel(String loginUserName, String userId, String atpId) {
        Bsatsetperrel bsatsetperrel = new Bsatsetperrel();
        bsatsetperrel.setBsdmAtpid(UUID.randomUUID().toString());
        bsatsetperrel.setBsprAtpcreatedatetime(Utils.getNow());
        bsatsetperrel.setBsprAtpcreateuser(loginUserName);
        bsatsetperrel.setBsprAtplastmodifydatetime(Utils.getNow());
        bsatsetperrel.setBsprAtplastmodifyuser(loginUserName);
        bsatsetperrel.setBsprUserid(userId);
        bsatsetperrel.setBsprBssid(atpId);
        return bsatsetperrel;
    }

    private List<Bsatsetrel> commonAddRel(String satIds, String loginUserName, String atpId) {
        List<Bsatsetrel> bsatsetrelList = new ArrayList<>();
        String[] strArray = satIds.split(",");
        for (String str : strArray) {
            Bsatsetrel bsatsetrel = new Bsatsetrel();
            bsatsetrel.setBssrAtpid(UUID.randomUUID().toString());
            bsatsetrel.setBssrAtpcreatedatetime(Utils.getNow());
            bsatsetrel.setBssrAtpcreateuser(loginUserName);
            bsatsetrel.setBssrAtplastmodifydatetime(Utils.getNow());
            bsatsetrel.setBssrAtplastmodifyuser(loginUserName);
            bsatsetrel.setBssrBssid(atpId);
            bsatsetrel.setBssrSatid(str);
            bsatsetrelList.add(bsatsetrel);
        }
        return bsatsetrelList;
    }

}
