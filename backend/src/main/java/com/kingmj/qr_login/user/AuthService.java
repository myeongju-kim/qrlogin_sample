package com.kingmj.qr_login.user;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.kingmj.qr_login.redis.RedisService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RedisService redisService;
    private static final String LOGIN_URL = "http://172.30.1.65:5173/login";
    private static final String QR_MODE = "qrmode";

    public byte[] generateORCode() {
        String token = UUID.randomUUID().toString();
        // 토큰 생성 후, Redis에 저장
        redisService.createQrToken(token);
        // localhost/login?mode=qrmode&token=token 형태로 URL 생성
        String loginUrl = generateQRUrl(QR_MODE, token);

        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            BitMatrix matrix = new MultiFormatWriter()
                .encode(loginUrl, BarcodeFormat.QR_CODE, 300, 300);
            MatrixToImageWriter.writeToStream(matrix, "PNG", stream);

            return stream.toByteArray();
        } catch (IOException | WriterException e) {
            throw new RuntimeException("OR 코드 생성 실패", e);
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
