package net.xdclass.xdvideo.mapper;

import net.xdclass.xdvideo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * For:
 *
 * @Author: gemini
 * @Date: 2020/4/15 15:37
 * 有内鬼，中止交易
 */
public interface UserMapper {

    /**
     * 根据主键找到user
     * @param userId
     * @return
     */
    @Select("select * form user where id = #{id}")
    User findById(@RequestParam(value = "id") int userId);

    /**
     * 根据openid找到user
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User findByOpenId(@RequestParam(value="openid") String openid);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    @Insert("INSERT INTO `user` ( `openid`, `name`, " +
                "`head_img`, `phone`, `sign`, `sex`," +
                    " `city`, `create_time`)" +
                "VALUES" +
                "(#{openid}, #{name}, #{headImg}, #{phone}, #{sign},#{sex}" +
                ",#{city},#{createTime});")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int save(User user);
}
