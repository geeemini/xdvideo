package net.xdclass.xdvideo.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.service.impl.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视频相关controller
 */

@RestController
@RequestMapping(value = "/api/v1/video")
public class VideoController {

    @Autowired
    private VideoServiceImpl videoService;

    @CrossOrigin
    @GetMapping("page")
	public Object find_all(@RequestParam(value = "page",defaultValue = "0")int page,
                           @RequestParam(value = "size",defaultValue = "10")int size){
        PageHelper.startPage(page, size);
        List<Video> list = videoService.getAll();
        PageInfo<Video> pageInfo = new PageInfo<>(list);

        return pageInfo;
	}

	@GetMapping("find_by_id")
	public Object find_by_id(@RequestParam(value = "id",required = true)int id){
		return videoService.findById(id);
	}



	
	
}
