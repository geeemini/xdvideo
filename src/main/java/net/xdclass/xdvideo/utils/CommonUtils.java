package net.xdclass.xdvideo.utils;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * For: 通用方法类
 *
 * @Author: gemini
 * @Date: 2020/4/8 14:50
 * 有内鬼，中止交易
 */
public class CommonUtils {


    /**
     * 返回一个32位uuid 用来标记唯一订单 也用作nonce_str
     * @return
     */
    public static String generateUUID(){
        String uuid = UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 32);
        return uuid;
    }


    /**
     * md5 加密
     * @param code
     * @return
     */
    public static String MD5(String code){
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] digest = md5.digest(code.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (byte item : digest) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
