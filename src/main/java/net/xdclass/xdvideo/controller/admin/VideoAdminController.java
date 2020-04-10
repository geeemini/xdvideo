package net.xdclass.xdvideo.controller.admin;

import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.service.impl.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * For: video中管理员能使用的方法
 *
 * @Author: gemini
 * @Date: 2020/4/10 8:18
 * 有内鬼，中止交易
 */
@RestController
@RequestMapping(value = "/admin/api/v1/video")
public class VideoAdminController {

    @Autowired
    private VideoServiceImpl videoService;

    @DeleteMapping("delete_by_id")
    public Object delete_by_id(int id){
		return videoService.deleteById(id);
	}

	@PutMapping("update_by_id")
	public Object update_by_id(@RequestBody Video video){
		return videoService.updateById(video);
	}

	@PostMapping("insert")
	public Object insert(@RequestBody Video video){
		return videoService.insert(video);
	}
}
