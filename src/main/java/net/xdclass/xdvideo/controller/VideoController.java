package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.service.VideoService;
import net.xdclass.xdvideo.service.impl.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/video")
public class VideoController {

    @Autowired
    private VideoServiceImpl videoService;

    @GetMapping("page")
	public Object find_all(@RequestParam(value = "page",defaultValue = "0")int page,
                           @RequestParam(value = "size",defaultValue = "10")int size){
		return videoService.getAll();
	}

	@GetMapping("find_by_id")
	public Object find_by_id(@RequestParam(value = "id",required = true)int id){
		return videoService.findById(id);
	}

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
