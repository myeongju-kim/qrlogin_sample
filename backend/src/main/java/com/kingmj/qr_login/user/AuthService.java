package com.kingmj.qr_login.user;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.kingmj.qr_login.jwt.JwtHandler;
import com.kingmj.qr_login.redis.RedisService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import com.kingmj.qr_login.user.dto.LoginRequest;
import com.kingmj.qr_login.user.dto.LoginResponse;
import com.kingmj.qr_login.user.dto.QRCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtHandler jwtHandler;
    private final RedisService redisService;
    private static final String LOGIN_URL = "http://172.30.1.65:5173/login";
    private static final String QR_MODE = "qrmode";
    private static final String STATUS_KEY = "status";
    private static final String ACCESS_TOKEN_KEY = "accessToken";

    public String getUserInfo(String token) {
        return jwtHandler.validateTokenAndGetUserId(token);
    }

    public LoginResponse login(LoginRequest request) {
        if (!redisService.isValidUser(request)) {
            return LoginResponse.fail("로그인 정보가 올바르지 않습니다.");
        }
        String accessToken = jwtHandler.generateToken(request.userId());
        if (QR_MODE.equals(request.mode())) {
            redisService.approveQrToken(request.token(), request.userId(), accessToken);
        }

        return LoginResponse.success(accessToken);
    }

    public QRCodeResponse generateORCode() {
        String token = UUID.randomUUID().toString();
        // 토큰 생성 후, Redis에 저장
        redisService.createQrToken(token);
        // localhost/login?mode=qrmode&token=token 형태로 URL 생성
        String loginUrl = generateQRUrl(QR_MODE, token);

        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(loginUrl, BarcodeFormat.QR_CODE, 300, 300);
            MatrixToImageWriter.writeToStream(matrix, "PNG", stream);
            String base64Image = Base64.getEncoder().encodeToString(stream.toByteArray());

            return QRCodeResponse.of(token, base64Image);
        } catch (IOException | WriterException e) {
            throw new RuntimeException("OR 코드 생성 실패", e);
        }
    }

    public LoginResponse checkORCodeLogin(String token) {
        String status = redisService.getQrToken(token, STATUS_KEY);
        System.out.println(status);
        if (!QrTokenStatus.APPROVED.getStatus().equals(status)) {
            return LoginResponse.fail("인증 대기중입니다.");
        }
        String accessToken = redisService.getQrToken(token, ACCESS_TOKEN_KEY);

        return LoginResponse.success(accessToken);
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
