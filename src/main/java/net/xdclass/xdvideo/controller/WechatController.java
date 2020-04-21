package net.xdclass.xdvideo.controller;

import jdk.nashorn.internal.ir.WhileNode;
import jdk.nashorn.internal.objects.annotations.Where;
import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.JsonData;
import net.xdclass.xdvideo.domain.User;
import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.domain.VideoOrder;
import net.xdclass.xdvideo.service.VideoOrderService;
import net.xdclass.xdvideo.service.WechatUserService;
import net.xdclass.xdvideo.utils.JwtUtils;
import net.xdclass.xdvideo.utils.WXPayUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

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

    @Autowired
    private VideoOrderService videoOrderService;
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

    /**
     * 微信支付的回调方法
     * @param request
     * @param response
     */
    @RequestMapping("/order/callback")
    public void orderPayCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream = request.getInputStream();

        //bufferedReader是包装设计模式，性能更高
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        StringBuffer sb = new StringBuffer();
        String line ;
        while ((line=in.readLine()) != null){
            sb.append(line);
        }
        in.close();
        inputStream.close();
        HashMap<String, String> map = WXPayUtils.xmlToMap(sb.toString());
        System.out.println(map);

        //支付成功 回调之后，去验证签名是否是一致的，一致的话再去更新订单，
        SortedMap<String, String> sortedMap = WXPayUtils.hashMapToSortedMap(map);

        //验证签名是否一致
        if(WXPayUtils.isCorrectPaySign(sortedMap,weChatConfig.getKey())){

            if("SUCCESS".equals(sortedMap.get("result_code"))){
                String outTradeNo = sortedMap.get("out_trade_no");
                VideoOrder dbVideoOrder = videoOrderService.findByOutTradeNo(outTradeNo);

                if(null != dbVideoOrder && dbVideoOrder.getState() == 0){
                    VideoOrder videoOrder = new VideoOrder();
                    videoOrder.setState(1);
                    videoOrder.setNotifyTime(new Date());
                    videoOrder.setOpenid(sortedMap.get("openid"));
                    videoOrder.setOutTradeNo(sortedMap.get("out_trade_no"));

                    Integer row = videoOrderService.updateVideoOrderByOutTradeNo(videoOrder);
                    if(row == 1){
                        response.setContentType("text/xml");
                        response.getWriter().print("success");
                        return ;
                    }
                }
            }
        }
        response.setContentType("text/xml");
        response.getWriter().print("fail");


    }

}
