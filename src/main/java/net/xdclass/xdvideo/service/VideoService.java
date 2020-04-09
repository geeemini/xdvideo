package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.domain.Video;

import java.util.List;

/**
 * For:
 *
 * @Author: gemini
 * @Date: 2020/4/8 14:48
 * 有内鬼，中止交易
 */
public interface VideoService {

    List<Video> getAll();

    Video findById(int id);

    int updateById(Video video);

    int deleteById(int id);

    int insert(Video video);

}
