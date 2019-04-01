package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.*;
import com.htzh.htdxjk.service.IBsjckgzhdService;
import com.htzh.htdxjk.service.IBsjckgzhdService;
import com.htzh.htdxjk.service.IBsjmxssService;
import com.htzh.htdxjk.utils.EzuiUtil;
import com.htzh.htdxjk.utils.PoiUtils;
import com.htzh.htdxjk.utils.ResultTo;
import com.htzh.htdxjk.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@Api(value = "user-api", tags = {"实际跟踪弧段"})
@RequestMapping("/api/bsjckgzhd")
public class BsjckgzhdController {


    @Autowired
    private IBsjckgzhdService iBsjckgzhdService;


    /**
     * 查询
     */
    @PostMapping(value = "listBsjckgzhd", produces = "application/json;charset=UTF-8")
    public String listBsjckgzhd() {
        List map = iBsjckgzhdService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**
     * 增加
     */
    @ApiOperation(value = "新增实际跟踪弧段（完成）", notes = "新增实际跟踪弧段")
    @PostMapping(value = "addBsjckgzhd", produces = "application/json;charset=UTF-8")
    public String addBsjckgzhd(Bsjckgzhd inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBsjckgzhdAtpid(UUID.randomUUID().toString());
            inputEntity.setBsjckgzhdAtpcreatedatetime(Utils.getNow());
            inputEntity.setBsjckgzhdAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBsjckgzhdAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBsjckgzhdAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBsjckgzhdService.save(inputEntity);
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
    @ApiOperation(value = "修改实际跟踪弧段（完成）", notes = "修改实际跟踪弧段")
    @PostMapping(value = "updateBsjckgzhd", produces = "application/json;charset=UTF-8")
    public String updateBsjckgzhd(Bsjckgzhd inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBsjckgzhdAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBsjckgzhdAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBsjckgzhdService.updateById(inputEntity);
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
    @PostMapping(value = "removeBsjckgzhd", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除（完成）", notes = "批量删除")
    @ApiImplicitParam(name = "bsjckgzhdAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBsjckgzhd(@RequestParam("bsjckgzhdAtpid") String bsjckgzhdAtpid) {
        try {
            String[] idsArray = bsjckgzhdAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            iBsjckgzhdService.removeByIds(arrayList);
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    @ApiOperation(value = "根据时间范围、关键字查询信息", notes = "查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "bsatCodes", value = "多个型号代号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })

    @PostMapping(value = "findByParamEzui", produces = "application/json;charset=UTF-8")
    public String findByParamEzui(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                  @RequestParam(value = "bsatCodes", defaultValue = "") String bsatCodes,
                                  @RequestParam(value = "startTime", defaultValue = "1900-01-01 00:00:00") String startTime,
                                  @RequestParam(value = "endTime", defaultValue = "2099-01-01 00:00:00") String endTime,
                                  @RequestParam(value = "page", defaultValue = "1") String page,
                                  @RequestParam(value = "rows", defaultValue = "10") String rows,
                                  @RequestParam(value = "sort", defaultValue = "bsjckgzhd_atplastmodifydatetime") String sort,
                                  @RequestParam(value = "order", defaultValue = "desc") String order) {


        int currPage = Integer.parseInt(page);

        int countRows = Integer.parseInt(rows);

        int offset = currPage * countRows - countRows;


        Map map = new HashMap();
        map.put("keyword", keyword);
        map.put("bsatCodes", bsatCodes);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("rows", rows);
        map.put("offset", String.valueOf(offset));
        map.put("sort", sort);
        map.put("order", order);

        List<Map> list = iBsjckgzhdService.getBsjckgzhdInfo(map);

        int count = iBsjckgzhdService.countBsjckgzhdInfo(map);

        Map<String, Object> result = new HashMap<>();
        result.put("total", count);
        result.put("rows", list);
        return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);

    }


    /**
     * 导出excel
     *
     * @param response
     * @throws IOException
     */

    @ApiOperation(value = "根据关键字导出实际跟踪弧段excel(完成)", notes = "导出excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    @GetMapping(value = "exportExcel")
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(value = "keyword", defaultValue = "") String keyword,
                            @RequestParam(value = "startTime", defaultValue = "1900-01-01 00:00:00") String startTime,
                            @RequestParam(value = "endTime", defaultValue = "2099-01-01 00:00:00") String endTime,
                            @RequestParam(value = "sort", defaultValue = "bsjckgzhd_atplastmodifydatetime") String sort,
                            @RequestParam(value = "order", defaultValue = "desc") String order) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("际跟踪弧段表");


        Map map = new HashMap();
        map.put("keyword", keyword);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("sort", sort);
        map.put("order", order);

        List<Map> mapList = iBsjckgzhdService.getBsjckgzhdInfo(map);
        //设置要导出的文件的名字
        String fileName = "实际跟踪弧段表" + Utils.getNow() + ".xls";
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"序号", "型号代号(26)", "开始时间", "结束时间", "测站信息"};
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

            if (map1.get("bsat_code") != null) {
                row1.createCell(1).setCellValue(map1.get("bsat_code").toString());
            } else {
                row1.createCell(1).setCellValue("");
            }

            if (map1.get("bsjckgzhd_start_time") != null) {
                row1.createCell(2).setCellValue(map1.get("bsjckgzhd_start_time").toString());
            } else {
                row1.createCell(2).setCellValue("");
            }

            if (map1.get("bsjckgzhd_end_time") != null) {
                row1.createCell(3).setCellValue(map1.get("bsjckgzhd_end_time").toString());
            } else {
                row1.createCell(3).setCellValue("");
            }

            if (map1.get("bsjckgzhd_devname") != null) {
                row1.createCell(4).setCellValue(map1.get("bsjckgzhd_devname").toString());
            } else {
                row1.createCell(4).setCellValue("");
            }

            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }


    /**
     * 导入excel
     *
     * @param httpSession
     * @param file
     * @return
     */
    @PostMapping(value = "/importExcel", produces = "application/json;charset=UTF-8")
    public String importEmp(HttpSession httpSession, MultipartFile file) {

        //检查权限
        if (!Utils.checkLoginStatus(httpSession)) {
            return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);
        }
        //具体过程
        LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");

        List<Bsjckgzhd> bckplanList = PoiUtils.importBsjckgzhdExcelToList(file);

        for (Bsjckgzhd bsjckgzhd : bckplanList) {
            bsjckgzhd.setBsjckgzhdAtpid(UUID.randomUUID().toString());
            bsjckgzhd.setBsjckgzhdAtpcreatedatetime(Utils.getNow());
            bsjckgzhd.setBsjckgzhdAtpcreateuser(ls.getSUser().getSuAccount());
            bsjckgzhd.setBsjckgzhdAtplastmodifydatetime(Utils.getNow());
            bsjckgzhd.setBsjckgzhdAtplastmodifyuser(ls.getSUser().getSuAccount());
        }

        if (iBsjckgzhdService.saveBatch(bckplanList)) {
            return "导入成功!";
        }
        return "导入失败!";
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBsjckgzhdService.getOneBsjckById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    @ApiOperation(value = "根据时间范围、型号代号查询弧段信息(完成)", notes = "查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "satCode", value = "型号代号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    @PostMapping(value = "findByParamEzuiForBsjckgzhd", produces = "application/json;charset=UTF-8")
    public String findByParamEzuiForBsjckgzhd(@RequestParam(value = "satCode", defaultValue = "") String satCode,
                                              @RequestParam(value = "startTime", defaultValue = "2000-01-01 00:00:00") String startTime,
                                              @RequestParam(value = "endTime", defaultValue = "2099-01-01 00:00:00") String endTime,
                                              @RequestParam(value = "page", defaultValue = "1") String page,
                                              @RequestParam(value = "rows", defaultValue = "10") String rows,
                                              @RequestParam(value = "sort", defaultValue = "bsjckgzhd_atplastmodifydatetime") String sort,
                                              @RequestParam(value = "order", defaultValue = "desc") String order) {

        IPage<Bsjckgzhd> pageResult = null;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.apply("    htdxjk_bsjckgzhd.bsjckgzhd_atplastmodifydatetime>'" + startTime + "'\n" +
                "AND htdxjk_bsjckgzhd.bsjckgzhd_atplastmodifydatetime<'" + endTime + "'\n" +
                "AND htdxjk_bsjckgzhd.bsjckgzhd_satname like '%" + satCode + "%'");

        Page modelPage = new Page(Integer.parseInt(page), Integer.parseInt(rows));
        if (order.equals("asc")) {
            modelPage.setAsc(sort);
        } else if (order.equals("desc")) {
            modelPage.setDesc(sort);
        } else {
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        pageResult = iBsjckgzhdService.page(modelPage, queryWrapper);
        return JSONObject.toJSONString(pageResult, SerializerFeature.WriteMapNullValue);


    }


}
