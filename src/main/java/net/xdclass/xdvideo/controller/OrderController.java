package net.xdclass.xdvideo.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.xdclass.xdvideo.domain.JsonData;
import net.xdclass.xdvideo.dto.VideoOrderDto;
import net.xdclass.xdvideo.service.VideoOrderService;
import net.xdclass.xdvideo.utils.IpUtils;
import net.xdclass.xdvideo.utils.QRCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
    public void saveOrder(@RequestParam(value = "video_id",required = true) Integer videoId
            , HttpServletRequest request
            , HttpServletResponse response) throws Exception {

        String ipAddr = IpUtils.getIpAddr(request);
        int userId = 2;
        VideoOrderDto dto = new VideoOrderDto();
        dto.setVideoId(videoId); //视频编号
        dto.setIp(ipAddr); // 下单用户ip地址
        dto.setUserId(userId); // 下单用户
        // 获取
        String codeUrl = videoOrderService.save(dto);
        if (null == codeUrl) throw new NullPointerException();

        //生成二维码
        QRCodeUtils.createQRCode(codeUrl,450, 450, response);
//        return JsonData.buildSuccess(codeUrl);
    }



}
