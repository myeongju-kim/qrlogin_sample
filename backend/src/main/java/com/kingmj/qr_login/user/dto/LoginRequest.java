package com.kingmj.qr_login.user.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        String mode,
        String token,
        @NotEmpty
        String userId,
        @NotEmpty
        String password

) {
}
