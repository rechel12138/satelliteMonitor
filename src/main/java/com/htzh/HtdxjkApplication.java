package com.htzh;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
@MapperScan({"com.htzh.mapper", "com.htzh.*.mapper"})
@Slf4j
public class HtdxjkApplication extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HtdxjkApplication.class);
    }

    @Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager) {
        return new Object();
    }

    public static void main(String[] args) throws Exception {
        log.error("Test logging!");
        SpringApplication.run(HtdxjkApplication.class, args);
    }
}

