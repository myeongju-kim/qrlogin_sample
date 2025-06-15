package com.kingmj.qr_login.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> generateORCode() {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(authService.generateORCode());
    }


    @PostMapping
    public void createUser(@RequestParam String userId, @RequestParam String password) {
        authService.createUser(userId, password);
    }
}
