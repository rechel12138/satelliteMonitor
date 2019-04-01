package com.htzh.htdxjk.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.IBsatService;
import com.htzh.htdxjk.service.IBxxrjlService;
import com.htzh.htdxjk.service.IBxxrjlService;
import com.htzh.htdxjk.service.IBzbbwlService;
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
import org.springframework.transaction.annotation.Transactional;
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
 * @since 2019-02-23
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"显示软件库(领域)"})
@RequestMapping("/api/bxxrjl")
public class BxxrjlController {


    @Autowired
    private IBxxrjlService iBxxrjlService;

    @Autowired
    private IBsatService iBsatService;


    @ApiOperation(value = "显示软件分类列表(完成)", notes = "显示软件分类列表")
    @GetMapping(value = "listBxxrjlCat", produces = "application/json;charset=UTF-8")
    public String listBxxrjlCat() {
        List<String> list = iBxxrjlService.findBxxrjlCat();
        return Utils.makeJSONResponseMsg(0, "查询成功", list);

    }

    /**
     * 查询
     * @return
     */
    @PostMapping(value = "listBxxrjl", produces = "application/json;charset=UTF-8")
    public String listBxxrjl() {
        List map = iBxxrjlService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, map);
    }

    /**
     * 增加
     */

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "新增分类(完成by Rechel)", notes = "新增分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appGroup", value = "分类名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bsatAtpids", value = "卫星atpid", required = false, dataType = "String")
    })
    @PostMapping(value = "addBxxrjl", produces = "application/json;charset=UTF-8")
    public String addBxxrjl(@RequestParam(value = "appGroup") String appGroup,
                            @RequestParam(value = "bsatAtpids", defaultValue = "") String bsatAtpids,
                            HttpSession httpSession) {
        try {


            Bxxrjl inputEntity = new Bxxrjl();

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("bxxrjl_app_group", appGroup);
            int count = iBxxrjlService.count(queryWrapper);

            if (count > 0) {
                return Utils.makeJSONResponseMsg(0, "添加失败，已经存在名为" + appGroup + "的分类！", null);
            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");

            String bxxrjlAtpid = UUID.randomUUID().toString();
            inputEntity.setBxxrjlAtpid(bxxrjlAtpid);
            inputEntity.setBxxrjlAtpcreatedatetime(Utils.getNow());
            inputEntity.setBxxrjlAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBxxrjlAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBxxrjlAtplastmodifyuser(ls.getSUser().getSuAccount());

            inputEntity.setBxxrjlAppGroup(appGroup);

            iBxxrjlService.save(inputEntity);

            String[] atpids;
            if (!StrUtil.hasBlank(bsatAtpids)) {
                atpids = bsatAtpids.split(",");
                iBsatService.updateSatelliteDomainByCodes(bxxrjlAtpid, atpids);
            }

            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    /**
     * 修改
     */

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "修改分类(完成by Rechel)", notes = "修改分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appGroup", value = "分类名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bxxrjlAtpid", value = "分类atpid", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bsatAtpids", value = "卫星atpids", required = true, dataType = "String")
    })
    @PostMapping(value = "updateBxxrjl", produces = "application/json;charset=UTF-8")
    public String updateBxxrjl(@RequestParam("bxxrjlAtpid") String bxxrjlAtpid, @RequestParam("appGroup") String appGroup, @RequestParam("bsatAtpids") String bsatAtpids, HttpSession httpSession) {
        try {

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("bxxrjl_app_group", appGroup);

            Bxxrjl inputEntity = iBxxrjlService.getOne(queryWrapper);
            if (inputEntity != null) {
                System.out.println("---------"+inputEntity.getBxxrjlAppName());
                if (inputEntity.getBxxrjlAtpid() != bxxrjlAtpid) {
                    return Utils.makeJSONResponseMsg(0, "修改失败，已经存在名为" + appGroup + "的分类！", null);
                }
                inputEntity.setBxxrjlAppGroup(appGroup);
                iBxxrjlService.updateById(inputEntity);
            }else {
                inputEntity=iBxxrjlService.getById(bxxrjlAtpid);
                inputEntity.setBxxrjlAppGroup(appGroup);
                iBxxrjlService.updateById(inputEntity);
            }

            String[] atpids;
            if (!StrUtil.hasBlank(bsatAtpids)) {
                atpids = bsatAtpids.split(",");
                iBsatService.updateSatelliteDomainByCodes("", atpids);
                iBsatService.updateSatelliteDomainByCodes(bxxrjlAtpid, atpids);
            }

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

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "删除分类(完成by Rechel)", notes = "删除分类")
    @ApiImplicitParam(name = "bxxrjlAtpid", value = "分类atpid", required = true, dataType = "String")
    @PostMapping(value = "deleteBxxrjl", produces = "application/json;charset=UTF-8")
    public String deleteBxxrjl(@RequestParam("bxxrjlAtpid") String bxxrjlAtpid) {

        try {
            iBxxrjlService.removeById(bxxrjlAtpid);

            Map map = new HashMap();
            map.put("domain", bxxrjlAtpid);
            iBsatService.deleteSateDomainByDomainIds(map);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }


    }


    @ApiOperation(value = "根据关键字查询信息(完成 by Rechel)", notes = "查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "bxxrjlAtpid", value = "关键字", required = false, dataType = "bxxrjlAtpid"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })

    @PostMapping(value = "findByParamEzui", produces = "application/json;charset=UTF-8")
    public String findByParamEzui(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                  @RequestParam(value = "bxxrjlAtpid", defaultValue = "") String bxxrjlAtpid,
                                  @RequestParam(value = "page", defaultValue = "1") String page,
                                  @RequestParam(value = "rows", defaultValue = "10") String rows,
                                  @RequestParam(value = "sort", defaultValue = "bxxrjl_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        int currPage = Integer.parseInt(page);

        int countRows = Integer.parseInt(rows);

        int offset = currPage * countRows - countRows;


        Map map = new HashMap();
        map.put("keyword", keyword);
        map.put("bxxrjlAtpid", bxxrjlAtpid);
        map.put("rows", rows);
        map.put("offset", String.valueOf(offset));
        map.put("sort", sort);
        map.put("order", order);

        List<Map> list = iBxxrjlService.findBxxrjlBsats(map);

        int count = iBxxrjlService.findBxxrjlBsatsCount(map);

        Map<String, Object> result = new HashMap<>();
        result.put("total", count);
        result.put("rows", list);
        return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBxxrjlService.getById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    /**
     * 导出excel
     *
     * @param response
     * @throws IOException
     */

    @ApiOperation(value = "根据关键字导出excel", notes = "导出excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String")
    })
    @GetMapping(value = "exportExcelForDomain")
    public void exportExcelForDomain(HttpServletResponse response,
                                     @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                     @RequestParam(value = "sort", defaultValue = "bxxrjl_atplastmodifydatetime") String sort,
                                     @RequestParam(value = "order", defaultValue = "desc") String order) throws IOException {

        Map map = new HashMap();
        map.put("keyword", keyword);
        map.put("sort", sort);
        map.put("order", order);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("角色列表");

        List<Map> domainsList = iBxxrjlService.findBxxrjlBsats(map);
        String fileName = "分类管理" + Utils.getNow() + ".xls";
        String[] headers = {"分类名称", "所属卫星", "修改时间", "创建人"};
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
        for (Map hashMap : domainsList) {

            HSSFRow row1 = sheet.createRow(rowNum);
            if (hashMap.get("dname") != null) {
                row1.createCell(0).setCellValue(hashMap.get("dname").toString());
            } else {
                row1.createCell(0).setCellValue("");
            }

            if (hashMap.get("bsatcodes") != null) {
                row1.createCell(1).setCellValue(hashMap.get("bsatcodes").toString());
            } else {
                row1.createCell(1).setCellValue("");
            }

            if (hashMap.get("bxxrjl_atplastmodifydatetime") != null) {
                row1.createCell(2).setCellValue(hashMap.get("bxxrjl_atplastmodifydatetime").toString());
            } else {
                row1.createCell(2).setCellValue("");
            }

            if (hashMap.get("bxxrjl_atpcreateuser") != null) {
                row1.createCell(3).setCellValue(hashMap.get("bxxrjl_atpcreateuser").toString());
            } else {
                row1.createCell(3).setCellValue("");
            }

            rowNum++;
        }

        response.setContentType("application/octet-stream;");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }


}
