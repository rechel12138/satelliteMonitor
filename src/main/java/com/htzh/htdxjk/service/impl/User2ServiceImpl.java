package com.htzh.htdxjk.service.impl;

import com.htzh.htdxjk.entity.User2;
import com.htzh.htdxjk.mapper.User2Mapper;
import com.htzh.htdxjk.service.IUser2Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 航天多星监控TEAM
 * @since 2019-02-21
 */
@Service
public class User2ServiceImpl extends ServiceImpl<User2Mapper, User2> implements IUser2Service {

    @Autowired
    User2Mapper user2Mapper;

    @Override
    public List<User2> getUser2Diy() {
        return user2Mapper.getUser2Diy();
    }

    @Override
    public void saveExcelList(List<User2> typeLists) {
        for (User2 type : typeLists) {
            //调用mapper的保存方法
            user2Mapper.insertSelective(type);
        }
    }

    private final static String excel2003 =".xls";
    private final static String excel2007 =".xlsx";

    /**
     * @param in
     * @param fileName
     * 处理上传的excel文件
     *
     * */
    public  List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception{
        List<List<Object>> list = null;
        //创建Excel工作薄
        Workbook work = this.getWorkbook(in,fileName);
        if(null == work){
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<List<Object>>();
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if(sheet==null){continue;}

            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if(row==null||row.getFirstCellNum()==j){continue;}

                List<Object> li = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell =row.getCell(y);
                    li.add(cell);
                }
                list.add(li);
            }
        }
        work.close();
        return list;
    }
    //判断excel文件的格式
    public  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(excel2003.equals(fileType)){
            wb = new HSSFWorkbook(inStr);
        }else if(excel2007.equals(fileType)){
            wb = new XSSFWorkbook(inStr);
        }else{
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

}
