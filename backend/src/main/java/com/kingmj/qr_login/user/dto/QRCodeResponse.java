package com.kingmj.qr_login.user.dto;

public record QRCodeResponse(
        String token,
        String qrCodeUrl
) {

    public static QRCodeResponse of (String token, String qrCodeUrl){
        return new QRCodeResponse(token, qrCodeUrl);
    }
}
