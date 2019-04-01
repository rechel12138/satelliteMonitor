package com.htzh.htdxjk.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htzh.htdxjk.entity.Slog;
import com.htzh.htdxjk.service.ISlogService;
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
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
@Api(value = "user-api", tags = {"日志"})
@RequestMapping("/api/slog/")
public class SlogController {


    @Autowired
    private ISlogService iSlogService;

    /**
     * 获取easyui datagrid数据表，支持关键字查询
     *
     * @param page
     * @param rows
     * @param keyword
     * @return
     */
    @ApiOperation(value = "获取easyui datagrid数据表 by boris_deng，支持关键字查询", notes = "获取easyui datagrid数据表，支持关键字查询")
    @PostMapping(value = "getLogListForEzui", produces = "application/json;charset=UTF-8")
    public String getLogListForEzui(int page, int rows, String keyword) {

        try {

            Map<String, Object> result = iSlogService.getLogListForEzui(page, rows, keyword);
            return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);

        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }

    @ApiOperation(value = "导出excel by boris_deng，支持关键字查询", notes = "导出excel，支持关键字查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    @GetMapping(value = "logToExcel")
    public void logToExcel(HttpServletResponse response,
                           @RequestParam(value = "keyword", defaultValue = "") String keyword,
                           @RequestParam(value = "sort", defaultValue = "sl_logtime") String sort,
                           @RequestParam(value = "order", defaultValue = "desc") String order) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("操作日志列表");

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("sl_content", keyword);
        queryWrapper.or();
        queryWrapper.like("sl_ip", keyword);
        queryWrapper.or();
        queryWrapper.like("sl_atpcreateuser", keyword);


        if (order.equals("desc")) {
            queryWrapper.orderByDesc(sort);
        } else {
            queryWrapper.orderByAsc(sort);
        }

        List<Slog> slogList = iSlogService.list(queryWrapper);
        String fileName = "操作日志列表" + Utils.getNow() + ".xls";
        String[] headers = {"操作内容", "操作时间", "操作人", "IP地址"};
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
        for (Slog slog : slogList) {

            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(slog.getSlContent());
            row1.createCell(1).setCellValue(slog.getSlAtpcreatedatetime());
            row1.createCell(2).setCellValue(slog.getSlAtpcreateuser());
            row1.createCell(3).setCellValue(slog.getSlIp());
            rowNum++;
        }

        response.setContentType("application/octet-stream;");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
