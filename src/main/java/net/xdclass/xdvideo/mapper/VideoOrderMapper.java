package net.xdclass.xdvideo.mapper;

import net.xdclass.xdvideo.domain.Video;
import net.xdclass.xdvideo.domain.VideoOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: xdvideo
 * @description 用户订单dao
 * @author: gemini
 * @create: 2020-04-16 22:19
 * 有内鬼，中止交易！
 **/
public interface VideoOrderMapper {

    /**
     * 新增订单
     * @param videoOrder
     * @return
     */
    @Insert("INSERT INTO `xdweixin`.`video_order`( `openid`, `out_trade_no`, `state`, " +
            "`create_time`, `notify_time`, `total_fee`, `nickname`, `head_img`, `video_id`, " +
            "`video_title`, `video_img`, `user_id`, `ip`, `del`) " +
            "VALUES " +
            "(#{openid},#{outTradeNo},#{state},#{createTime},#{notifyTime},#{totalFee},#{nickname},#{headImg}," +
            "#{videoId},#{videoTitle},#{videoImg},#{userId},#{ip},#{del});")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insert(VideoOrder videoOrder);

    /**
     * 根据id查找订单
     * @param id
     * @return
     */
    @Select("select * from video_order where id = #{id} and del = 0")
    Video findById(Integer id);

    /**
     * 根据订单号out_trade_no查找订单
     * @param id
     * @return
     */
    @Select("select * from video_order where out_trade_no = #{outTradeNo} and del = 0")
    Video findByOutTradeNo(String outTradeNo);

    /**
     * 根据id和用户id更改删除标记位的标记
     * @param id
     * @param userId
     * @return
     */
    @Update("update video_order set del = 1 where id = #{id} and user_id = #{userId}")
    Integer delOrder(@Param("id") Integer id, @Param("userId") Integer userId);

    /**
     * 查找我的订单
     * @param userId
     * @return
     */
    @Select("select * from video_order where user_id = #{userId} ")
    List<VideoOrder> findMyOrderList(Integer userId);


    /**
     * 微信回调之改变订单的状态
     * @param videoOrder
     * @return
     */
    @Update("update video_order set state = #{state} ,notify_time = #{notifyTime},openid = #{openid}  " +
            "where out_trade_no = #{outTradeNo} and state = 0 and del = 0")
    Integer updateVideoOrderByOutTradeNo(VideoOrder videoOrder);



}
