package net.xdclass.xdvideo.service.impl;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.User;
import net.xdclass.xdvideo.mapper.UserMapper;
import net.xdclass.xdvideo.service.WechatUserService;
import net.xdclass.xdvideo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * For:
 *
 * @Author: gemini
 * @Date: 2020/4/15 14:56
 * 有内鬼，中止交易
 */
@Service
public class WechatUserServiceImpl implements WechatUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatConfig weChatConfig;

    @Override
    public User savaWechatUser(String code) {

        // 获取用户的access_token
        String accessUrl = String.format(WeChatConfig.getOpenAccessTokenUrl()
                ,weChatConfig.getOpenAppid(), weChatConfig.getOpenAppSecret(),code);
        Map<String, Object> map = HttpUtils.doget(accessUrl);
        if(map == null || map.isEmpty()){
            return null;
        }
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        // 通过openid去数据库中查找是否有已存在的用户信息
        User dbuser =userMapper.findByOpenId(openid);
        if(dbuser != null){ // 如果有的话，可以进行比较更新，也可以直接返回
            return dbuser;
        }

        //获取用户的基本信息
        String userInfoUrl = String.format(WeChatConfig.getOpenUserInfoUrl()
                ,accessToken,openid);
        Map<String, Object> userMap = HttpUtils.doget(userInfoUrl);
        if(userMap == null || userMap.isEmpty()){
            return null;
        }
        String nickname = (String)map.get("nickname");

        try {
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Double sex = (Double)map.get("sex");
        String province = (String)map.get("province");
        String city = (String)map.get("city");
        String country = (String)map.get("country");
        String headimgurl = (String)map.get("headimgurl");

        StringBuffer finalAddress = new StringBuffer(country).append("||")
                .append(province).append("||").append(city);

        User user = new User();
        user.setName(nickname);
        user.setHeadImg(headimgurl);
        user.setSex(Integer.valueOf(sex.toString()));
        // 中国||浙江||杭州
        user.setCity(finalAddress.toString());
        user.setOpenid(openid);

        int userId = userMapper.save(user);

        return user;
    }
}
