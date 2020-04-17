package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.JsonData;
import net.xdclass.xdvideo.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * For:
 *
 * @Author: gemini
 * @Date: 2020/4/8 15:47
 * 有内鬼，中止交易
 */
@RestController
public class TestController {

	@Value("${test}")
	private String test;

    @RequestMapping("test")
	public String test(){
		return test;
	}

	@Autowired
	private WeChatConfig weChatConfig;

	@RequestMapping("test_config")
	public String testConfig(){
        System.out.println(weChatConfig.getAppid()+"---"+weChatConfig.getAppsecret());
//        return JsonData.buildSuccess("hello xdclass222");
		return "hello xdclass222";
	}

	@Autowired
	private VideoMapper videoMapper;

	@RequestMapping("test_db")
	public Object testDb(){
		return videoMapper.getAll();
	}


}
