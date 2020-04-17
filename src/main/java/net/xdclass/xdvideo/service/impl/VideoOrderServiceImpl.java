package net.xdclass.xdvideo.service.impl;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.User;
import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.domain.VideoOrder;
import net.xdclass.xdvideo.dto.VideoOrderDto;
import net.xdclass.xdvideo.mapper.UserMapper;
import net.xdclass.xdvideo.mapper.VideoMapper;
import net.xdclass.xdvideo.mapper.VideoOrderMapper;
import net.xdclass.xdvideo.service.VideoOrderService;
import net.xdclass.xdvideo.utils.CommonUtils;
import net.xdclass.xdvideo.utils.WXPayUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * For:
 *
 * @Author: gemini
 * @Date: 2020/4/17 14:07
 * 有内鬼，中止交易
 */
@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 保存订单
     * @param videoOrderDto
     * @return
     */
    @Override
    public VideoOrder save(VideoOrderDto videoOrderDto) throws Exception {

        // 检查是否有对应视频
        Video video = videoMapper.findById(videoOrderDto.getVideoId());

        //检查是否有对应用户
        User user = userMapper.findById(videoOrderDto.getUserId());

        //生成订单
        VideoOrder order = new VideoOrder();
        // video相关
        order.setVideoId(video.getId()); // video id
        order.setVideoImg(video.getCoverImg()); // video 图片
        order.setVideoTitle(video.getTitle()); // video 标题
        order.setTotalFee(video.getPrice()); // video 价钱
        // 用户相关
        order.setNickname(user.getName()); // 用户名称
        order.setHeadImg(user.getHeadImg()); // 用户头像
        order.setUserId(user.getId()); // 用户id
        // 其他
        order.setCreateTime(new Date()); //创建日期
        order.setState(0); //订单支付状态 0 未支付 1 已支付
        order.setDel(0); // 删除标记位
        order.setIp(videoOrderDto.getIp());
        order.setOutTradeNo(CommonUtils.generateUUID());

        videoOrderMapper.insert(order);


        //生成签名
        //统一下单
        //上面两个合并成一个方法
        unifiedOrder(order);



        //获取codeurl


        //生成二维码

        return null;
    }


    /**
     * 统一下单方法
     * @param order
     * @return
     */
    private String unifiedOrder(VideoOrder order) throws Exception {
        //生成签名
        SortedMap<String,String> map = new TreeMap<>();
        map.put("appid",weChatConfig.getAppid()); //公众号id
        map.put("mch_id",weChatConfig.getMchId()); // 商户号
        map.put("nonce_str",CommonUtils.generateUUID()); //随机字符串
        map.put("body","鱼尾的测试支付-"+order.getVideoTitle()); //商品的简单描述
        map.put("out_trade_no",order.getOutTradeNo()); // 商家内部订单号
        map.put("total_fee",order.getTotalFee().toString()); // 订单金额
        map.put("spbill_create_ip",order.getIp()); // 终端ip
        map.put("notify_url",weChatConfig.getPayCallbackUrl()); //回调通知地址
        map.put("trade_type","NATIVE");
        //生成sign
        String sign = WXPayUtils.createSign(map, weChatConfig.getKey()).toUpperCase();
        map.put("sign",sign); // 签名值
        // map转xml
        String xml = WXPayUtils.mapToXml(map);
        System.out.println(xml);
        //统一下单

        return null;
    }

}
