package net.xdclass.xdvideo.utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.Map;

/**
 * For: 封装http 方法  这里只封装get和post
 *
 * @Author: gemini
 * @Date: 2020/4/15 8:39
 * 有内鬼，中止交易
 */
public class HttpUtils {

    private static final Gson gson = new Gson();


    /**
     * 模拟一个get请求
     * @param url
     * @return
     */
    public static Map<String, Object> doget(String url){

        Map<String, Object> map = new HashMap<>();
        // 新建一个网络连接
        CloseableHttpClient aDefault = HttpClients.createDefault();

        RequestConfig build = RequestConfig.custom() //自定义配置？？？
                .setConnectTimeout(5000) // 建立连接超时
                .setConnectionRequestTimeout(3000) // 请求超时
                .setSocketTimeout(3000) // socket连接超时
                .setRedirectsEnabled(true) // 允许重定向
                .build();
        // 模拟一个get请求
        HttpGet httpGet = new HttpGet(url);
        // 把配置放到请求里去
        httpGet.setConfig(build);

        try {
            HttpResponse httpResponse = aDefault.execute(httpGet);
            // 如果返回的状态码是200   表示成功
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                // 接受返回结果
                String jsonResult = EntityUtils.toString(httpResponse.getEntity());
                // 把string类型的json数据转化成map类型
                map = gson.fromJson(jsonResult,map.getClass());
            }
        }catch (Exception e){
            // 打印堆栈比较耗性能
            e.printStackTrace();
        }finally {
            try {
                aDefault.close();
            }catch (Exception e){
                // 得到信息相较于上面比较不耗性能
                e.getMessage();
            }
        }
        return map;
    }

    /**
     * 模拟一个post请求
     * @return
     */
    public static String doPost(String url,String data,Integer timeout){

        //创建一个连接
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 配置信息
        RequestConfig build = RequestConfig.custom() //自定义配置？？？
                .setConnectTimeout(timeout) // 建立连接超时
                .setConnectionRequestTimeout(timeout) // 请求超时
                .setSocketTimeout(timeout) // socket连接超时
                .setRedirectsEnabled(true) // 允许重定向
                .build();

        //模拟一个post请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(build);
        //　设置请求的响应头
        httpPost.setHeader("Content-Type","text/html;charset=UTF-8");
        //把数据放到post请求中去
        if(data != null && data instanceof String){
            StringEntity stringEntity = new StringEntity(data, "UTF-8");
            httpPost.setEntity(stringEntity);

        }
        try {
            // 执行
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            // 拿到响应体
            HttpEntity entity = httpResponse.getEntity();
            // 如果返回的状态码是200  也就是成功
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 把响应体进行格式转换
                String result = EntityUtils.toString(entity);
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


}
