package com.htzh.htdxjk.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.htzh.htdxjk.entity.Srole;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Rechel
 * @Date: 2019/2/23 下午11:12
 */
public class EzuiUtil {
    public static IPage getPageResult(String page, String rows, String sort, String order, Object obj, String keyWord, List list, IService iService) {
        IPage pageResult = null;
        Page modelPage = new Page(Integer.parseInt(page), Integer.parseInt(rows));

        QueryWrapper modelQueryWrapperw = new QueryWrapper(obj);
        List<String> likes  = new ArrayList();

        for (int i = 0; i < list.size(); i++) {

            likes.add(list.get(i)+" like concat('%','"+keyWord+"','%')");
        }

        if(obj instanceof Srole){

            modelQueryWrapperw.apply("sr_atpid <> 'sr001' and ( "+String.join(" or ",likes)+" )");
        }else{

            modelQueryWrapperw.apply(String.join(" or ",likes));
        }


        if (order.equals("asc")) {
            modelQueryWrapperw.orderByAsc(sort);
        } else if (order.equals("desc")) {
            modelQueryWrapperw.orderByDesc(sort);
        } else {
        }


        pageResult = iService.page(modelPage, modelQueryWrapperw);

        return pageResult;
    }


    public static IPage getPageResultForBnotice(String page, String rows, String sort, String order, Object obj, String keyWord, List list, IService iService, String col) {
        IPage pageResult = null;
        Page modelPage = new Page(Integer.parseInt(page), Integer.parseInt(rows));


        QueryWrapper modelQueryWrapperw = new QueryWrapper(obj);

        StringBuffer stringBuffer = new StringBuffer("");
        for (int i = 0; i < list.size(); i++) {

            if (i == (list.size() - 1)) {
                stringBuffer.append(list.get(i) + " LIKE '%" + keyWord + "%'");
            } else {
                stringBuffer.append(list.get(i) + " LIKE '%" + keyWord + "%' or ");
            }
        }



        modelQueryWrapperw.apply("" + col + " <> 'DEL' and ( " + stringBuffer + " )");


        if (order.equals("asc")) {
            modelQueryWrapperw.orderByAsc(sort);
        } else if (order.equals("desc")) {
            modelQueryWrapperw.orderByDesc(sort);
        } else {
        }


        pageResult = iService.page(modelPage, modelQueryWrapperw);

        return pageResult;
    }
}
