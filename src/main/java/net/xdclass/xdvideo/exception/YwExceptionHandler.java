package net.xdclass.xdvideo.exception;

import net.xdclass.xdvideo.domain.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * For: 自定义异常处理类
 *
 * @Author: gemini
 * @Date: 2020/4/21 9:30
 * 有内鬼，中止交易
 */
@ControllerAdvice
public class YwExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handler(Exception e){
        if(e instanceof YwException){
            YwException ywexcetion = (YwException)e;
            return JsonData.buildError(ywexcetion.getCode(),ywexcetion.getMsg());
        }else{
            return JsonData.buildError("全局异常，位置错误");
        }
    }

}
