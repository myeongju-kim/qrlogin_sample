package com.kingmj.qr_login.user.dto;

public record LoginResponse(
        boolean isSuccess,
        String message,
        String token
) {

    public static LoginResponse success(String token){
        return new LoginResponse(true, "로그인 성공", token);
    }

    public static LoginResponse fail(String message){
        return new LoginResponse(false, message, null);
    }
}
