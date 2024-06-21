package com.intelli5.labourlink.controller;
import com.intelli5.labourlink.Exception.CustomerRegistrationException;
import com.intelli5.labourlink.entity.JobRole;
import com.intelli5.labourlink.entity.RefreshToken;
import com.intelli5.labourlink.entity.User;
import com.intelli5.labourlink.repository.CustomerRepository;
import com.intelli5.labourlink.repository.LabourRepository;
import com.intelli5.labourlink.service.AuthService;
import com.intelli5.labourlink.service.JwtService;
import com.intelli5.labourlink.service.RefreshTokenService;
import com.intelli5.labourlink.utils.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final CustomerRepository customerRepository;
    private final LabourRepository labourRepository;
    public AuthController(AuthService authService, JwtService jwtService, RefreshTokenService refreshTokenService,
                          CustomerRepository customerRepository,
                          LabourRepository labourRepository
                          ) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.customerRepository = customerRepository;
        this.labourRepository = labourRepository;

    }
    @PostMapping("/register/admin")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody @Valid RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.registerAdmin(registerRequest));
    }

    @PostMapping("/register/customer")
    public ResponseEntity<AuthResponse> registerCustomer(@RequestBody @Valid RegisterRequest registerRequest) throws CustomerRegistrationException {
        return ResponseEntity.ok(authService.registerCustomer(registerRequest));
    }

    @PostMapping("/register/labour")
    public ResponseEntity<AuthResponse> registerLabour(@RequestBody @Valid RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.registerLabour(registerRequest));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<AuthResponse> loginAdmin(@RequestBody LoginRequest loginRequest){
        log.info("login request received,in controller");
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
    @GetMapping("/getUserRole/{email}")
    public ResponseEntity<UserRoleResponse> getUserRole(@PathVariable String email ){
        Optional<User> user = customerRepository.findByEmail(email);
        return ResponseEntity.ok(UserRoleResponse.builder()
                .role(user.get().getRole())
                .isVerified(user.get().isVerified())
                .build());

    }
    @GetMapping("/getJobRoles")
    public List<String> getJobRoles() {
        return Arrays.stream(JobRole.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @GetMapping("/nicExist/{nic}")
    public ResponseEntity<Boolean> isNICExist(@PathVariable String nic){
        return ResponseEntity.ok(authService.checkNicExists(nic));
    }
}
