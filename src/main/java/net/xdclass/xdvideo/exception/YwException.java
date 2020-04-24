package net.xdclass.xdvideo.exception;

/**
 * For: 自定义异常
 *
 * @Author: gemini
 * @Date: 2020/4/21 9:24
 * 有内鬼，中止交易
 */
public class YwException extends RuntimeException{

    private String msg;
    private Integer code;

    public YwException(String msg, Integer code) {
        // 隐式的调用父类的构造方法  变量啊  实参啊啥的
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
