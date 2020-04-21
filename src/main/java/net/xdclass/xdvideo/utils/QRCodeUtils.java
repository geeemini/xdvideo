package net.xdclass.xdvideo.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * For: Google zxing 二维码工具类
 *
 * @Author: gemini
 * @Date: 2020/4/20 10:19
 * 有内鬼，中止交易
 */
public class QRCodeUtils {

    /**
     * 生成一个二维码
     * @param content
     * @param response
     */
    public static void createQRCode(String content, int width,
                                    int height, HttpServletResponse response){
        Map<EncodeHintType,Object> hintType = new HashMap<>();
        //纠错等级
        hintType.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //编码类型 utf-8
        hintType.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        //二维码空白区域,最小为0也有白边,只是很小,最小是6像素左右
        hintType.put(EncodeHintType.MARGIN, 1);

        try {
            // 1、读取文件转换为字节数组
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content
                    , BarcodeFormat.QR_CODE, width, height, hintType);
            // 2、转为图片对象格式
            BufferedImage bufferedImage = toBufferImage(bitMatrix);

            // 3、转为png格式的图片io流
            ImageIO.write(bufferedImage,"png",response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *  转为图片对象格式
     * @param matrix
     * @return
     */
    private static BufferedImage toBufferImage(BitMatrix matrix){
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

}
