package com.kingmj.qr_login.user;

import com.kingmj.qr_login.user.dto.LoginRequest;
import com.kingmj.qr_login.user.dto.LoginResponse;
import com.kingmj.qr_login.user.dto.QRCodeResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/qrcode")
    public ResponseEntity<QRCodeResponse> generateORCode() {
        return ResponseEntity.ok(authService.generateORCode());
    }

    @GetMapping("/qrcode/{token}/login")
    public ResponseEntity<LoginResponse> checkQRCodeLogin(@PathVariable String token){
        return ResponseEntity.ok(authService.checkORCodeLogin(token));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/user-info")
    public ResponseEntity<String> getUserInfo(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(authService.getUserInfo(token));
    }

    @PostMapping
    public void createUser(@RequestParam String userId, @RequestParam String password) {
        authService.createUser(userId, password);
    }
}
