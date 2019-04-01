package com.htzh.htdxjk.demoController;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htzh.htdxjk.entity.User2;
import com.htzh.htdxjk.mapper.User2Mapper;
import com.htzh.htdxjk.service.IUser2Service;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import io.swagger.annotations.Api;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Rechel
 * @since 2019-02-20
 */
//@RestController
@RequestMapping("/api/user2")
public class User2Controller {

    @Autowired
    IUser2Service user2Service;

    @Autowired
    User2Mapper user2Mapper;



    @GetMapping("getUser2Page")
    IPage<User2> getUser2Page(@RequestParam(value = "start", defaultValue = "1") Integer currentPage,
                              @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
                              User2 user2) {

        Page<User2> page = new Page<User2>(currentPage, pageSize);
        QueryWrapper<User2> diseaseQueryWrapperw = new QueryWrapper<User2>(user2);
        IPage<User2> page1 = user2Service.page(page, diseaseQueryWrapperw);

        return page1;
    }

    @GetMapping("getUsers")
    String getUsers() {
        System.out.println(user2Service.list().size());
        List<User2> user2List = user2Service.getUser2Diy();
        return JSONObject.toJSONString(user2List);
    }

    //poi 导出
    @GetMapping(value = "ElementsDownloads")
    public void downloadAllClassmate(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        List<User2> list1 = user2Service.list();
        String fileName = "userinf"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "序号", "型号代号"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (User2 user2 : list1) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(user2.getIid());
            row1.createCell(1).setCellValue(user2.getName());
            row1.createCell(2).setCellValue(user2.getAge());
            row1.createCell(3).setCellValue(user2.getEmail());
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    //导入
    @RequestMapping(value="/upload",method=RequestMethod.POST)
    public   String  uploadExcel(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        InputStream inputStream =null;
        List<List<Object>> list = null;
        MultipartFile file = multipartRequest.getFile("filename");
        if(file.isEmpty()){
            return "文件不能为空";
        }
        inputStream = file.getInputStream();
        list = user2Service.getBankListByExcel(inputStream,file.getOriginalFilename());
        inputStream.close();
        //连接数据库部分
        for (int i = 0; i < list.size(); i++) {
            List<Object> lo = list.get(i);
            user2Mapper.insertSelective(String.valueOf(lo.get(0)),Integer.valueOf((Integer) lo.get(1)),String.valueOf(lo.get(2)));
            //调用mapper中的insert方法
        }
        return "上传成功";
    }
}

