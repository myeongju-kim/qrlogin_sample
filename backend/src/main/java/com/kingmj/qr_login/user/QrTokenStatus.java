package com.kingmj.qr_login.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QrTokenStatus {
    PENDING("pending"),
    APPROVED("approved");

    private final String status;
}
