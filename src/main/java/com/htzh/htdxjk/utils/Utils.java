package com.htzh.htdxjk.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.htzh.htdxjk.entity.ResponseMsg;

import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {


    /**
     * 生成状态返回JSON
     *
     * @param status
     * @param msg
     * @param data
     * @return
     */
    public static String makeJSONResponseMsg(int status, String msg, Object data) {
        ResponseMsg rm = new ResponseMsg();
        rm.setStatus(status);
        rm.setMsg(msg);
        rm.setData(data);
        return JSON.toJSONString(rm, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 检查当前用户是否登录
     *
     * @param httpSession
     * @return
     */
    public static boolean checkLoginStatus(HttpSession httpSession) {
        if (null == httpSession.getAttribute("loginStatus")) {
            return false;
        } else {
            return true;
        }
    }


    public static String getNow(){
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 生成小写md5字符串
     * @param str
     */

    public static String makeMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {


        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update((str).getBytes("UTF-8"));
        byte b[] = md5.digest();

        int i;
        StringBuffer buf = new StringBuffer();

        for(int offset=0; offset<b.length; offset++){
            i = b[offset];
            if(i<0){
                i+=256;
            }
            if(i<16){
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));

        }

        return buf.toString();
    }


    public static void main(String[] args) throws Exception {
        System.out.println(Utils.getNow());
    }

    public static List<Map<String,Object>> getMenuActionList(String action){


        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        if("".equals(action) || action==null) {return resultList;}
        String[] actionArray = action.split(",");
        for (String actionItem:actionArray){

            Map<String,Object> result = new HashMap<String,Object>();
            result.put("id",actionItem);
            result.put("text",getActionNameByNum(actionItem));
            result.put("iconCls","");
            result.put("level",3);
            resultList.add(result);
        }

        return resultList;
    }

    public static String getActionNameByNum(String num){

        //1-置顶
        //2-下载列表
        //3-大图标/小图标/列表
        //4-添加
        //5-修改
        //6-删除
        //7-启用禁用
        //8-导出execl
        //9-重置密码
        //10-设置权限
        //11-详情
        //12-回放数据
        //13-导入execl
        //14-上传
        //15-预览/下载
        //16-添加备忘
        //17-搜索
        //18-待确认按钮
        //19-新建（值班列表）
        //20-近期卫星报警信息周天筛选
        //21-值班人预判
        //22-等待确认
        //23-添加到文件共享区
        //24-批量分析
        //25-保存
        // 26-配置角色
        // 27-配置权限
        if("1".equals(num)) { return "置顶";}
        if("2".equals(num)) { return "下载列表";}
        if("3".equals(num)) { return "大图标/小图标/列表";}
        if("4".equals(num)) { return "添加";}
        if("5".equals(num)) { return "修改";}
        if("6".equals(num)) { return "删除";}
        if("7".equals(num)) { return "启用/禁用";}
        if("8".equals(num)) { return "导出execl";}
        if("9".equals(num)) { return "重置密码";}
        if("10".equals(num)) { return "设置权限";}
        if("11".equals(num)) { return "详情";}
        if("12".equals(num)) { return "回放数据";}
        if("13".equals(num)) { return "导入execl";}
        if("14".equals(num)) { return "上传";}
        if("15".equals(num)) { return "预览/下载";}
        if("16".equals(num)) { return "添加备忘";}
        if("17".equals(num)) { return "搜索";}
        if("18".equals(num)) { return "待确认";}
        if("19".equals(num)) { return "新建值班列表";}
        if("20".equals(num)) { return "近期卫星报警信息周天筛选";}
        if("21".equals(num)) { return "值班人预判";}
        if("22".equals(num)) { return "等待确认";}
        if("23".equals(num)) { return "添加到文件共享区";}
        if("24".equals(num)) { return "批量分析";}
        if("25".equals(num)) { return "保存";}
        if("26".equals(num)) { return "配置角色";}
        if("27".equals(num)) { return "配置权限";}
        return "";
    }

}
