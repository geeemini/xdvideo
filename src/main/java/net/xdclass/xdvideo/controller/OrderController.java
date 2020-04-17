package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.domain.JsonData;
import net.xdclass.xdvideo.dto.VideoOrderDto;
import net.xdclass.xdvideo.service.VideoOrderService;
import net.xdclass.xdvideo.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;




/**
 * @program: xdvideo
 * @description
 * @author: gemini
 * @create: 2020-04-15 22:04
 * 有内鬼，中止交易！
 **/
@Controller
@RequestMapping("/api/v1/order")
//@RequestMapping("/user/api/v1/order")
public class OrderController {

    @Autowired
    private VideoOrderService videoOrderService;

    @GetMapping("save")
    @ResponseBody
    public JsonData saveOrder(@RequestParam(value = "video_id",required = true) Integer videoId
            , HttpServletRequest request) throws Exception {

        String ipAddr = IpUtils.getIpAddr(request);
        int userId = 2;
        VideoOrderDto dto = new VideoOrderDto();
        dto.setVideoId(videoId); //视频编号
        dto.setIp(ipAddr); // 下单用户ip地址
        dto.setUserId(userId); // 下单用户

        videoOrderService.save(dto);
        return JsonData.buildSuccess("登陆成功");
    }



}
