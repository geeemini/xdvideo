package net.xdclass.xdvideo.intercepter;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import net.xdclass.xdvideo.domain.JsonData;
import net.xdclass.xdvideo.utils.JwtUtils;
import org.apache.http.HttpResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: xdvideo
 * @description
 * @author: gemini
 * @create: 2020-04-15 21:41
 * 有内鬼，中止交易！
 **/
public class Loginintercepter implements HandlerInterceptor {

    private static Gson gson = new Gson();

    /**
     * 进行controller之前的拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拿token
        String accessToken = request.getHeader("token");
        if(accessToken == null){
            accessToken = (String)request.getParameter("token");
        }
        if(accessToken != null){
            Claims claims = JwtUtils.decryptJWS(accessToken);
            if(claims == null) return false;
            Integer user_id = (Integer)claims.get("id");
            String name = (String)claims.get("name");
            String img = (String)claims.get("img");

            request.setAttribute("user_id",user_id);
            request.setAttribute("name",name);
            request.setAttribute("img",img);
            return true;
        }

        sendJsonMessage(response, JsonData.buildError("请先登陆"));

        return false;
    }

    public static void sendJsonMessage(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(gson.toJson(obj));
        writer.close();
        response.flushBuffer();


    }
}
