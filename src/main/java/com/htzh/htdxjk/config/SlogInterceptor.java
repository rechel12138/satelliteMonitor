package com.htzh.htdxjk.config;

import com.htzh.htdxjk.entity.Slog;
import com.htzh.htdxjk.mapper.SlogMapper;
import com.htzh.htdxjk.utils.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Rechel
 * @Date: 2019/3/12 下午1:05
 */
@Slf4j
public class SlogInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        //把整个log中的参数，交给logUtil来获取，并返回log对象
        Slog log = LoggerUtil.getLog(httpServletRequest);
        httpServletRequest.setAttribute(LoggerUtil.LOG_OPERATE, log);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        Slog slog = (Slog) httpServletRequest.getAttribute(LoggerUtil.LOG_OPERATE);
        SlogMapper slogMapper = getMapper(SlogMapper.class, httpServletRequest);
        if (slog == null) {
            log.warn("日志信息为空", log);
        } else {
            slogMapper.insert(slog);
        }

    }

    private <T> T getMapper(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }
}
