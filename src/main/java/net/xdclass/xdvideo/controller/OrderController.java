package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.domain.JsonData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: xdvideo
 * @description
 * @author: gemini
 * @create: 2020-04-15 22:04
 * 有内鬼，中止交易！
 **/
@ResponseBody
@RequestMapping("/user/api/v1/order")
public class OrderController {

    @GetMapping("save")
    public JsonData saveOrder(){
        return JsonData.buildSuccess("登陆成功");
    }


}
