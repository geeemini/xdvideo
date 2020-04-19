package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.domain.VideoOrder;
import net.xdclass.xdvideo.dto.VideoOrderDto;

/**
 * For:订单交易service类
 *
 * @Author: gemini
 * @Date: 2020/4/17 14:06
 * 有内鬼，中止交易
 */
public interface VideoOrderService {

    VideoOrder save(VideoOrderDto videoOrderDto) throws Exception;

}
