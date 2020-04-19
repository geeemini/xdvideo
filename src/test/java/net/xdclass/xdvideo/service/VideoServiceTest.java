package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.mapper.VideoMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * For:
 *
 * @Author: gemini
 * @Date: 2020/4/17 9:44
 * 有内鬼，中止交易
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class VideoServiceTest {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoMapper videoMapper;

    @Test
    void getAll() {
        List<Video> list = videoService.getAll();
        assertNotNull(list);
        list.forEach(video -> {
            System.out.println(video.getId());
            System.out.println(video.getTitle());
            System.out.println(video.getViewNum());
        });

    }

    @Test
    void findById() {
        Video video = videoService.findById(1);
        assertNotNull(video);

    }

    @Test
    void updateById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void insert() {
    }
}