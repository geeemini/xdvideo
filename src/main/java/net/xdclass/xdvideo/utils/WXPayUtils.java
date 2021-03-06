package net.xdclass.xdvideo.utils;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.CommunicationException;
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
import java.util.*;

/**
 * For: 微信支付工具类
 *
 * @Author: gemini
 * @Date: 2020/4/17 12:36
 * 有内鬼，中止交易
 */
public class WXPayUtils {
      /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static HashMap<String, String> xmlToMap(String strXML) throws Exception {
        try {
            HashMap<String, String> data = new HashMap<String, String>();
            DocumentBuilder documentBuilder = WXPayXmlUtil.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }

    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {

        org.w3c.dom.Document document = WXPayXmlUtil.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }


    /**
     * 创建一个签名
     * @param params 订单相关参数
     * @param key 微信支付key
     * @return
     */
    public static String createSign(SortedMap<String,String> params ,String key){
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            String k = next.getKey();
            String v = next.getValue();
            if(null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)){
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append(key);
        String sign = CommonUtils.MD5(sb.toString()).toUpperCase();

        return sign;
    }

    /**
     * 支付成功后微信回调，验证回调的签名和自己生成的签名是否一致
     * @param params
     * @param key
     * @return
     */
    public static boolean isCorrectPaySign(SortedMap<String,String> params ,String key){
        String sign = createSign(params,key);
        String weixinPaySign = params.get("sign").toUpperCase();
        return weixinPaySign.equals(sign);
    }

    /**
     * 把普通map转为sortedMap
     * @return
     */
    public static SortedMap<String,String> hashMapToSortedMap(HashMap<String,String> map){
        SortedMap<String,String> sortedMap = new TreeMap<>();
        if(null != map && map.size() > 0){
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()){
                String key = it.next();
                sortedMap.put(key,map.get(key)==null?"":map.get(key).trim());
            }
        }
        return sortedMap;
    }

}
