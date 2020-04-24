package net.xdclass.xdvideo.config;

import net.xdclass.xdvideo.intercepter.Loginintercepter;
import net.xdclass.xdvideo.intercepter.TestLoggerintercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: xdvideo
 * @description 拦截器配置
 * @author: gemini
 * @create: 2020-04-15 21:58
 * 有内鬼，中止交易！
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Loginintercepter()).addPathPatterns("/user/api/v1/*/**");
        registry.addInterceptor(new TestLoggerintercepter()).addPathPatterns("/api/v1/*/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
