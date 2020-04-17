package net.xdclass.xdvideo.mapper;

import net.xdclass.xdvideo.domain.VideoOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * For:
 *
 * @Author: gemini
 * @Date: 2020/4/17 10:08
 * 有内鬼，中止交易
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class VideoOrderMapperTest {

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Test
    void insert() {
        VideoOrder order = new VideoOrder();
        order.setDel(0);
        order.setNickname("小林呀");
        order.setOpenid("12345123");
        order.setVideoTitle("如果让自己变成一个更好的人");

        videoOrderMapper.insert(order);
        assertNotNull(order);



    }

    @Test
    void findById() {
    }

    @Test
    void findByOutTradeNo() {
    }

    @Test
    void delOrder() {
    }

    @Test
    void findMyOrderList() {
    }

    @Test
    void updateVideoOrderByOutTradeNo() {
    }
}