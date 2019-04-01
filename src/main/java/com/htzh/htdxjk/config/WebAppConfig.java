package com.htzh.htdxjk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Rechel
 * 拦截器配置类
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);
        // 拦截路径
        loginRegistry.addPathPatterns("/api/*/add*");
        loginRegistry.addPathPatterns("/api/*/update*");
        loginRegistry.addPathPatterns("/api/*/remove*");
        loginRegistry.addPathPatterns("/api/*/delete*");
        // 排除路径
        loginRegistry.excludePathPatterns("/index");
        // 排除资源请求
        loginRegistry.excludePathPatterns("/resources/static/*");
        loginRegistry.excludePathPatterns("/resources/templates/*");


        SlogInterceptor slogInterceptor = new SlogInterceptor();
        InterceptorRegistration slogRegistry = registry.addInterceptor(slogInterceptor);
        slogRegistry.addPathPatterns("/api/*/*");

//        ApiPermissionInterceptor apiPermissionInterceptor = new ApiPermissionInterceptor();
//        InterceptorRegistration apiRegistry=  registry.addInterceptor(apiPermissionInterceptor);
//        apiRegistry.addPathPatterns("/api/*/*");

    }

    /**
     * 文件 设置虚拟路径，访问绝对路径下资源
     */
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);
    }

}