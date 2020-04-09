package net.xdclass.xdvideo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * For:
 *
 * @Author: gemini
 * @Date: 2020/4/8 15:41
 * 有内鬼，中止交易
 */
@Configuration
@PropertySource(value = "classpath:application.properties")
public class WeChatConfig {

    /**
     * 微信公众号id
     */
    @Value("${wxpay.appid}")
    private String appid;

    /**
     * 微信 公众号支付密钥
     */
    @Value("${wxpay.appsecret}")
    private String appsecret;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
}
