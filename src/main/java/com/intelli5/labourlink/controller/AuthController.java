package com.intelli5.labourlink.controller;

import com.intelli5.labourlink.Exception.CustomerRegistrationException;
import com.intelli5.labourlink.entity.RefreshToken;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.service.AuthService;
import com.intelli5.labourlink.service.JwtService;
import com.intelli5.labourlink.service.RefreshTokenService;
import com.intelli5.labourlink.utils.AuthResponse;
import com.intelli5.labourlink.utils.LoginRequest;
import com.intelli5.labourlink.utils.RefreshTokenRequest;
import com.intelli5.labourlink.utils.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
//@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.registerAdmin(registerRequest));
    }

    @PostMapping("/register/customer")
    public ResponseEntity<AuthResponse> registerCustomer(@RequestBody RegisterRequest registerRequest) throws CustomerRegistrationException {
        return ResponseEntity.ok(authService.registerCustomer(registerRequest));
    }

    @PostMapping("/register/labour")
    public ResponseEntity<AuthResponse> registerLabour(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.registerLabour(registerRequest));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<AuthResponse> loginAdmin(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.loginAdmin(loginRequest));
    }

    @PostMapping("/login/customer")
    public ResponseEntity<AuthResponse> loginCustomer(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.loginCustomer(loginRequest));
    }

    @PostMapping("/login/labour")
    public ResponseEntity<AuthResponse> loginLabour(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.loginLabour(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }
}
