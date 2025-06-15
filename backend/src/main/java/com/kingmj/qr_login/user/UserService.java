package com.kingmj.qr_login.user;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.kingmj.qr_login.redis.RedisService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RedisService redisService;
    private static final String LOGIN_URL = "http://localhost:7000/login";
    private static final String QR_MODE = "qrmode";

    public byte[] generateORCode() {
        String token = UUID.randomUUID().toString();
        // 토큰 생성 후, Redis에 저장
        redisService.createQrToken(token);
        // localhost/login?mode=qrmode&token=token 형태로 URL 생성
        String loginUrl = generateQRUrl(QR_MODE, token);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            BitMatrix matrix = new MultiFormatWriter()
                .encode(loginUrl, BarcodeFormat.QR_CODE, 300, 300);
            MatrixToImageWriter.writeToStream(matrix, "PNG", stream);

            return stream.toByteArray();
        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUser(String userId, String password) {
        redisService.createUser(userId, password);
    }

    private String generateQRUrl(String mode, String token) {
        return new StringBuilder(LOGIN_URL)
            .append("?mode=").append(mode)
            .append("&token=").append(token)
            .toString();
    }
}
