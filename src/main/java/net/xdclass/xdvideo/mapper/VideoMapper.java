package net.xdclass.xdvideo.mapper;

import net.xdclass.xdvideo.domain.Video;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.List;

/**
 * For:
 *
 * @Author: gemini
 * @Date: 2020/4/8 16:06
 * 有内鬼，中止交易
 */
//@Mapper
public interface VideoMapper {

    @Select("select * from video")
//    @Results({
//            @Result(column = "cover_img",property ="coverImg")
//            ,@Result(column = "view_num",property ="viewNum")
//            ,@Result(column = "create_time",property ="createTime")
//    })
    List<Video> getAll();

    @Select("select * from video where id = #{id}")
    Video findById(int id);

    @Update("update video set title=#{title} where id = #{id}")
    int updateById(Video video);

    @Delete("delete from video where id = #{id}")
    int deleteById(int id);

    @Insert("INSERT INTO `video` ( `title`, `summary`, " +
			            "`cover_img`, `view_num`, `price`, `create_time`," +
				            " `online`, `point`)" +
			            "VALUES" +
			            "(#{title}, #{summary}, #{coverImg}, #{viewNum}, #{price},#{createTime}" +
			            ",#{online},#{point});")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insert(Video video);


}
