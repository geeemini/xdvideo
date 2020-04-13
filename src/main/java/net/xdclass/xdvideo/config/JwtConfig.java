package net.xdclass.xdvideo.config;

/**
 * @program: xdvideo
 * @description
 * @author: gemini
 * @create: 2020-04-11 10:00
 * 有内鬼，中止交易！
 **/
public class JwtConfig {

    // jwt发布者
    public static final String SUBJECT = "www.xdclass.net";

    // 过期时间
    public static final Long EXPIRE = 1000*60*69*24*7L;

    // 密钥
    public static final  String APPSECRET = "gemini666";

}
