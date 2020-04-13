package net.xdclass.xdvideo.domain;

import javax.security.auth.message.callback.PrivateKeyCallback;
import java.io.Serializable;

/**
 * @program: xdvideo
 * @description 封装的一个返回的工具类
 * @author: gemini
 * @create: 2020-04-13 20:09
 * 有内鬼，中止交易！
 **/
public class JsonData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code; // 状态码  0 成功  1 处理中  -1 失败
    private Object data; // 返回的数据
    private String msg; // 描述


    public JsonData(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    // 成功 不传入数据
    public static JsonData buildSuccess(){
        return new JsonData(0, null, null);
    }

    // 成功 传入数据
    public static JsonData buildSuccess(Object data){
        return new JsonData(0,data,null);
    }

    // 成功 传入数据和描述
    public static JsonData buildSuccess(Object data, String msg){
        return new JsonData(0, data, msg);
    }


    // 失败 输入描述信息
    public static JsonData buildError(String msg){
        return new JsonData(-1,null,msg);
    }

    // 未成功 输入描述信息和状态码
    public static JsonData buildError(Integer code, String msg){
        return new JsonData(code, null, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
