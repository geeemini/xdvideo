package net.xdclass.xdvideo;

import io.jsonwebtoken.Claims;
import net.xdclass.xdvideo.domain.User;
import net.xdclass.xdvideo.utils.JwtUtils;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


public class CommonTest {

    /**
     * 加密用法  返回token
     */
    @Test
    public void testJwtEncryption(){
        User user = new User();
        user.setId(123);
        user.setName("lxx");
        user.setHeadImg("aaaaaddddd");

        String s = JwtUtils.jwtEncryption(user);
        System.out.println(s);

    }

    /**
     * 解密用法  返回用户的信息
     */

    @Test
    public void testJwtDecrypt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpbWciOiJhYWFhYWRkZGRkIiwibmFtZSI6Imx4eCIsImlkIjoxMjMsImV4cCI6MTU4NzE3MjAxMywiaWF0IjoxNTg2NTY3MjEzfQ.Sq4JQMvhZbspX9-Bn73PwdOsANqB1eX0BNFOTz2dVqA";

        Claims claims = JwtUtils.decryptJWS(token);

        if (claims != null){
            int id = (Integer) claims.get("id");
            String name = claims.get("name").toString();
            String img = claims.get("img").toString();

            System.out.println(id);
            System.out.println(name);
            System.out.println(img);
        }else {
            System.out.println("解密失败");
        }

    }

    @Test
    public void testMap(){
        Integer n = 8;
        Integer hash = "asda".hashCode();
//        ConcurrentHashMap
        System.out.println(hash%n +"===="+((n-1) & hash));
    }

}
