package net.xdclass.xdvideo.provider;

import net.xdclass.xdvideo.domain.Video;
import org.apache.ibatis.jdbc.SQL;

/**
 * For: video类动态sql构建类
 *
 * @Author: gemini
 * @Date: 2020/4/10 8:42
 * 有内鬼，中止交易
 */
public class VideoSqlProvider {

    public String updateVideo(final Video video){
        return  new SQL(){{
            UPDATE("video");
            //条件写法.
           if(video.getTitle()!= null){
              SET("title=#{title}");
           }
           if(video.getSummary()!= null){
              SET("summary=#{summary}");
           }
           if(video.getCoverImg()!= null){
              SET("cover_img=#{coverImg}");
           }
           if(video.getViewNum()!= null){
              SET("view_num=#{viewNum}");
           }
           if(video.getPrice()!= null){
              SET("price=#{price}");
           }
           if(video.getOnline()!= null){
              SET("online=#{online}");
           }
           if(video.getPoint()!= null){
              SET("point=#{point}");
           }

           WHERE("id=#{id}");
        }}.toString();
    }

}
