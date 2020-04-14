package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.JsonData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @program: xdvideo
 * @description
 * @author: gemini
 * @create: 2020-04-14 21:09
 * 有内鬼，中止交易！
 **/
@Controller
@RequestMapping("/api/v1/wechat")
public class WechatController {

    @Autowired
    private WeChatConfig weChatConfig;

    /**
     * 拼装微信扫一扫  登陆url
     * @param access_url
     * @return
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @GetMapping("login_url")
    public JsonData loginUrl(@RequestParam(value = "access_url",required = true) String access_url) throws UnsupportedEncodingException {
        // 获取微信开放平台重定向地址
        String redirectUrl = weChatConfig.getOpenRedirectUrl();
        // 进行编码
        String callbackUrl = URLEncoder.encode(redirectUrl,"GBK");

        String qrCodeUrl = String.format(WeChatConfig.getOpenQrcodeUrl(),weChatConfig.getOpenAppid(),callbackUrl,access_url);

        return JsonData.buildSuccess(qrCodeUrl);
    }

}
