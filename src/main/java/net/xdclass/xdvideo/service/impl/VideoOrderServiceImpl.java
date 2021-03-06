package net.xdclass.xdvideo.service.impl;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.User;
import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.domain.VideoOrder;
import net.xdclass.xdvideo.dto.VideoOrderDto;
import net.xdclass.xdvideo.exception.YwException;
import net.xdclass.xdvideo.mapper.UserMapper;
import net.xdclass.xdvideo.mapper.VideoMapper;
import net.xdclass.xdvideo.mapper.VideoOrderMapper;
import net.xdclass.xdvideo.service.VideoOrderService;
import net.xdclass.xdvideo.utils.CommonUtils;
import net.xdclass.xdvideo.utils.HttpUtils;
import net.xdclass.xdvideo.utils.WXPayUtils;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

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
    @Transactional(propagation = Propagation.REQUIRED)
    public String save(VideoOrderDto videoOrderDto) throws Exception {
        dataLogger.info("model=video_order`api=save`user_id={}`video_id={}"
                ,videoOrderDto.getUserId(), videoOrderDto.getVideoId());

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
        String str = unifiedOrder(order);

        return str;
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {
        return videoOrderMapper.findByOutTradeNo(outTradeNo);
    }

    @Override
    public Integer updateVideoOrderByOutTradeNo(VideoOrder videoOrder) {
        return videoOrderMapper.updateVideoOrderByOutTradeNo(videoOrder);
    }


    /**
     * 统一下单方法
     * @param order
     * @return
     */
    private String unifiedOrder(VideoOrder order) throws Exception {

//        int i = 1/0; //模拟异常

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
        String payXml = WXPayUtils.mapToXml(map);
        System.out.println(payXml);
        //统一下单
        String orderStr = HttpUtils.doPost(WeChatConfig.getUnifiedOrderUrl(),payXml,4000);
        if(null == orderStr) {
            return null;
        }

        Map<String, String> unifiedOrderMap =  WXPayUtils.xmlToMap(orderStr);
        System.out.println(unifiedOrderMap.toString());
        if(unifiedOrderMap != null) {
            return unifiedOrderMap.get("code_url");
        }

        return null;
    }

}
