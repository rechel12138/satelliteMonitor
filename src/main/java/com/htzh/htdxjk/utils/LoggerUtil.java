package com.htzh.htdxjk.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.htzh.htdxjk.entity.LoginStatus;
import com.htzh.htdxjk.entity.Slog;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Rechel
 */
public class LoggerUtil {
    public static final String LOG_TARGET_TYPE = "targetType";
    public static final String LOG_ACTION = "action";
    public static final String LOG_REMARK = "remark";
    public static final String LOG_OPERATE = "crud";

    public LoggerUtil() {
    }

    public static Slog getLog(HttpServletRequest request) {

        Slog log = new Slog();
        HttpSession httpSession = request.getSession();

        String suAccount="";
        String fkAccountId="";
        String userName="未登录用户";

        if (Utils.checkLoginStatus(httpSession)) {
            LoginStatus loginStatus = (LoginStatus) httpSession.getAttribute("loginStatus");
            suAccount = loginStatus.getSUser().getSuAccount();
            fkAccountId = loginStatus.getSUser().getSuAtpid();
            userName = loginStatus.getSUser().getSuChinesename();
        }


        String methodType = request.getRequestURI();

        String operateType = "";
        if (methodType.indexOf("add") != -1) {
            operateType = "新增";
        } else if (methodType.indexOf("update") != -1) {
            operateType = "修改";
        } else if (methodType.indexOf("delete") != -1 || methodType.indexOf("remove") != -1) {
            operateType = "删除";
        } else {
            operateType = "其他";
        }

        log.setSlAtpcreatedatetime(Utils.getNow());
        log.setSlAtpcreateuser(suAccount);
        log.setSlFksaccountid(fkAccountId);
        log.setSlAtpdotype(operateType);
        log.setSlAtplastmodifydatetime(Utils.getNow());
        log.setSlAtplastmodifyuser(suAccount);
        log.setSlAtpremark(userName + "调用了api《 "+request.getRequestURI()+" 》");
        log.setSlContent("发送参数：（"+JSON.toJSONString(request.getParameterMap())+"）");
        log.setSlIp(getIpAddress(request));
        log.setSlLogtime(Utils.getNow());
        log.setSlType(request.getMethod()+"操作！");
        return log;
    }

    /**
     * 获取客户端ip地址
     *
     * @param request
     * @return
     */
    public static String getCliectIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}