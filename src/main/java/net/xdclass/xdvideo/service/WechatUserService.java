package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.domain.User;

/**
 * For: 微信用户服务类
 *
 * @Author: gemini
 * @Date: 2020/4/15 14:55
 * 有内鬼，中止交易
 */
public interface WechatUserService {

    User savaWechatUser(String code);
}
