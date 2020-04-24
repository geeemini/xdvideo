package net.xdclass.xdvideo.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * For:
 *
 * @Author: gemini
 * @Date: 2020/4/21 11:11
 * 有内鬼，中止交易
 */
public class TestLoggerintercepter implements HandlerInterceptor {

    private Logger datalogger = LoggerFactory.getLogger("dataLogger");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            datalogger.info("这是我自定义的拦截器");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;


    }
}
