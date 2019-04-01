package com.htzh.htdxjk.controller;


import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.zxing.common.BitMatrix;
import com.htzh.htdxjk.entity.Borbit;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.service.IBorbitService;
import com.htzh.htdxjk.utils.MatrixToImageWriter;
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
import java.io.OutputStream;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-03-01
 */
@RestController
@Slf4j
@Api(value = "user-api", tags = {"轨道根数"})
@RequestMapping("/api/borbit")
public class BorbitController {


    @Autowired
    private IBorbitService iBorbitService;


    /**
     * 查询
     */
    @PostMapping(value = "listBorbit", produces = "application/json;charset=UTF-8")
    public String listBorbit() {
        List map = iBorbitService.list();
        return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, null);
    }

    /**
     * 增加
     */
    @ApiOperation(value = "新增轨道根数", notes = "新增轨道根数")
    @PostMapping(value = "addBorbit", produces = "application/json;charset=UTF-8")
    public String addBorbit(Borbit inputEntity, HttpSession httpSession) {
        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBorbitAtpid(UUID.randomUUID().toString());
            inputEntity.setBorbitAtpcreatedatetime(Utils.getNow());
            inputEntity.setBorbitAtpcreateuser(ls.getSUser().getSuAccount());
            inputEntity.setBorbitAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBorbitAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBorbitService.save(inputEntity);
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
    @ApiOperation(value = "修改轨道根数(完成)", notes = "修改轨道根数")
    @PostMapping(value = "updateBorbit", produces = "application/json;charset=UTF-8")
    public String updateBorbit1(Borbit inputEntity, HttpSession httpSession) {

        try {
            //检查权限
            if (!Utils.checkLoginStatus(httpSession)) {
                return Utils.makeJSONResponseMsg(ResultTo.LOGIN_ERR_STATUS, ResultTo.LOGIN_ERR_MSG, null);

            }
            if (StringUtils.isEmpty(inputEntity.getBorbitAtpid())) {
                return Utils.makeJSONResponseMsg(0, "修改主键为空", null);
            }
            if (iBorbitService.getById(inputEntity.getBorbitAtpid()) == null) {
                return Utils.makeJSONResponseMsg(0, "修改对象不存在", null);
            }
            //具体过程
            LoginStatus ls = (LoginStatus) httpSession.getAttribute("loginStatus");
            inputEntity.setBorbitAtplastmodifydatetime(Utils.getNow());
            inputEntity.setBorbitAtplastmodifyuser(ls.getSUser().getSuAccount());

            iBorbitService.updateById(inputEntity);
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
    @PostMapping(value = "removeBorbit", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据ids批量删除轨道根数(完成)", notes = "批量删除")
    @ApiImplicitParam(name = "borbitAtpid", value = "用户ids ，多个用英文逗号分割", required = true, dataType = "String")
    public String removeBorbit1(@RequestParam("borbitAtpid") String borbitAtpid) {
        try {
            String[] idsArray = borbitAtpid.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(idsArray));
            boolean b = iBorbitService.removeByIds(arrayList);
            if (b) {
                return Utils.makeJSONResponseMsg(1, "删除成功", b);
            }
            return Utils.makeJSONResponseMsg(0, "删除失败", b);

        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }


    @ApiOperation(value = "根据时间范围、关键字查询信息(完成)", notes = "查询信息")
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
                                  @RequestParam(value = "sort", defaultValue = "borbit_atplastmodifydatetime") String sort,
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

        List<Map> list = iBorbitService.getBorbitInfo(map);

        int count = iBorbitService.countBorbitInfo(map);

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

    @ApiOperation(value = "根据时间范围、关键字导出轨道根数excel(完成)", notes = "导出excel")
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
                            @RequestParam(value = "sort", defaultValue = "borbit_atplastmodifydatetime") String sort,
                            @RequestParam(value = "order", defaultValue = "desc") String order) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("轨道根数表");


        Map map = new HashMap();
        map.put("keyword", keyword);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("sort", sort);
        map.put("order", order);

        List<Map> mapList = iBorbitService.getBorbitInfo(map);
        //设置要导出的文件的名字
        String fileName = "轨道根数列表" + Utils.getNow() + ".xls";
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"序号", "型号代号(26)", "测量时间", "类别", "半长轴(m)", "偏心率", "倾角(Deg)", "升交点赤经(Deg)", "近地点俯角(Deg)", "平近点角(Deg)"};
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

            if (map1.get("borbit_time_stamp") != null) {
                row1.createCell(2).setCellValue(map1.get("borbit_time_stamp").toString());
            } else {
                row1.createCell(2).setCellValue("");
            }

            if (map1.get("borbit_type") != null) {
                row1.createCell(3).setCellValue(map1.get("borbit_type").toString());
            } else {
                row1.createCell(3).setCellValue("");
            }

            if (map1.get("borbit_a") != null) {
                row1.createCell(4).setCellValue(map1.get("borbit_a").toString());
            } else {
                row1.createCell(4).setCellValue("");
            }

            if (map1.get("borbit_e") != null) {
                row1.createCell(5).setCellValue(map1.get("borbit_e").toString());
            } else {
                row1.createCell(5).setCellValue("");
            }

            if (map1.get("borbit_i") != null) {
                row1.createCell(6).setCellValue(map1.get("borbit_i").toString());
            } else {
                row1.createCell(6).setCellValue("");
            }

            if (map1.get("borbit_o") != null) {
                row1.createCell(7).setCellValue(map1.get("borbit_o").toString());
            } else {
                row1.createCell(7).setCellValue("");
            }

            if (map1.get("borbit_w") != null) {
                row1.createCell(8).setCellValue(map1.get("borbit_w").toString());
            } else {
                row1.createCell(8).setCellValue("");
            }

            if (map1.get("borbit_m") != null) {
                row1.createCell(9).setCellValue(map1.get("borbit_m").toString());
            } else {
                row1.createCell(9).setCellValue("");
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
    @ApiOperation(value = "导入excel（完成）", notes = "导入excel")
    @PostMapping(value = "/importExcel", produces = "application/json;charset=UTF-8")
    public String importEmp(HttpSession httpSession, MultipartFile file) {
        return null;
    }


    @GetMapping(value = "makeQRCodeString")
    @ApiOperation(value = "根据轨道根数ID生成二维码字符串", notes = "除序号、型号代号、类别外的字段，中文逗号分隔")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "borbitAtpid", value = "atpid", required = true, dataType = "String"),
    })
    public String makeQRCodeString(@RequestParam("borbitAtpid") String borbitAtpid, HttpServletResponse response) {

        try {

            Borbit borbit = iBorbitService.getById(borbitAtpid);
            StringBuffer strings = new StringBuffer();
            strings.append(borbit.getBorbitA() + ",");
            strings.append(borbit.getBorbitE() + ",");
            strings.append(borbit.getBorbitI() + ",");
            strings.append(borbit.getBorbitO() + ",");
            strings.append(borbit.getBorbitW() + ",");
            strings.append(borbit.getBorbitM());
            BitMatrix bitMatrix = QrCodeUtil.encode(strings.toString(), 50, 50);
            //设置请求头
            response.setHeader("Content-Type", "image/jpeg");
            OutputStream outputStream = response.getOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
            outputStream.flush();
            outputStream.close();

            return Utils.makeJSONResponseMsg(1, "二维码生成成功", "");
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.UPDATE_FAIL_STATUS, ResultTo.UPDATE_FAIL_STATUS_MSG, null);
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
            return Utils.makeJSONResponseMsg(ResultTo.SUCCESS_STATUS, ResultTo.SUCCESS_STATUS_MSG, iBorbitService.getOneBorbitById(atpId));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            return Utils.makeJSONResponseMsg(ResultTo.FAIL_STATUS, ResultTo.FAIL_STATUS_MSG, null);
        }
    }

    @ApiOperation(value = "根据时间范围、型号代号查询轨道根数信息(完成)", notes = "查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "satCode", value = "型号代号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "rows", value = "每页条数", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序方式", required = false, dataType = "String")
    })
    @PostMapping(value = "findByParamEzuiForBorbit", produces = "application/json;charset=UTF-8")
    public String findByParamEzuiForBorbit(@RequestParam(value = "satCode", defaultValue = "") String satCode,
                                           @RequestParam(value = "startTime", defaultValue = "2000-01-01 00:00:00") String startTime,
                                           @RequestParam(value = "endTime", defaultValue = "2099-01-01 00:00:00") String endTime,
                                           @RequestParam(value = "page", defaultValue = "1") String page,
                                           @RequestParam(value = "rows", defaultValue = "10") String rows,
                                           @RequestParam(value = "sort", defaultValue = "borbit_atplastmodifydatetime") String sort,
                                           @RequestParam(value = "order", defaultValue = "desc") String order) {

        IPage<Borbit> pageResult = null;
        Page modelPage = new Page(Integer.parseInt(page), Integer.parseInt(rows));
        if (order.equals("asc")) {
            modelPage.setAsc(sort);
        } else if (order.equals("desc")) {
            modelPage.setDesc(sort);
        } else {
            Utils.makeJSONResponseMsg(ResultTo.PARAM_ERR_STATUS, ResultTo.PARAM_ERR_MSG, null);
        }
        pageResult = iBorbitService.getBorbitInfoList(modelPage, satCode, startTime, endTime);
        return JSONObject.toJSONString(pageResult, SerializerFeature.WriteMapNullValue).replace("records", "rows");


    }

}
