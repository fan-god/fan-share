package com.fan.util;


import com.google.common.collect.Maps;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import static com.fan.util.ConstantFan.CHARSET;

/**
 * @author fan
 * @title: QRCodeUtil
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/26/002610:46
 */
@Slf4j
public class QRCodeUtil {
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 生成二维码
     *
     * @param content
     * @param imgPath
     * @param needCompress
     * @return
     * @throws Exception
     */
    private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QRCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 二维码中间插入图片
     *
     * @param source
     * @param imgPath
     * @param needCompress
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            log.error("该文件不存在!{}", imgPath);
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 创建二维码
     * TODO 文件路径可以加上时间戳来区分文件名称
     * @param content
     * @param imgPath
     * @param fileName
     * @param needCompress
     * @throws Exception
     */
    public static void createQRCode(String content, String imgPath, String fileName, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        // 生成的二维码的路径及名称
        if(StringUtils.isNotBlank(fileName)){
            String destPath = ConstantFan.QRCODE_PATH.concat(fileName).concat(ConstantFan.PNG);
            OperateFileUtil.checkFilePath(destPath);
            ImageIO.write(image, FORMAT_NAME, new File(destPath));
        }
    }

    /**
     * 解析二维码
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String decode(String fileName) throws Exception {
        String path = ConstantFan.QRCODE_PATH.concat(fileName).concat(ConstantFan.PNG);
        BufferedImage image = ImageIO.read(new File(path));
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }


    /**
     * 以下代码作用同上，但是不能在二维码中间插入图片
     *
     * @param url
     * @param path
     * @param fileName
     * @return
     */
    public static String createQRCode(String url, String path, String fileName) {
        try {
            Map<EncodeHintType, String> hints = Maps.newHashMap();
            hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
            File file = new File(path, fileName);
            if (file.exists() || ((file.getParentFile().exists() || file.getParentFile().mkdirs()) && file.createNewFile())) {
                writeToFile(bitMatrix, FORMAT_NAME, file);
                System.out.println("搞定：" + file);
            }

        } catch (Exception e) {
            log.error("createQrCode error:{}", e);
        }
        return null;
    }

    static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
}
