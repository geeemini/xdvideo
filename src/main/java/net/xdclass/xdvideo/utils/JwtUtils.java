package net.xdclass.xdvideo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.xdclass.xdvideo.config.JwtConfig;
import net.xdclass.xdvideo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

   /* //发行者
    public static final String SUBJECT = "www.xdclass.net";

    // 过期时间  一周
    public static final Long EXPIRE = 1000*60*60*24*7L;

    // 自定义的密钥  很重要   不能泄露  生产和测试应该都不一样
    public static final  String APPSECRET = "gemini666";*/

    /**
     * Jwt 的加密算法生成token
     * @param user
     * @return
     */
    public static String jwtEncryption(User user){

        if(user == null || user.getId() == null || user.getName() == null || user.getHeadImg() == null){
            return null;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("id",user.getId());
        map.put("name",user.getName());
        map.put("img",user.getHeadImg());

        String token = Jwts.builder()
                .setSubject(JwtConfig.SUBJECT) // 发行者
//                .setSubject(SUBJECT) // 发行者
                .setClaims(map) // 设置个人信息,就是属于自己的属性   负责部分
                .setIssuedAt(new Date()) // 设置发行时间
                .setExpiration(new Date(System.currentTimeMillis()+ JwtConfig.EXPIRE)) //过期时间
//                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE)) //过期时间
                .signWith(SignatureAlgorithm.HS256,JwtConfig.APPSECRET) // 设置签名  里面的参数是签名方法和密钥
//                .signWith(SignatureAlgorithm.HS256,APPSECRET) // 设置签名  里面的参数是签名方法和密钥
                .compact();  // 紧凑一下，前面做的操作可能会很长，这一步就是紧凑一下整理成对应格式的string类型
        return token;
    }

    /**
     * Jwt解密算法   传进来token进行解密 得到相应的信息
     * @param token
     * @return
     */
    public  static Claims decryptJWS(String token){
        try{
            final Claims claims = Jwts.parser() //生成一个解析类
                    .setSigningKey(JwtConfig.APPSECRET) // 设置一个密钥
//                    .setSigningKey(APPSECRET) // 设置一个密钥
                    .parseClaimsJws(token) // 解析token
                    .getBody(); // 解密成功后 拿到body，我们的自定义信息存在body里
            return claims;
        }catch (Exception e){

        }

        return null;

    }

}
