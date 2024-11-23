package com.trade.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.trade.constant.MessageConstant;
import com.trade.entity.LoginUser;
import com.trade.entity.UserQrRemaining;
import com.trade.exception.GenerateQRCodeFailureException;
import com.trade.mapper.TradeMapper;
import com.trade.service.TradeService;
import com.trade.utils.AliOssUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeServiceImpl extends ServiceImpl<TradeMapper, UserQrRemaining> implements TradeService {

    private final AliOssUtil aliOssUtil;

    private final RedisTemplate<Object, Object> redisTemplate;

    private TradeMapper tradeMapper;

    /**
     * 支付码生成
     */
    public String generatePaymentCode() {
        Long userId = getCurrentUserId();
        // 检查缓存中是否有已生成的二维码
        String QRUrl = redisTemplate.opsForValue().get("user:QRCode:" + userId).toString();
        if (!Objects.isNull(QRUrl)) {
            return QRUrl;
        }
        UserQrRemaining u = query().eq("id", userId).one();
        if (!Objects.isNull(u)) {
            QRUrl = u.getQr();
            return QRUrl;
        }
        // TODO 改为正确的url
        String url = "https://www.baidu.com";
        int width = 1000; // 图片宽度
        int height = 1000; // 图片高度
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 设置二维码的纠错级别

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            // 将BitMatrix转换为BufferedImage，支持透明度
            MatrixToImageConfig config = new MatrixToImageConfig(0x000000, 0xFFFFFFFF); // 黑色
            BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
            // 加载中心图案
            Path logoPath = FileSystems.getDefault().getPath("D:\\Java projects\\real-time-trading\\trading-server\\src\\main\\resources\\images\\logo.jpg");
            if (Files.exists(logoPath)) {
                BufferedImage logoImage = ImageIO.read(logoPath.toFile());
                // 计算中心图案的位置
                int logoWidth = logoImage.getWidth();
                int logoHeight = logoImage.getHeight();
                int x = (width - logoWidth) / 2;
                int y = (height - logoHeight) / 2;
                // 在二维码图片上绘制中心图案
                Graphics2D g2 = qrCodeImage.createGraphics();
                g2.drawImage(logoImage, x, y, null);
                g2.dispose();
            } else {
                log.warn("中心图案文件不存在: {}", logoPath);
            }
            // 将BufferedImage写入字节数组流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "PNG", baos);
            // 获取字节数组流
            byte[] qrCodeBytes = baos.toByteArray();
            // 将字节数组流转换为InputStream
            InputStream inputStream = new java.io.ByteArrayInputStream(qrCodeBytes);
            // 生成文件名
            String originalFilename = "QRCodeWithLogo.png";
            // 调用AliOssUtil的upload方法上传到OSS
            QRUrl = aliOssUtil.upload(inputStream, originalFilename);
            log.info("二维码地址：{}", QRUrl);
            tradeMapper.saveQr(url);
            redisTemplate.opsForValue().set("user:QRCode:" + userId, QRUrl, 30 + RandomUtil.randomInt(10), TimeUnit.MINUTES);
            return QRUrl;
        } catch (WriterException | IOException e) {
            log.error("生成或上传二维码失败: {}", e.getMessage());
            e.printStackTrace();
            throw new GenerateQRCodeFailureException(MessageConstant.QRCODE_GENERATED_FAILED);
        }
    }

    /**
     * 获取当前登录用户的ID
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getUser().getId();
    }
}