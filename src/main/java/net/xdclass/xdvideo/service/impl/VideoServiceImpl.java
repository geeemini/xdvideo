package net.xdclass.xdvideo.service.impl;

import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.mapper.VideoMapper;
import net.xdclass.xdvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * For:
 *
 * @Author: gemini
 * @Date: 2020/4/8 14:49
 * 有内鬼，中止交易
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> getAll() {
        return videoMapper.getAll();
    }

    @Override
    public Video findById(int id) {
        return videoMapper.findById(id);
    }

    @Override
    public int updateById(Video video) {
        return videoMapper.updateById(video);
    }

    @Override
    public int deleteById(int id) {
        return videoMapper.deleteById(id);
    }

    @Override
    public int insert(Video video) {

        int value = videoMapper.insert(video);
        System.out.println(video.getId());
        return value;
    }
}
