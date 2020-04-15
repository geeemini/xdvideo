package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.JsonData;
import net.xdclass.xdvideo.domain.User;
import net.xdclass.xdvideo.service.WechatUserService;
import net.xdclass.xdvideo.utils.JwtUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Autowired
    private WechatUserService wechatUserService;
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

    /**
     * 微信授权登陆成功后的回调地址
     * @param code
     * @param state
     * @param response
     */
    @GetMapping("/user/callback")
    @ResponseBody
    public void wechatUserCallback(@RequestParam(value = "code",required = true) String code, String state, HttpServletResponse response) throws IOException {
        // 这里的code就类似与一个凭证，可以根据这个再去往微信的服务器发送请求
//        System.out.println("code="+code);
        // 这里state好像是传回来一个登陆成功后返回的页面的地址
//        System.out.println("state="+state);

        //根据code去拿到access_token和openid
        User user = wechatUserService.savaWechatUser(code);
        if(user != null){
            // 通过jwt生成一个token
            String token = JwtUtils.jwtEncryption(user);
            //state 当前用户页面的地址需要拼接http:// 这样才不会进行站内跳转
            response.sendRedirect(state+"&token="+token+"&name="+user.getName()+"&headerImg="+URLEncoder.encode(user.getHeadImg(),"UTF-8"));
        }

    }

}
