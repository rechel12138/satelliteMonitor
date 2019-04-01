package com.htzh.htdxjk.config;

import com.htzh.htdxjk.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * controller 增强器  统一异常处理类
 * @author Rechel
 * @since 2019/3/18
 */
@ControllerAdvice
@Slf4j
public class MyControllerAdvice {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "Rechel");
    }

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String errorHandler(Exception ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return Utils.makeJSONResponseMsg(0, "异常啦！", ex.getMessage());
    }

}